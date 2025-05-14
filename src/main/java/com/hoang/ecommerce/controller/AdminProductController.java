package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.model.Product;
import com.hoang.ecommerce.request.CreateProductRequest;
import com.hoang.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts( @RequestParam(required = false) String category,
                                                         @RequestParam(required = false) List<String> color,
                                                         @RequestParam(required = false) List<String> size,
                                                         @RequestParam(required = false) Integer minPrice,
                                                         @RequestParam(required = false) Integer maxPrice,
                                                         @RequestParam(required = false) Integer minDiscount,
                                                         @RequestParam(defaultValue = "relevance") String sort,
                                                         @RequestParam(defaultValue = "all") String stock,
                                                         @RequestParam(defaultValue = "0") Integer pageNumber,
                                                         @RequestParam(defaultValue = "10") Integer pageSize){
        Page<Product> res = productService.getAllProduct(category,color,size,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize);

        System.out.println("Complete product");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/product")
    public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest request) {
        Product createdProduct = productService.createProduct(request);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PostMapping("/products")
    public ResponseEntity<List<Product>> createMultipleProducts(@RequestBody List<CreateProductRequest> requests) {
    List<Product> createdProducts = productService.createMultipleProducts(requests);
    return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
}
    


}
