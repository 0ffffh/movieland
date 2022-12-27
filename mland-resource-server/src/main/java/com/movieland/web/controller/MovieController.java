package com.movieland.web.controller;


import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieRequestDto;
import com.movieland.entity.CurrencyType;
import com.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/movie")
public class MovieController {
    private static final String CURRENCY_DEFAULT_VALUE = "UAH";
    private static final String RANDOM_COUNT_DEFAULT_VALUE = "3";
    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String PAGE_SIZE_DEFAULT_VALUE = "5";
    private final MovieService movieService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    List<MovieDto> getAll(@RequestParam Map<String, String> param) {
        return movieService.findAll(param);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    MovieDto getById(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = CURRENCY_DEFAULT_VALUE) CurrencyType currency) {

        return movieService.findById(id, currency);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/random")
    List<MovieDto> getRandom(@RequestParam(required = false, defaultValue = RANDOM_COUNT_DEFAULT_VALUE) int count) {
        return movieService.findRandom(count);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/genre/{id}")
    List<MovieDto> getByGenre(@PathVariable int id) {
        return movieService.findByGenre(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    MovieDto add(@RequestBody MovieRequestDto movie) {
        return movieService.add(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    MovieDto update(@PathVariable int id, @RequestBody MovieRequestDto movie) {
        return movieService.update(id, movie);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/search")
    List<MovieDto> search(@RequestParam String title,
                          @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,
                          @RequestParam(required = false, defaultValue = PAGE_SIZE_DEFAULT_VALUE) int size){
        return movieService.findByTitle(title, PageRequest.of(page, size));
    }

}
