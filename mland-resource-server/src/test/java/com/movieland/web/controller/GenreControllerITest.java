package com.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.movieland.AbstractWebITest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@AutoConfigureMockMvc(addFilters = false)
class GenreControllerITest extends AbstractWebITest {

    @Test
    @DisplayName("Get all genres")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/genre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/genre/getAll_response.json")));
    }
}