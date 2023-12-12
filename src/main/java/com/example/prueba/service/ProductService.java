package com.example.prueba.service;

import com.example.prueba.model.ProductDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDetail> getSimilarProducts(String productId) {
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:3001/product/{productId}/similarids",
                String[].class,
                productId
        );

        String[] similarProductIds = responseEntity.getBody();
        List<ProductDetail> productDetails = new ArrayList<>();

        for (String id : similarProductIds) {
            ProductDetail productDetail = restTemplate.getForObject(
                    "http://localhost:3001/product/{id}",
                    ProductDetail.class,
                    id
            );
            productDetails.add(productDetail);
        }
        return productDetails;
    }
}

