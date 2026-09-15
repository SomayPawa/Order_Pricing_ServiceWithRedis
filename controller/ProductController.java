package com.slp.order_pricing_service.controller;

import com.slp.order_pricing_service.dto.request.CreateProductRequest;
import com.slp.order_pricing_service.dto.request.UpdateProductRequest;
import com.slp.order_pricing_service.dto.response.ProductResponse;
import com.slp.order_pricing_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request){
        ProductResponse productResponse = productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> AllProduct = productService.getAllProduct();
        return ResponseEntity.ok(AllProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> UpdateProduct(@PathVariable Long id, @Valid @RequestBody
                                                         UpdateProductRequest request){
        ProductResponse productResponse = productService.UpdateProduct(id,request);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
