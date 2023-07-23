# OAuth2 Authentication Server Implementation using Spring Boot new Authorization Server & MongoDb as Database

This project demonstrates the implementation of an OAuth2 authentication server using Spring Boot. The authentication server allows clients to authenticate and obtain access tokens for accessing protected resources.

## Features

- Provides OAuth2 endpoints for client authentication and token issuance.
- Supports multiple grant types, including authorization code, client credentials, and refresh token.
- Uses an in-memory client store and user store for simplicity.
- Secures endpoints using Spring Security and OAuth2 security configurations.
- Uses JSON Web Tokens (JWT) for access token generation and validation.
- Includes sample REST APIs for testing token-based authentication.

## Prerequisites

Before running the application, ensure that the following software is installed on your system:

- Java Development Kit (JDK) 17 or higher
- Gradle 6.0 or higer (for building and running the application)
- MongoDB (for storing client and user details)

