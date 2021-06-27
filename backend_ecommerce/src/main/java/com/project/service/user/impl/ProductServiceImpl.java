package com.project.service.user.impl;

import com.project.model.dto.ProductDTO;
import com.project.repository.user.ProductRepository;
import com.project.entity.Product;
import com.project.model.mapper.ProductMapper;
import com.project.service.user.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDTO> findAll(Pageable pageable) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAll(pageable).getContent();
        for(Product x : products){
            productDTOS.add(ProductMapper.toProductDTO(x));
        }
        productDTOS.sort((o1, o2) -> o1.getId()- o2.getId());
        return productDTOS;
    }

    @Override
    public int totalProduct() {
        return (int)productRepository.count();
    }

    @Override
    public List<ProductDTO> findByCategory(Integer idCategory) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findProductByCategory(idCategory);
        for(Product x: products){
            productDTOS.add(ProductMapper.toProductDTO(x));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> findByPrice(Double price, Pageable pageable) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findByPrice(price, pageable);
        for(Product product : products){
            productDTOS.add(ProductMapper.toProductDTO(product));
        }
        return productDTOS;
    }

    @Override
    public int countProductFindByPrice(Double price) {
        return productRepository.countProductFindByPrice(price);
    }

    @Override
    public ProductDTO findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return ProductMapper.toProductDTO(product.get());
    }

    @Override
    public List<ProductDTO> findProductRelateById(Integer id) {
        List<Product> products = productRepository.findProductRelateById(id);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product x : products){
            productDTOS.add(ProductMapper.toProductDTO(x));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> findNewProduct(Integer limit) {
        List<Product> products = productRepository.findNewProduct(limit);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product x: products){
            productDTOS.add(ProductMapper.toProductDTO(x));
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> findBestSellerProduct(Integer limit){
        List<Product> products = productRepository.findBestSellerProduct(limit);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product x: products){
            productDTOS.add(ProductMapper.toProductDTO(x));
        }
        return productDTOS;
    }

}
