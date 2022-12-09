package com.movieland.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    private String nameNative;
    private String nameRu;
    private String yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;

    @JsonProperty("countries")
    private List<Integer> countryIds;
    @JsonProperty("genres")
    private List<Integer> genreIds;

    public List<Integer> getCountryIds() {
        if (Objects.nonNull(countryIds)) {
            return countryIds;
        }
        return Collections.emptyList();
    }

    public List<Integer> getGenreIds() {
        if (Objects.nonNull(genreIds)) {
            return genreIds;
        }
        return Collections.emptyList();

    }
}
