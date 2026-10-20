package com.algaworks.algashop.product.catalog.application.product.query;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ProductDetailOutputTesteDatabuilder {

    private ProductDetailOutputTesteDatabuilder() {
    }

    public static ProductDetailOutput.ProductDetailOutputBuilder aProduct() {
        return ProductDetailOutput.builder()
                .id(UUID.randomUUID())
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
                ;
    }

    public static ProductDetailOutput.ProductDetailOutputBuilder aProdutctAlt1() {
        return ProductDetailOutput.builder()
                .id(UUID.randomUUID())
                .addedAt(OffsetDateTime.now())
                .name("Desktop I9000")
                .brand("Deep Diver")
                .description("A Gamer Notebook")
                .regularPrice(new BigDecimal(3500))
                .salePrice(new BigDecimal(3000))
                .inStock(false)
                .enabled(true)
                .categoryId(UUID.randomUUID())
                .category(CategoryMinimalOutput.builder()
                        .id(UUID.randomUUID())
                        .name("Desktop")
                        .build())
                ;
    }
}
