package com.movieland.mapper;

import com.movieland.dto.ReviewDto;
import com.movieland.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto reviewToReviewDto(Review review);

    List<ReviewDto> reviewsToReviewDtoList(List<Review> review);

}
