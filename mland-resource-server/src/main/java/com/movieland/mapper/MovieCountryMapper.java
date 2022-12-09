package com.movieland.mapper;

import com.movieland.dto.CountryDto;
import com.movieland.entity.MovieCountry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieCountryMapper {

    @Mapping(target = "id", source = "country.id")
    @Mapping(target = "name", source = "country.name")
    CountryDto countryToCountryDto(MovieCountry country);

}
