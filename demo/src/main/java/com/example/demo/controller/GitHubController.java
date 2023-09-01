package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotEmpty;

import com.example.demo.service.GitHubService;

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
            ResponseEntity<String> responseEntity = gitHubService.getRepositories(username);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                return ResponseEntity.ok(processGitHubResponse(responseBody));
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                return ResponseEntity.status(404).body("GitHub user not found");
            } else {
                return ResponseEntity.status(500).body("Internal Server Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    private String processGitHubResponse(String responseBody) {
        return responseBody;
    }
}
