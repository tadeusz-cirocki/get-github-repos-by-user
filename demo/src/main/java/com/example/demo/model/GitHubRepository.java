package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository {
    private long id;
    private String name;
    private String full_name;
    private boolean isPrivate;
    private Owner owner;
    
}
