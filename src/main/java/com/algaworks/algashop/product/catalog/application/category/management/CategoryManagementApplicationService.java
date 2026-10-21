package com.algaworks.algashop.product.catalog.application.category.management;

import com.algaworks.algashop.product.catalog.application.category.query.CategoryDetailOutput;
import com.algaworks.algashop.product.catalog.application.product.management.ProductInput;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryManagementApplicationService {
    public CategoryDetailOutput create(CategoryInput input){
        return null;
    }

    public void update(UUID categoryId, CategoryInput input){

    }


    public void disable(UUID categoryId){

    }
}
