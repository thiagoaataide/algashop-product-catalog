package com.algaworks.algashop.product.catalog.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailOutput findById(@PathVariable UUID productId) {
        return ProductDetailOutput.builder()
                .id(productId)
                .addedAt(OffsetDateTime.now())
                .name("Notebook X11")
                .brand("Deep Diver")
                .description("A Gamer Notebook")
                .regularPrice(new BigDecimal(1500))
                .salePrice(new BigDecimal(1000))
                .inStock(true)
                .enabled(true)
                .categoryId(UUID.randomUUID())
                .category(CategoryMinimalOutput.builder()
                        .id(UUID.randomUUID())
                        .name("Notebooks")
                        .build())
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDetailOutput create(@RequestBody @Valid ProductInput input) {
        return ProductDetailOutput.builder()
                .id(UUID.randomUUID())
                .name(input.getName())
                .brand(input.getBrand())
                .regularPrice(input.getRegularPrice())
                .salePrice(input.getSalePrice())
                .enabled(input.getEnabled())
                .description(input.getDescription())
                .inStock(false)
                .addedAt(OffsetDateTime.now())
                .category(CategoryMinimalOutput.builder()
                        .id(input.getCategoryId())
                        .name("Notebooks")
                        .build())
                .build();

    }

}
