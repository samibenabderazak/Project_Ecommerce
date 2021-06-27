package com.project.controller.admin;

import com.project.model.dto.ProductDTO;
import com.project.service.admin.ManagerProductService;
import com.project.model.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ManageProductController {
    @Autowired
    private ManagerProductService productService;

    @GetMapping(value = "admin/product")
    public ResponseEntity<?> managerProduct(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        List<ProductDTO> list = productService.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "admin/product/{id}")
    public ResponseEntity<?> getProductsByCategory( @PathVariable(value = "id") Integer idCategory,
                                            @RequestParam(value = "page") Integer page,
                                            @RequestParam(value = "limit") Integer limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        return ResponseEntity.ok(productService.findByCategory(idCategory, pageable));
    }

    @GetMapping(value = "admin/category")
    public ResponseEntity<?> managerCategory(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        List<CategoryDTO> list = productService.findAllCategory(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "admin/product-count")
    public ResponseEntity<?> totalProducts(){
        return ResponseEntity.ok(productService.countProducts());
    }

    @GetMapping(value = "admin/category-count")
    public ResponseEntity<?> totalCategories(){
        return ResponseEntity.ok(productService.countCategories());
    }

    @PostMapping(value = "admin/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    @PutMapping(value = "admin/product")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(productDTO));
    }

    @DeleteMapping(value = "admin/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @PostMapping(value = "admin/category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(productService.addCategory(categoryDTO));
    }

    @PutMapping(value = "admin/category")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(productService.updateCategory(categoryDTO));
    }

    @DeleteMapping(value = "admin/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(productService.deleteCategory(id));
    }

    //Receive photo uploads
    @PostMapping(value = "/admin/upload-img")
    public ResponseEntity<?> receiveImage(@RequestParam(value = "image") MultipartFile[] multipartFiles){
        MultipartFile multipartFile = multipartFiles[0];
        return ResponseEntity.ok(productService.uploadFile(multipartFile));

    }
}
