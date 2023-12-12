package com.example.prueba;

import com.example.prueba.model.ProductDetail;
import com.example.prueba.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void getSimilarProducts_ReturnsListOfProducts() {
        String productId = "1";
        String[] productIds = {"2", "3"};
      
        when(restTemplate.getForEntity("http://localhost:3001/product/{productId}/similarids", String[].class, productId))
                .thenReturn(new ResponseEntity<>(productIds, HttpStatus.OK));
        when(restTemplate.getForObject("http://localhost:3001/product/{id}", ProductDetail.class, "2"))
                .thenReturn(new ProductDetail("2", "Product 2", 20.0, true));
        when(restTemplate.getForObject("http://localhost:3001/product/{id}", ProductDetail.class, "3"))
                .thenReturn(new ProductDetail("3", "Product 3", 30.0, false));
        
        List<ProductDetail> result = productService.getSimilarProducts(productId);

        assertEquals(2, result.size());
        assertEquals("Product 2", result.get(0).getName());
        assertEquals("Product 3", result.get(1).getName());
    }
}
