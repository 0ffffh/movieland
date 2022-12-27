package com.movieland.repository;

import com.movieland.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, CustomMovieRepository {
    List<Movie> findByGenres_Id_GenreId(int genreId);

    @Query("""
    select m from Movie m where
    lower(m.nameNative) like lower(CONCAT('%',?1,'%')) or
    lower(m.nameRu) like lower(CONCAT('%',?1,'%'))""")
    List<Movie> findByTitle(String title, Pageable pageable);

}