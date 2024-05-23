package com.axis.oidcauthapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.oidcauthapp.repository.entity.JournalEntry;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(String userId);
}

