package com.movieland.service.impl;

import com.movieland.dto.ReviewDto;
import com.movieland.entity.Review;
import com.movieland.mapper.ReviewMapper;
import com.movieland.repository.ReviewRepository;
import com.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultReviewService implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> findByMovieId(int id) {
        List<Review> reviews = reviewRepository.findByMovie_Id(id);
        log.info("Find {} reviews for movie {}", reviews.size(), id);
        return reviewMapper.reviewsToReviewDtoList(reviewRepository.findByMovie_Id(id));
    }
}
