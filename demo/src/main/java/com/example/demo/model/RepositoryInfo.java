package com.example.demo.model;

import java.util.List;
import lombok.Data;

@Data
public class RepositoryInfo {
    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;
}
