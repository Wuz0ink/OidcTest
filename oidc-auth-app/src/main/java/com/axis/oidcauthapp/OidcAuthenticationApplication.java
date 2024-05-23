package com.axis.oidcauthapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class OidcAuthenticationApplication {

	public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();

    System.setProperty("CLIENT_ID", dotenv.get("CLIENT_ID"));
    System.setProperty("CLIENT_SECRET", dotenv.get("CLIENT_SECRET"));
    System.setProperty("REDIRECT_URI", dotenv.get("REDIRECT_URI"));
    System.setProperty("AUTHORIZATION_URI", dotenv.get("AUTHORIZATION_URI"));
    System.setProperty("TOKEN_URI", dotenv.get("TOKEN_URI"));
    System.setProperty("USER_INFO_URI", dotenv.get("USER_INFO_URI"));
    System.setProperty("JWK_SET_URI", dotenv.get("JWK_SET_URI"));
    System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
    System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
    System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));

		SpringApplication.run(OidcAuthenticationApplication.class, args);
	}

}
