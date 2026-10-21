package com.algaworks.algashop.product.catalog.application.category.query;

import java.util.UUID;

public class CateroryDetailOutputTestDataBuilder {

    private CateroryDetailOutputTestDataBuilder() {
    }

    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategory(){
        return CategoryDetailOutput
                .builder()
                .id(UUID.randomUUID())
                .enabled(true)
                .name("Notebooks");
    }


    public static CategoryDetailOutput.CategoryDetailOutputBuilder aCategoryAlt1(){
        return CategoryDetailOutput
                .builder()
                .id(UUID.randomUUID())
                .enabled(true)
                .name("Desktops");
    }
}
