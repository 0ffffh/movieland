package com.movieland.web.controller;


import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieRequestDto;
import com.movieland.entity.CurrencyType;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/movie")
public class MovieController {
    private static final String CURRENCY_DEFAULT_VALUE = "UAH";
    private static final String RANDOM_COUNT_DEFAULT_VALUE = "3";
    private final MovieService movieService;

    @GetMapping
    List<MovieDto> getAll(@RequestParam Map<String, String> param) {
        return movieService.findAll(param);
    }

    @GetMapping("/{id}")
    MovieDto getById(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = CURRENCY_DEFAULT_VALUE) CurrencyType currency) {

        return movieService.findById(id, currency);
    }

    @GetMapping("/random")
    List<MovieDto> getRandom(@RequestParam(required = false, defaultValue = RANDOM_COUNT_DEFAULT_VALUE) int count) {
        return movieService.findRandom(count);
    }

    @GetMapping("/genre/{id}")
    List<MovieDto> getByGenre(@PathVariable int id) {
        return movieService.findByGenre(id);
    }

    @PostMapping
    MovieDto add(@RequestBody MovieRequestDto movie) {
        return movieService.add(movie);
    }

    @PutMapping("{id}")
    MovieDto update(@PathVariable int id, @RequestBody MovieRequestDto movie) {
        return movieService.update(id, movie);
    }

}
