package br.com.consumerapigithub.app.service.impl;

import br.com.consumerapigithub.app.dto.GitHubResponseDTO;
import br.com.consumerapigithub.app.dto.OrganizationRepoDTO;
import br.com.consumerapigithub.app.dto.RepositoriesResponseDTO;
import br.com.consumerapigithub.app.service.GitHubService;
import br.com.consumerapigithub.entity.Respository;
import br.com.consumerapigithub.infrastructure.integration.GitHubApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.com.consumerapigithub.infrastructure.utils.ConstantesUtils.*;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GitHubService {

    private final GitHubApi gitHubApi;

    @Override
    public GitHubResponseDTO findRepositoriesByOrganization(String org) {
        return new GitHubResponseDTO(this.getRespositories(org));
    }

    private List<Respository> getRespositories(String organizationName) {
        OrganizationRepoDTO organizationRepo = gitHubApi.getOrganizationRepositories();
        var urlRepo = organizationRepo.getOrganization_repositories_url().replace(FORMAT_URL_ORG_REPO, StringUtils.EMPTY);
        List<RepositoriesResponseDTO> repositoriesResponse = gitHubApi.getRepositories(urlRepo, organizationName);
        List<Respository> respositories = new ArrayList<>();
        for (var repos : repositoriesResponse) {
            if (repos != null) {
                var urlCommits = repos.getCommits_url().replace(FORMAT_URL_COMMIT, "?per_page=100");
                var issueUrl = organizationRepo.getIssue_search_url().replace(FORMAT_URL_ISSUE, StringUtils.EMPTY);
                var issue = gitHubApi.getIssues(issueUrl, repos.getFull_name());
                var commits = gitHubApi.getCommits(urlCommits);
                var count_commits = commits != null ? commits.size() : 0;
                if (count_commits > 40 && issue.getTotal_count() >= 1) {
                    Respository respository = this.buildRespositoriesDTO(repos, count_commits, issue.getTotal_count());
                    respositories.add(respository);
                }
            }
        }
        return respositories;
    }

    private Respository buildRespositoriesDTO(RepositoriesResponseDTO repos, int count_commits, int count_issues) {
        return Respository.builder()
                .organization(repos.getOwner().getLogin())
                .full_name(repos.getName())
                .count_commits(count_commits)
                .count_open_issues(count_issues)
                .topics(repos.getTopics())
                .build();
    }
}
