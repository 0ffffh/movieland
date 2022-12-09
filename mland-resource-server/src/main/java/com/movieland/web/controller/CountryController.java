package com.movieland.web.controller;

import com.movieland.dto.CountryDto;
import com.movieland.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    List<CountryDto> getAll() {
        return countryService.findAll();
    }
}
