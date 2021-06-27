package com.project.controller.user;

import com.project.model.dto.ProductDTO;
import com.project.model.response.HomePageResponse;
import com.project.service.user.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class HomeController {
    @Autowired
    ProductService productService;

    @GetMapping(value = {"/","/home"})
    public ResponseEntity<?> homePage(){
        //Get the latest 4 products
        List<ProductDTO> listNewProduct = productService.findNewProduct(4);
        //Get 4 best selling products sản
        List<ProductDTO> listBestSeller = productService.findBestSellerProduct(4);
        HomePageResponse response = new HomePageResponse(listNewProduct, listBestSeller);
        return ResponseEntity.ok(response);
    }
}
