package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.product.management.ProductInput;
import com.algaworks.algashop.product.catalog.application.product.management.ProductManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.PageModel;
import com.algaworks.algashop.product.catalog.application.product.query.CategoryMinimalOutput;
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

    private static final UUID updatedProductId = UUID.fromString("fffe6ec2-7103-48b3-8e4f-3b58e43fb75a");

    private static final UUID deletedProductId = UUID.fromString("9676e7f6-1080-40fd-b487-0ca92dacfb83");

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();


        mockValidProductFindById();

        mockFilterProducts();

        mockCreateProduct();

        mockUpdateProduct();

        mockDeleteProduct();

        mockUpdateProductNotFound();

        mockDeleteProductNotFound();

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

    private void mockUpdateProduct() {
        Mockito.when(productQueryService.findById(updatedProductId))
                .thenReturn(ProductDetailOutputTesteDatabuilder.aProduct()
                        .id(updatedProductId)
                        .category(CategoryMinimalOutput.builder()
                                .id(UUID.randomUUID())
                                .name("Notebook")
                                .build())
                        .build());
    }

    private void mockDeleteProduct() {
        Mockito.doNothing()
                .when(productManagementApplicationService)
                .disable(deletedProductId);
    }

    private void mockUpdateProductNotFound() {
        Mockito.doThrow(new ResourceNotFoundException())
                .when(productManagementApplicationService)
                .update(Mockito.eq(invalidProductId), Mockito.any(ProductInput.class));
    }

    private void mockDeleteProductNotFound() {
        Mockito.doThrow(new ResourceNotFoundException())
                .when(productManagementApplicationService)
                .disable(invalidProductId);
    }
}
