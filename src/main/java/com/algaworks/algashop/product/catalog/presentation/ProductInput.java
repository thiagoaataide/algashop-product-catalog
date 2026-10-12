package com.algaworks.algashop.product.catalog.presentation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInput {

    private String name;
    private String brand;
    private BigDecimal regularPrice;
    private BigDecimal salePrice;
    private UUID categoryId;
    private Boolean enabled;
    private String description;
}
