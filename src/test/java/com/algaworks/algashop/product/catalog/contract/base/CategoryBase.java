package com.algaworks.algashop.product.catalog.contract.base;

import com.algaworks.algashop.product.catalog.application.PageModel;
import com.algaworks.algashop.product.catalog.application.ResourceNotFoundException;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryInput;
import com.algaworks.algashop.product.catalog.application.category.management.CategoryManagementApplicationService;
import com.algaworks.algashop.product.catalog.application.category.query.CategoryDetailOutput;
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
import java.util.Arrays;
import java.util.UUID;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private CategoryManagementApplicationService categoryManagementApplicationService;

    @MockitoBean
    private CategoryQueryService categoryQueryService;

    private static final UUID validCategoryId = UUID.fromString("e021ed5e-324d-4322-ae47-c46c95382628");

    private static final UUID invalidCategoryId = UUID.fromString("c5f7ea67-cf06-4574-aaeb-b3df0ad4baa4");

    private static final UUID updatedCategoryId = UUID.fromString("fffe6ec2-7103-48b3-8e4f-3b58e43fb75a");

    private static final UUID deletedCategoryId = UUID.fromString("9676e7f6-1080-40fd-b487-0ca92dacfb83");

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(context)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build());

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockValidCategoryFindById();
        mockFilterCategories();
        mockCreateCategory();
        mockUpdateCategory();
        mockDeleteCategory();
        mockUpdateCategoryNotFound();
        mockDeleteCategoryNotFound();
        mockInvalidCategoryFindById();
    }

    private void mockInvalidCategoryFindById() {
        Mockito.when(categoryQueryService.findById(invalidCategoryId))
                .thenThrow(new ResourceNotFoundException());
    }

    private void mockCreateCategory() {
        Mockito.when(categoryManagementApplicationService.create(Mockito.any(CategoryInput.class)))
                .thenAnswer(invocation -> {
                    CategoryInput input = invocation.getArgument(0);
                    return CateroryDetailOutputTestDataBuilder.aCategory()
                            .name(input.getName())
                            .enabled(input.getEnabled())
                            .build();
                });
    }

    private void mockFilterCategories() {
        Mockito.when(categoryQueryService.filter(Mockito.any(), Mockito.any()))
                .thenAnswer(answer -> {
                    Integer size = answer.getArgument(0);
                    return PageModel.<CategoryDetailOutput>builder()
                            .number(0)
                            .size(size)
                            .totalPages(1)
                            .totalElements(2)
                            .content(Arrays.asList(
                                    CateroryDetailOutputTestDataBuilder.aCategory().build(),
                                    CateroryDetailOutputTestDataBuilder.aCategoryAlt1().build()
                            ))
                            .build();
                });
    }

    private void mockValidCategoryFindById() {
        Mockito.when(categoryQueryService.findById(validCategoryId))
                .thenReturn(CateroryDetailOutputTestDataBuilder.aCategory()
                        .id(validCategoryId)
                        .build());
    }

    private void mockUpdateCategory() {
        Mockito.when(categoryQueryService.findById(updatedCategoryId))
                .thenAnswer(invocation -> CateroryDetailOutputTestDataBuilder.aCategory()
                        .id(updatedCategoryId)
                        .name("Periféricos")
                        .enabled(false)
                        .build());
    }

    private void mockDeleteCategory() {
        Mockito.doNothing()
                .when(categoryManagementApplicationService)
                .disable(deletedCategoryId);
    }

    private void mockUpdateCategoryNotFound() {
        Mockito.doThrow(new ResourceNotFoundException())
                .when(categoryManagementApplicationService)
                .update(Mockito.eq(invalidCategoryId), Mockito.any(CategoryInput.class));
    }

    private void mockDeleteCategoryNotFound() {
        Mockito.doThrow(new ResourceNotFoundException())
                .when(categoryManagementApplicationService)
                .disable(invalidCategoryId);
    }
}
