package com.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "movies_countries")
@Getter
@Setter
@NoArgsConstructor
public class MovieCountry {

    public MovieCountry(Movie movie, Country country) {
        this.id = new MovieCountryId(movie.getId(), country.getId());
        this.movie = movie;
        this.country = country;
    }

    @EmbeddedId
    private MovieCountryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @ToString.Exclude
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("countryId")
    @ToString.Exclude
    private Country country;


    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @Data
    public static class MovieCountryId implements Serializable {
        private int movieId;
        private int countryId;
    }
}
