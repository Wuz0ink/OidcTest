package com.axis.oidcauthapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import com.axis.oidcauthapp.repository.JournalEntryRepository;
import com.axis.oidcauthapp.repository.entity.JournalEntry;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @PostMapping
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry, @AuthenticationPrincipal OidcUser oidcUser) {
        String userId = oidcUser.getSubject();
        journalEntry.setUserId(userId);
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journalEntry);
    }

    @GetMapping
    public List<JournalEntry> getJournalEntries(@AuthenticationPrincipal OidcUser oidcUser) {
        String userId = oidcUser.getSubject();
        return journalEntryRepository.findByUserId(userId);
    }
}

