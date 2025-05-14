package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.ProductException;
import com.hoang.ecommerce.model.Review;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
