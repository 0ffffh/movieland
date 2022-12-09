package com.movieland.mapper;

import com.movieland.dto.MovieDto;
import com.movieland.dto.MovieRequestDto;
import com.movieland.entity.Movie;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring", uses = {MovieCountryMapper.class, MovieGenreMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {

    @Named(value = "short")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "yearOfRelease", source = "releaseDate")
    MovieDto movieToShortMovieDto(Movie movie);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "yearOfRelease", source = "releaseDate")
    MovieDto movieToMovieDto(Movie movie);

    @Mapping(target = "yearOfRelease", source = "releaseDate")
    MovieDto toMovieDto(Movie movie);

    @IterableMapping(qualifiedByName = "short")
    List<MovieDto> moviesToShortMovieDtoList(List<Movie> movies);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "releaseDate", source = "yearOfRelease", qualifiedByName = "getDate")
    Movie addMovie(MovieRequestDto movieDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "releaseDate", source = "yearOfRelease", qualifiedByName = "getDate")
    void update(@MappingTarget Movie movie, MovieRequestDto movieRequestDto);

    @Named(value = "getDate")
    default LocalDate getDate(String date) {
        return LocalDate.of(Integer.parseInt(date), 1, 1);
    }

}

