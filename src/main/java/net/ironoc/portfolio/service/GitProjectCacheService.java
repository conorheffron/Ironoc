package net.ironoc.portfolio.service;

import jakarta.annotation.PreDestroy;
import net.ironoc.portfolio.domain.RepositoryIssueDomain;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitProjectCacheService extends AbstractGitCache implements GitProjectCache {

    private final Map<String, List<RepositoryIssueDomain>> projectGitDetails;

    public GitProjectCacheService() {
        this.projectGitDetails = new HashMap<>();
    }

    @Override
    public void put(String userId, String project, List<RepositoryIssueDomain> repositoryIssueDomains) {
        projectGitDetails.put(userId + project, repositoryIssueDomains);
    }

    @Override
    public List<RepositoryIssueDomain> get(String userId, String project) {
        return projectGitDetails.get(userId + project);
    }

    @Override
    public void remove(String key) {
        projectGitDetails.remove(key);
    }

    @PreDestroy
    public void tearDown() {
        this.clear(projectGitDetails);
    }
}
