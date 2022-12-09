package com.movieland.service;

import com.movieland.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> findByMovieId(int id);
}
