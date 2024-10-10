package com.ironoc.portfolio.service;


import com.ironoc.portfolio.client.Client;
import com.ironoc.portfolio.config.PropertyConfigI;
import com.ironoc.portfolio.domain.RepositoryDetailDomain;
import com.ironoc.portfolio.domain.RepositoryIssueDomain;
import com.ironoc.portfolio.dto.RepositoryDetailDto;
import com.ironoc.portfolio.dto.RepositoryIssueDto;
import com.ironoc.portfolio.job.GitDetailsRunnable;
import com.ironoc.portfolio.logger.AbstractLogger;
import com.ironoc.portfolio.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitDetailsService extends AbstractLogger implements GitDetails {

    private final PropertyConfigI propertyConfig;

    private final Client gitClient;

    private final GitRepoCache gitRepoCache;

    private final UrlUtils urlUtils;

    @Autowired
    public GitDetailsService(PropertyConfigI propertyConfig,
                             Client gitClient,
                             GitRepoCache gitRepoCache,
                             UrlUtils urlUtils) {
        this.propertyConfig = propertyConfig;
        this.gitClient = gitClient;
        this.urlUtils = urlUtils;
        this.gitRepoCache = gitRepoCache;
;    }

    @Override
    public List<RepositoryDetailDto> getRepoDetails(String username) {
        // check cache for home page user ID
        if (username.toLowerCase().equals(GitDetailsRunnable.USERNAME_HOME_PAGE)) {
            List<RepositoryDetailDomain> repoDetails = gitRepoCache
                    .get(GitDetailsRunnable.USERNAME_HOME_PAGE);
            if (repoDetails != null) {
                return this.mapResponseToRepositories(repoDetails);
            }
        }
        // further end-point validation (contains User ID)
        String uri = propertyConfig.getGitApiEndpointRepos();
        Integer page = 1;
        Integer per_page = 100;
        String apiUri = UriComponentsBuilder.fromHttpUrl(uri)
                .buildAndExpand(username, per_page, page)
                .toUriString();
        if (StringUtils.isBlank(apiUri) | StringUtils.isBlank(uri)
                | !urlUtils.isValidURL(apiUri)) {
            warn("URL is not valid: url={}", apiUri);
            return Collections.emptyList();
        }
        List<RepositoryDetailDto> dtos = gitClient.callGitHubApi(apiUri, uri, RepositoryDetailDto.class, HttpMethod.GET.name());
        return dtos;
    }

    @Override
    public List<RepositoryDetailDomain> mapRepositoriesToResponse(
            List<RepositoryDetailDto> repositoryDetailDtos) {
        return repositoryDetailDtos.stream()
                .map(repositoryDetailDto -> RepositoryDetailDomain.builder()
                        .name(repositoryDetailDto.getName())
                        .fullName(parseNull(repositoryDetailDto.getFullName()))
                        .description(parseNull(repositoryDetailDto.getDescription()))
                        .appHome(parseNull(repositoryDetailDto.getHomePage()))
                        .topics(StringUtils.joinWith(", ", repositoryDetailDto.getTopics()))
                        .repoUrl(parseNull(repositoryDetailDto.getHtmlUrl()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<RepositoryDetailDto> mapResponseToRepositories(
            List<RepositoryDetailDomain> repositoryDetailDomains) {
        return repositoryDetailDomains.stream()
                .map(repositoryDetailDomain -> RepositoryDetailDto.builder()
                        .name(repositoryDetailDomain.getName())
                        .fullName(parseNull(repositoryDetailDomain.getFullName()))
                        .description(parseNull(repositoryDetailDomain.getDescription()))
                        .homePage(parseNull(repositoryDetailDomain.getAppHome()))
                        .topics(StringUtils.isNotBlank(repositoryDetailDomain.getTopics()) ?
                                List.of(repositoryDetailDomain.getTopics()
                                        .substring(1, repositoryDetailDomain.getTopics().length() - 1)
                                        .split(", ")) : Collections.emptyList())
                        .htmlUrl(repositoryDetailDomain.getRepoUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<RepositoryIssueDto> getIssues(String userId, String repo) {
        // further end-point validation (contains User ID)
        String uri = propertyConfig.getGitApiEndpointIssues();
        Integer page = 1;
        Integer per_page = 100;
        String apiUri = UriComponentsBuilder.fromHttpUrl(uri)
                .buildAndExpand(userId, repo, per_page, page)
                .toUriString();
        if (StringUtils.isBlank(apiUri) | StringUtils.isBlank(uri)
                | !urlUtils.isValidURL(apiUri)) {
            warn("URL is not valid: url={}", apiUri);
            return Collections.emptyList();
        }
        return gitClient.callGitHubApi(apiUri, uri, RepositoryIssueDto.class, HttpMethod.GET.name());
    }

    @Override
    public List<RepositoryIssueDomain> mapIssuesToResponse(List<RepositoryIssueDto> repositoryIssueDtos) {
        return repositoryIssueDtos.stream()
                .map(repositoryIssueDto -> RepositoryIssueDomain.builder()
                        .number(repositoryIssueDto.getNumber())
                        .title(repositoryIssueDto.getTitle())
                        .body(repositoryIssueDto.getBody())
                        .build())
                .collect(Collectors.toList());
    }

    private String parseNull(String value) {
        return StringUtils.isBlank(value) ? "" : value;
    }
}
