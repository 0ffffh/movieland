package com.movieland.mapper;

import com.movieland.dto.GenreDto;
import com.movieland.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto genreToGenreDto(Genre genre);

    List<GenreDto> genreToGenreDtoList(List<Genre> genre);

}
