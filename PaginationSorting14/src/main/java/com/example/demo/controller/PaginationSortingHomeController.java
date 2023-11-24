package com.example.demo.controller;

import com.example.demo.dto.APIResponse;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
public class PaginationSortingHomeController {

    @Autowired
    private ProductService service;

    @GetMapping("/")
    public ResponseEntity<APIResponse<List<Product>>> getProducts() {
        List<Product> allProducts = service.findAllProducts();
        return ResponseEntity.ok(new APIResponse<>(allProducts.size(), allProducts));
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<APIResponse<List<Product>>> addProduct(@RequestBody Product product) {
        Product savedProduct = service.saveProduct(product);
        return ResponseEntity.ok(new APIResponse<>(1, Collections.singletonList(savedProduct)));
    }

    @GetMapping("/{field}")
    public ResponseEntity<APIResponse<List<Product>>> getProductsWithSort(@PathVariable String field) {
        List<Product> allProducts = service.findProductsWithSorting(field);
        return ResponseEntity.ok(new APIResponse<>(allProducts.size(), allProducts));
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<APIResponse<Page<Product>>> getProductsWithPagination(
            @PathVariable int offset, @PathVariable int pageSize) {
        Page<Product> productsWithPagination = service.findProductsWithPagination(offset, pageSize);
        return ResponseEntity.ok(new APIResponse<>(productsWithPagination.getSize(), productsWithPagination));
    }

    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    public ResponseEntity<APIResponse<Page<Product>>> getProductsWithPaginationAndSort(
            @PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        Page<Product> productsWithPagination = service.findProductsWithPaginationAndSorting(offset, pageSize, field);
        return ResponseEntity.ok(new APIResponse<>(productsWithPagination.getSize(), productsWithPagination));
    }
}
