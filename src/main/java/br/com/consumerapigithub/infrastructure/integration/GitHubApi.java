package br.com.consumerapigithub.infrastructure.integration;

import br.com.consumerapigithub.app.dto.CommitsDTO;
import br.com.consumerapigithub.app.dto.IssuesDTO;
import br.com.consumerapigithub.app.dto.OrganizationRepoDTO;
import br.com.consumerapigithub.app.dto.RepositoriesResponseDTO;
import br.com.consumerapigithub.infrastructure.utils.ConstantesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
@Slf4j
public class GitHubApi {

    private final RestTemplate restTemplate;

    @Value(value = "${github.url}")
    private String url;

    @Value(value = "${github.token}")
    private String token;

    public OrganizationRepoDTO getOrganizationRepositories() {
        return restTemplate.exchange(url, HttpMethod.GET, this.getHttpEntity(), OrganizationRepoDTO.class).getBody();
    }

    public List<RepositoriesResponseDTO> getRepositories(String url, String org) {
        RepositoriesResponseDTO[] response = restTemplate.exchange(this.buildUrl(url, org).concat("/repos"), HttpMethod.GET, this.getHttpEntity(), RepositoriesResponseDTO[].class).getBody();
        return List.of(Objects.requireNonNull(response));
    }

    public List<CommitsDTO> getCommits(String url) {
        CommitsDTO[] response = new CommitsDTO[0];
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, this.getHttpEntity(), CommitsDTO[].class).getBody();
        } catch (HttpClientErrorException ex) {
            log.warn(ex.getMessage());
            return ex.getStatusCode().value() == 409 ? List.of(Objects.requireNonNull(response)) : null;
        }
        return List.of(Objects.requireNonNull(response));
    }

    public IssuesDTO getIssues(String url, String fullName) {
        var isssueUrl = url.concat("repo:" + fullName).concat("+type:issue+state:open");
        return restTemplate.exchange(isssueUrl, HttpMethod.GET, this.getHttpEntity(), IssuesDTO.class).getBody();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ConstantesUtils.AUTHORIZATION, "token ".concat(token));
        return new HttpEntity<>("", headers);
    }

    private String buildUrl(String url, String param) {
        return UriComponentsBuilder.fromUri(URI.create(url)).path(param).encode().toUriString();
    }

}
