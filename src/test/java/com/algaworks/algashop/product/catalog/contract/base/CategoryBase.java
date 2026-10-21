package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryQueryService;
import com.algaworks.algashop.product.catalog.application.category.query.CateroryDetailOutputTestDataBuilder;
import com.algaworks.algashop.product.catalog.presentation.CategoryController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CategoryManagementApplicationService categoryManagementApplicationService;

    @MockitoBean
    private CategoryQueryService categoryQueryService;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockCreateProduct();
    }

    private void mockCreateProduct() {
        Mockito.when(categoryManagementApplicationService.create(Mockito.any(CategoryInput.class)))
                .thenReturn(CateroryDetailOutputTestDataBuilder.aCategory().build());

    }
}
