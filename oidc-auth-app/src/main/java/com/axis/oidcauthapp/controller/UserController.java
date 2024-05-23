package com.axis.oidcauthapp.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<OidcUser> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
      if(Optional.ofNullable(oidcUser).isPresent()){
        return ResponseEntity.ok(oidcUser);
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    }
}

