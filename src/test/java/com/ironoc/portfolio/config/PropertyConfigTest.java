package com.ironoc.portfolio.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PropertyConfigTest {

    @InjectMocks
    private PropertyConfig propertyConfig;

    @Mock
    private Environment environmentMock;

    @Mock
    private PropertyKeyI propertyKeyMock;

    private static final String TEST_PROP_VAL = "test_val";

    @Test
    public void test_getGitApiEndpoint_success() {
        // given
        when(propertyKeyMock.getGitApiEndpoint()).thenReturn(Properties.GIT_API_ENDPOINT.getKey());
        when(environmentMock.getRequiredProperty(Properties.GIT_API_ENDPOINT.getKey())).thenReturn(TEST_PROP_VAL);

        // when
        String result = propertyConfig.getGitApiEndpoint();

        // then
        verify(propertyKeyMock).getGitApiEndpoint();
        verify(environmentMock).getRequiredProperty(Properties.GIT_API_ENDPOINT.getKey());

        assertThat(result, is(TEST_PROP_VAL));
    }

    @Test
    public void test_getGitToken_success() {
        // given
        when(propertyKeyMock.getGitToken()).thenReturn(Properties.GIT_TOKEN.getKey());
        when(environmentMock.getProperty(Properties.GIT_TOKEN.getKey(), "")).thenReturn(TEST_PROP_VAL);

        // when
        String result = propertyConfig.getGitToken();

        // then
        verify(propertyKeyMock).getGitToken();
        verify(environmentMock).getProperty(Properties.GIT_TOKEN.getKey(), "");

        assertThat(result, is(TEST_PROP_VAL));
    }

    @Test
    public void test_getGitReposUri_success() {
        // given
        when(propertyKeyMock.getGitReposUri()).thenReturn(Properties.GIT_REPOS_URI.getKey());
        when(environmentMock.getRequiredProperty(Properties.GIT_REPOS_URI.getKey())).thenReturn(TEST_PROP_VAL);

        // when
        String result = propertyConfig.getGitReposUri();

        // then
        verify(propertyKeyMock).getGitReposUri();
        verify(environmentMock).getRequiredProperty(Properties.GIT_REPOS_URI.getKey());

        assertThat(result, is(TEST_PROP_VAL));
    }

    @Test
    public void test_getGitFollowRedirects_success() {
        // given
        when(propertyKeyMock.getGitFollowRedirects()).thenReturn(Properties.GIT_FOLLOW_REDIRECTS.getKey());
        when(environmentMock.getRequiredProperty(Properties.GIT_FOLLOW_REDIRECTS.getKey())).thenReturn("true");

        // when
        Boolean result = propertyConfig.getGitFollowRedirects();

        // then
        verify(propertyKeyMock).getGitFollowRedirects();
        verify(environmentMock).getRequiredProperty(Properties.GIT_FOLLOW_REDIRECTS.getKey());

        assertThat(result, is(Boolean.TRUE));
    }

    @Test
    public void test_getGitTimeoutRead_success() {
        // given
        when(propertyKeyMock.getGitTimeoutRead()).thenReturn(Properties.GIT_TIMEOUT_READ.getKey());
        when(environmentMock.getRequiredProperty(Properties.GIT_TIMEOUT_READ.getKey())).thenReturn("500");

        // when
        Integer result = propertyConfig.getGitTimeoutRead();

        // then
        verify(propertyKeyMock).getGitTimeoutRead();
        verify(environmentMock).getRequiredProperty(Properties.GIT_TIMEOUT_READ.getKey());

        assertThat(result, is(500));
    }
    @Test
    public void test_getGitTimeoutConnect_success() {
        // given
        when(propertyKeyMock.getGitTimeoutConnect()).thenReturn(Properties.GIT_TIMEOUT_CONNECT.getKey());
        when(environmentMock.getRequiredProperty(Properties.GIT_TIMEOUT_CONNECT.getKey())).thenReturn("631");

        // when
        Integer result = propertyConfig.getGitTimeoutConnect();

        // then
        verify(propertyKeyMock).getGitTimeoutConnect();
        verify(environmentMock).getRequiredProperty(Properties.GIT_TIMEOUT_CONNECT.getKey());

        assertThat(result, is(631));
    }

    @Test
    public void test_getGitInstanceFollowRedirects_success() {
        // given
        when(propertyKeyMock.getGitInstanceFollowRedirects()).thenReturn(Properties.GIT_INSTANCE_FOLLOW_REDIRECTS.getKey());
        when(environmentMock.getRequiredProperty(Properties.GIT_INSTANCE_FOLLOW_REDIRECTS.getKey())).thenReturn("false");

        // when
        Boolean result = propertyConfig.getGitInstanceFollowRedirects();

        // then
        verify(propertyKeyMock).getGitInstanceFollowRedirects();
        verify(environmentMock).getRequiredProperty(Properties.GIT_INSTANCE_FOLLOW_REDIRECTS.getKey());

        assertThat(result, is(Boolean.FALSE));
    }
}