package com.hoang.ecommerce.controller;

import com.hoang.ecommerce.exception.ProductException;
import com.hoang.ecommerce.model.Product;
import com.hoang.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, @RequestParam List<String> color,
                                                                      @RequestParam List<String> size,@RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice,@RequestParam Integer minDiscount,
                                                                      @RequestParam String sort,@RequestParam String stock,
                                                                      @RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        Page<Product> res = productService.getAllProduct(category,color,size,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize);

        System.out.println("Complete product");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
//    @GetMapping("/products")
//    public ResponseEntity<Page<Product>> getAllProducts( @RequestParam(required = false) String category,
//                                                         @RequestParam(required = false) List<String> color,
//                                                         @RequestParam(required = false) List<String> size,
//                                                         @RequestParam(required = false) Integer minPrice,
//                                                         @RequestParam(required = false) Integer maxPrice,
//                                                         @RequestParam(required = false) Integer minDiscount,
//                                                         @RequestParam(defaultValue = "relevance") String sort,
//                                                         @RequestParam(defaultValue = "all") String stock,
//                                                         @RequestParam(defaultValue = "0") Integer pageNumber,
//                                                         @RequestParam(defaultValue = "10") Integer pageSize){
//        Page<Product> res = productService.getAllProduct(category,color,size,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize);
//
//        System.out.println("Complete product");
//        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//    }


    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product>findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);
        return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
    }

//    @GetMapping("/products/search")
//    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){
//        List<Product> products = productService.searchProduct(q);
//        return new ResponseEntity<List<Product>>(HttpStatus.OK)
//    }
}
