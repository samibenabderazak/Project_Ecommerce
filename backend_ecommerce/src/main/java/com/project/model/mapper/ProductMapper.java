package com.project.model.mapper;

import com.project.model.dto.ProductDTO;
import com.project.entity.Product;

public class ProductMapper {
    public static ProductDTO toProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setImage(product.getImage());
        productDTO.setCreateDate(product.getCreateDate());
        productDTO.setCategoryId(product.getCategoryByIdCategory().getId());
        return productDTO;
    }

    public static Product toProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());
        product.setCreateDate(productDTO.getCreateDate());
        return product;
    }
}
