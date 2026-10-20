package com.algaworks.algashop.product.catalog.application.product.query;

import com.algaworks.algashop.product.catalog.presentation.ProductController;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProductQueryService {
    ProductDetailOutput findById(UUID id);
    PageModel<ProductDetailOutput> filter(Integer size, Integer number);
}
