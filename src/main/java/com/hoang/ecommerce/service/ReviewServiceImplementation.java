package com.hoang.ecommerce.service;

import com.hoang.ecommerce.exception.ProductException;
import com.hoang.ecommerce.model.Product;
import com.hoang.ecommerce.model.Review;
import com.hoang.ecommerce.model.User;
import com.hoang.ecommerce.repository.ProductRepository;
import com.hoang.ecommerce.repository.ReviewRepository;
import com.hoang.ecommerce.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReviewServiceImplementation  implements ReviewService{

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository,ProductService productService,ProductRepository productRepository){
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }
    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(req.getReview());
        review.setCreateAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
