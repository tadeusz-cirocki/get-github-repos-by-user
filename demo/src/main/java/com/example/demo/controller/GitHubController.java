package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.RepositoryInfo;
import com.example.demo.service.GitHubService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/github")
@Validated
public class GitHubController {
    private final GitHubService gitHubService;

    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/repositories")
    public ResponseEntity<Object> listRepositories(
            @RequestParam @NotEmpty(message = "Username cannot be empty") String username,
            @RequestHeader("Accept") String acceptHeader
    ) {
        if (!acceptHeader.equals("application/json")) {
            return ResponseEntity.status(406).body("Unsupported Media Type");
        }

        try {
            List<RepositoryInfo> repositories = gitHubService.getRepositories(username);

            if (repositories.isEmpty()) {
                return ResponseEntity.status(404).body("GitHub user not found");
            } else {
                return ResponseEntity.ok(repositories);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
