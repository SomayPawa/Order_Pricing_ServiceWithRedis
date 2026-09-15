package com.slp.order_pricing_service.service;

import com.slp.order_pricing_service.dto.response.ProductResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
public class ProductCacheService {
    public static final String PRODUCT_KEY_PREFIX = "product:";

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper; // convert object -> Json or json to object

    public ProductCacheService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void save(ProductResponse productResponse){
        String key = PRODUCT_KEY_PREFIX + productResponse.getId();

        try{
            String json = objectMapper.writeValueAsString(productResponse);

            redisTemplate.opsForValue().set(
                    key,json, Duration.ofMinutes(10)
            );
        } catch (Exception e){
            throw new RuntimeException("Failed to Serialize product",e);
        }
    }

    public ProductResponse get(Long id){
        String key = PRODUCT_KEY_PREFIX+id;
        String json = redisTemplate.opsForValue().get(key);

        if (json == null){
            return null;
        }

        try{
            return objectMapper.readValue(json,ProductResponse.class);
        } catch (Exception e){
            throw new RuntimeException("Failed to deserialize product",e);
        }
    }

    public void delete(Long id){
        String key = PRODUCT_KEY_PREFIX+id;
        redisTemplate.delete(key);
    }

}
