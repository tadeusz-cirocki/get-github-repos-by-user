# GitHub Repository Listing API

This is a Spring Boot-based RESTful API that allows you to list GitHub repositories for a given user.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17 or later installed.
- [Spring Boot](https://spring.io/projects/spring-boot) framework.
- [Maven](https://maven.apache.org/) for managing dependencies.

## Getting Started

To get started with this project, follow these steps:

1. Clone this repository to your local machine and navigate to the project directory.

2. Build the project using Maven:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

The API will start running locally at `http://localhost:8080`.

## Usage

To use the API, make a GET request to the following endpoint:

```
GET http://localhost:8080/github/repositories?username={GitHubUsername}
```

- Replace `{GitHubUsername}` with the GitHub username for which you want to list repositories.

The API will respond with a JSON object containing a list of GitHub repositories that are not forks, along with their branch information.

### Request Headers

- `Accept: application/json` - To receive a JSON response.

### Response Example

```json
[
  {
    "repositoryName": "example-repo",
    "ownerLogin": "your-username",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "a1b2c3d4"
      },
      {
        "name": "feature-branch",
        "lastCommitSha": "e5f6g7h8"
      }
    ]
  },
  // Additional repositories...
]
```

### Handling Errors

- If the GitHub user is not found, you will receive a 404 response with the message: "GitHub user not found."
- If there is an internal server error, you will receive a 500 response with an error message.

## Configuration

The project uses external configuration for the GitHub API URL. You can configure it in the `application.properties` file.

```properties
github.api.url=https://api.github.com
```

## Acknowledgments

- This project is based on the GitHub API.
