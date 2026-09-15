package com.slp.order_pricing_service.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 255,message = "product name cannot exceed 255 character")
    private String name;

    @Size(max = 255,message = "Description cannot exceed 255 character")
    private String description;

    @DecimalMin(value = "0.01",message = "Price should be greater then zero")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Size(max = 255,message = "Category cannot exceed 255 character")
    @NotBlank(message = "Category is required")
    private String category;

    @Min(value = 0,message = "Stock can not be negative")
    @NotNull(message = "Stock is required")
    private Integer stock;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
