package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.ProductException;
import com.hoang.ecommerce.model.Rating;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
