package com.movieland.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "movie")
@Getter
@Setter
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq_generator")
    @SequenceGenerator(name = "movie_seq_generator", sequenceName = "movie_id_sequence")
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name_native", nullable = false, length = 100)
    private String nameNative;
    @Column(name = "name_ru", nullable = false, length = 100)
    private String nameRu;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "rating", precision = 5, scale = 2)
    private double rating;
    @Column(name = "price", precision = 5, scale = 2)
    private double price;
    @Column(name = "picture_path", nullable = false, length = 2000)
    private String picturePath;
    @Column(name = "votes")
    private int votes;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieGenre> genres = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieCountry> countries = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public void updateCountries(List<Country> countries) {
        List<Country> countryList = Optional.ofNullable(countries)
                .orElse(Collections.emptyList());
        List<MovieCountry> movieCountries = countryList.stream()
                .map(country -> new MovieCountry(this, country)).toList();
        this.countries.clear();
        this.countries.addAll(movieCountries);
    }

    public void updateGenres(List<Genre> genres) {
        List<Genre> genreList = Optional.ofNullable(genres)
                .orElse(Collections.emptyList());
        List<MovieGenre> movieGenres = genreList.stream()
                .map(genre -> new MovieGenre(this, genre)).toList();
        this.genres.clear();
        this.genres.addAll(movieGenres);
    }

}
