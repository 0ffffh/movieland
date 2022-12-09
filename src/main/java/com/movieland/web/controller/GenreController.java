package com.movieland.web.controller;

import com.movieland.dto.GenreDto;
import com.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    List<GenreDto> getAll() {
        return genreService.findAll();
    }

}
