package com.movieland.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private long id;
    private String nameNative;
    private String nameRu;
    private double rating;
    private double price;
    private String picturePath;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private LocalDate yearOfRelease;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CountryDto> countries;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<GenreDto> genres;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ReviewDto> reviews;

}
