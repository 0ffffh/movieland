package com.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.movieland.AbstractWebITest;
import com.movieland.configuration.TestSecurityDisableConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@AutoConfigureMockMvc(addFilters = false)
@Import({TestSecurityDisableConfig.class})
@WithMockUser(roles = "ADMIN")
class MovieControllerITest extends AbstractWebITest {

    @Test
    @DisplayName("Get all movies")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/movie")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/getAll_response.json")));
    }

    @Test
    @DisplayName("Get by id")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void getById() throws Exception {
        mockMvc.perform(get("/api/v1/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/getById_response.json")));
    }


    @Test
    @DisplayName("Get random")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void getRandom() throws Exception {
        mockMvc.perform(get("/api/v1/movie/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get movies by genre")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void getByGenre() throws Exception {
        mockMvc.perform(get("/api/v1/movie/genre/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/getByGenre_response.json")));
    }

    @Test
    @DisplayName("Get movies sorted by ")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"})
    void getSortedByRating() throws Exception {
        mockMvc.perform(get("/api/v1/movie")
                        .requestAttr("rating", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/sortByRatingASC_response.json")));

        mockMvc.perform(get("/api/v1/movie")
                        .requestAttr("rating", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/sortByRatingDESC_response.json")));
    }

    @Test
    @DisplayName("Add movie")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"},
            executeStatementsBefore = "ALTER SEQUENCE movie_id_sequence RESTART WITH 1000;"
    )
    void add() throws Exception {
        mockMvc.perform(post("/api/v1/movie")
                        .content(getResponseAsString("datasets/controller/movie/add_requestBody.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/add_response.json")));
    }

    @Test
    @DisplayName("Edit movie")
    @DataSet(value = "datasets/sql_dump.json", cleanBefore = true, skipCleaningFor = {"flyway_schema_history"}
    )
    void edit() throws Exception {
        mockMvc.perform(get("/api/v1/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/getById_response.json")));

        mockMvc.perform(put("/api/v1/movie/1")
                        .content(getResponseAsString("datasets/controller/movie/update_requestBody.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(getResponseAsString("datasets/controller/movie/update_response.json")));
    }
}