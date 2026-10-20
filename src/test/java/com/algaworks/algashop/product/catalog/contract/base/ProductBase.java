package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.product.management.ProductInput;
import com.algaworks.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.product.query.PageModel;
import com.algaworks.algashop.product.catalog.application.product.query.ProductDetailOutput;
import com.algaworks.algashop.product.catalog.application.product.query.ProductDetailOutputTesteDatabuilder;
import com.algaworks.algashop.product.catalog.application.product.query.ProductQueryService;
import com.algaworks.algashop.product.catalog.presentation.ProductController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(controllers = ProductController.class)
public class ProductBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ProductQueryService productQueryService;

    @MockitoBean
    private ProductManagementApplicationService productManagementApplicationService;

    private static final UUID validProductId = UUID.fromString("e021ed5e-324d-4322-ae47-c46c95382628");

    private static final UUID createdProductId = UUID.fromString("b55c3717-506c-4615-89db-b7f1c0c8e354");

    private static final UUID invalidProductId = UUID.fromString("c5f7ea67-cf06-4574-aaeb-b3df0ad4baa4");

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();


        mockValidProductFindById();

        mockFilterProducts();

        mockCreateProduct();

        mockInvalidProductFindById();
    }

    private void mockInvalidProductFindById() {
        Mockito.when(productQueryService.findById(invalidProductId))
                .thenThrow(new ResourceNotFoundException());
    }

    private void mockCreateProduct() {
        Mockito.when(productManagementApplicationService.create(Mockito.any(ProductInput.class)))
                .thenReturn(createdProductId);

        Mockito.when(productQueryService.findById(createdProductId))
                .thenReturn(ProductDetailOutputTesteDatabuilder.aProduct().inStock(false).build());
    }

    private void mockFilterProducts() {
        Mockito.when(productQueryService.filter(Mockito.anyInt(), Mockito.anyInt())).
                then((answer) -> {
                    Integer size = answer.getArgument(0);
                    return PageModel.<ProductDetailOutput>builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(
                                    Arrays.asList(
                                            ProductDetailOutputTesteDatabuilder.aProduct().build(),
                                            ProductDetailOutputTesteDatabuilder.aProdutctAlt1().build()

                                    )
                            ).build();
                });
    }

    private void mockValidProductFindById() {
        Mockito.when(productQueryService.findById(validProductId))
                .thenReturn(ProductDetailOutputTesteDatabuilder.aProduct()
                        .id(validProductId)
                        .build());
    }
}
