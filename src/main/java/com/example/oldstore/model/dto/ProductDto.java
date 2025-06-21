package com.example.oldstore.model.dto;

import lombok.Data;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDto {

    private Integer productId;

    @NotBlank(message = "{productDto.name.notBlank}")
    @Size(max = 100, message = "{productDto.name.size}")
    private String name;

    @NotNull(message = "{productDto.price.notNull}")
    @DecimalMin(value = "1.0", message = "{productDto.price.min}")
    private BigDecimal price;

    @NotNull(message = "{productDto.stock.notNull}")
    @Min(value = 1, message = "{productDto.stock.min}")
    private Integer stock;

    @Size(max = 500, message = "{productDto.description.size}")
    private String description;

    private String imagePath; // 圖片路徑不用驗證，因為不是使用者輸入
}