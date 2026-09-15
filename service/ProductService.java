package com.slp.order_pricing_service.service;

import com.slp.order_pricing_service.dto.request.CreateProductRequest;
import com.slp.order_pricing_service.dto.request.UpdateProductRequest;
import com.slp.order_pricing_service.dto.response.ProductResponse;
import com.slp.order_pricing_service.entity.Product;
import com.slp.order_pricing_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCacheService productCacheService;

    public ProductService(ProductRepository productRepository, ProductCacheService productCacheService){
        this.productRepository = productRepository;
        this.productCacheService = productCacheService;
    }

    public ProductResponse createProduct(CreateProductRequest request){
        Product product = new Product();

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id){
        ProductResponse cacheProduct = productCacheService.get(id);

        if(cacheProduct != null){
            return cacheProduct;
        }
        Product product = productRepository.findById(id).orElseThrow(()->{
            return new RuntimeException("Product not found with id: "+id);
        });

        ProductResponse response = mapToResponse(product);
        productCacheService.save(response);
        return response;
    }

    public List<ProductResponse> getAllProduct(){
        List<Product> getAllProd = productRepository.findAll();

        List<ProductResponse> ans = new ArrayList<>();

        for(int i=0;i<getAllProd.size();i++){
            Product product = getAllProd.get(i);
            ProductResponse response = mapToResponse(product);
            ans.add(response);
        }

        return ans;
    }

    public ProductResponse UpdateProduct(Long id, UpdateProductRequest request){
        Product existingProd = productRepository.findById(id).orElseThrow(()->{
            return new RuntimeException("Product not found ");
        });

        existingProd.setName(request.getName());
        existingProd.setPrice(request.getPrice());
        existingProd.setStock(request.getStock());
        existingProd.setDescription(request.getCategory());
        existingProd.setCategory(request.getCategory());

        existingProd.setUpdatedAt(LocalDateTime.now());

        Product updatedProd = productRepository.save(existingProd);

        productCacheService.delete(id);

        return mapToResponse(updatedProd);
    }

    public void deleteProduct(Long id){
        Product existingProd = productRepository.findById(id).orElseThrow(()->{
            return new RuntimeException("Product not found");
        });

        productRepository.delete(existingProd);
        productCacheService.delete(id);


    }

    public ProductResponse mapToResponse(Product product){
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;

    }
}
