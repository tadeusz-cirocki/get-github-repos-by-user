package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.BranchInfo;
import com.example.demo.model.GitHubBranch;
import com.example.demo.model.GitHubRepository;
import com.example.demo.model.RepositoryInfo;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {
    @Value("${github.api.url}")
    private String githubApiUrl;

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryInfo> getRepositories(String username) {
        try {
            String url = githubApiUrl + "/users/" + username + "/repos";
            GitHubRepository[] githubRepositories = restTemplate.getForObject(url, GitHubRepository[].class);

            if (githubRepositories != null) {
                List<RepositoryInfo> repositories = new ArrayList<>();
                for (GitHubRepository githubRepository : githubRepositories) {
                    if (!githubRepository.isFork()) {
                        RepositoryInfo repositoryInfo = new RepositoryInfo();
                        repositoryInfo.setRepositoryName(githubRepository.getName());
                        repositoryInfo.setOwnerLogin(githubRepository.getOwner().getLogin());
                        repositoryInfo.setBranches(getBranches(username, githubRepository.getName()));
                        repositories.add(repositoryInfo);
                    }
                }
                return repositories;
            } else {
                return new ArrayList<>();
            }
        } catch (HttpClientErrorException.NotFound e) {
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error", e);
        }
    }

    private List<BranchInfo> getBranches(String username, String repositoryName) {
        try {
            String url = githubApiUrl + "/repos/" + username + "/" + repositoryName + "/branches";
            GitHubBranch[] githubBranches = restTemplate.getForObject(url, GitHubBranch[].class);

            if (githubBranches != null) {
                List<BranchInfo> branches = new ArrayList<>();
                for (GitHubBranch githubBranch : githubBranches) {
                    BranchInfo branchInfo = new BranchInfo();
                    branchInfo.setName(githubBranch.getName());
                    branchInfo.setLastCommitSha(githubBranch.getCommit().getSha());
                    branches.add(branchInfo);
                }
                return branches;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal Server Error", e);
        }
    }
}
