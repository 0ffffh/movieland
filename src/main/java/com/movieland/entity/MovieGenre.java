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

@Entity(name = "movies_genres")
@Table(name = "movies_genres")
@NoArgsConstructor
@Getter
@Setter
public class MovieGenre {

    public MovieGenre(Movie movie, Genre genre) {
        this.id = new MovieGenreId(movie.getId(), genre.getId());
        this.movie = movie;
        this.genre = genre;
    }

    @EmbeddedId
    private MovieGenreId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @ToString.Exclude
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genreId")
    @ToString.Exclude
    private Genre genre;

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @Data
    public static class MovieGenreId implements Serializable {
        private int movieId;
        private int genreId;
    }

}
