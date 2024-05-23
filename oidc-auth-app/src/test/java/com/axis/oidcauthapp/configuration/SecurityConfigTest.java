package com.axis.oidcauthapp.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OidcUserService axisUserService;

    @BeforeEach
    public void setup() {
        OidcIdToken idToken = new OidcIdToken(
            "tokenValue",
            null,
            null,
            Map.of("sub", "mock-user-id")
        );
        OidcUserInfo userInfo = new OidcUserInfo(Map.of(
            "sub", "mock-user-id",
            "given_name", "Mock",
            "family_name", "User"
        ));

        OidcUserAuthority authority = new OidcUserAuthority(idToken, userInfo);
        DefaultOidcUser principal = new DefaultOidcUser(Collections.singletonList(authority), idToken, userInfo);
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(principal, Collections.singletonList(authority), "axis");

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void givenNoAuth_whenAccessPrivate_thenRedirectToLogin() throws Exception {
        SecurityContextHolder.clearContext();
        mockMvc.perform(MockMvcRequestBuilders.get("/journal"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser
    @Test
    public void givenAuth_whenAccessPrivate_thenOk() throws Exception {
        SecurityContextHolder.clearContext();
        mockMvc.perform(MockMvcRequestBuilders.get("/journal"))
                .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void givenAuth_whenAccessCsrf_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/csrf")
                .with(SecurityMockMvcRequestPostProcessors.authentication(SecurityContextHolder.getContext().getAuthentication())))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAuth_whenPostJournalEntry_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/journal")
                .contentType("application/json")
                .content("{\"text\": \"A day in my life\"}")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.authentication(SecurityContextHolder.getContext().getAuthentication())))
                .andExpect(status().isOk());
    }
}

