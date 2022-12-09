package com.movieland.mapper;

import com.movieland.dto.CountryDto;
import com.movieland.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto countryToCountryDto(Country country);

    List<CountryDto> countriesToCountryDtoList(List<Country> countries);

}
