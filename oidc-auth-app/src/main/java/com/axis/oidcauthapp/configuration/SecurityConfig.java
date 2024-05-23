package com.axis.oidcauthapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      http
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/user"),
                    new AntPathRequestMatcher("/error")
                ).permitAll()
                .anyRequest().authenticated()
            )
        .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("http://localhost:3000", true)
                .userInfoEndpoint(userInfo -> userInfo.oidcUserService(axisUserService()))
                .redirectionEndpoint(cfg -> cfg.baseUri("/testapp/login/oauth2/code/*"))
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
        )
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );

      return http.build();
    }

    @Bean
    public OidcUserService axisUserService() {
        return new OidcUserService();
    }
}
