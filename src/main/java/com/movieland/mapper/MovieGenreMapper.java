package com.movieland.mapper;

import com.movieland.dto.GenreDto;
import com.movieland.entity.MovieGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieGenreMapper {

    @Mapping(target = "id", source = "genre.id")
    @Mapping(target = "name", source = "genre.name")
    GenreDto genreToGenreDto(MovieGenre genre);

}
