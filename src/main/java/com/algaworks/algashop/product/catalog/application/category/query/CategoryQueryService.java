package com.algaworks.algashop.product.catalog.application.category.query;

import com.algaworks.algashop.product.catalog.application.PageModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CategoryQueryService {
    CategoryDetailOutput findById(UUID id);
    PageModel<CategoryDetailOutput> filter(Integer size, Integer number);
}
