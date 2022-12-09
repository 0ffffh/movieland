package com.movieland.service.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.movieland.AbstractBaseITest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DBRider
class UserDetailsServiceImpTest extends AbstractBaseITest {
    @Autowired
    private UserDetailsServiceImp service;

    @Test
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void loadUserByUsername() {
        UserDetails userDetails = service.loadUserByUsername("admin@admin.com");
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.stream().findFirst().get().getAuthority());
    }
}