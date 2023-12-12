package com.example.prueba;

import com.example.prueba.controller.ProductController;
import com.example.prueba.model.ProductDetail;
import com.example.prueba.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void getSimilarProducts_ReturnsProductDetails() {
        String productId = "1";
        List<ProductDetail> mockProducts = Arrays.asList(
                new ProductDetail("2", "Product 2", 20.0, true),
                new ProductDetail("3", "Product 3", 30.0, false)
        );

        when(productService.getSimilarProducts(productId)).thenReturn(mockProducts);

        ResponseEntity<List<ProductDetail>> response = productController.getSimilarProducts(productId);

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getSimilarProducts_ReturnsNotFound() {
        String productId = "1";
        when(productService.getSimilarProducts(productId)).thenReturn(List.of());

        ResponseEntity<List<ProductDetail>> response = productController.getSimilarProducts(productId);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }
}
