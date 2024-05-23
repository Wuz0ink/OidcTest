# OIDC Authentication Application

This project demonstrates how to integrate OIDC authentication with a Spring Boot backend and a React frontend.

# Project Structure
  1. oidc-auth-app: This directory contains the Spring Boot backend application.
  2. journal-app: This directory contains the React frontend application.

## Setup Instructions

1. Clone the repository:

   ```sh
   git clone https://github.com/Wuz0ink/OidcTest.git
   cd OidcTest

2. Create a .env file in the root of the oidc-auth-app directory with the following content:
   ```sh
    CLIENT_ID=your_client_id
    CLIENT_SECRET=your_client_secret
    REDIRECT_URI=redirect_uri
    AUTHORIZATION_URI=authorization_uri
    TOKEN_URI=token_uri
    USER_INFO_URI=user_info_uri
    JWK_SET_URI=jwk_set_uri
    DATASOURCE_URL=h2_datasource
    DATASOURCE_USERNAME=datasource_username
    DATASOURCE_PASSWORD=datasource_password

3. Navigate into the oidc-auth-app directory and run the Spring Boot application:
   ```sh
   cd oidc-auth-app
   ./gradlew bootRun

4. Open a new terminal, navigate into the journal-app directory and start the React application:
   ```sh
    cd journal-app
    npm install
    npm start

5. Open your browser and navigate to http://localhost:3000 to access the application.
