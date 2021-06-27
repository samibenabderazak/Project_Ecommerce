package com.project.service.user;

import com.project.model.dto.ProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProductService {
    List<ProductDTO> findAll(Pageable pageable);
    int totalProduct();
    List<ProductDTO> findByCategory(Integer idCategory);
    List<ProductDTO> findByPrice(Double price, Pageable pageable);
    int countProductFindByPrice(Double price);
    ProductDTO findById(Integer id);
    List<ProductDTO> findProductRelateById(Integer id);
    List<ProductDTO> findNewProduct(Integer limit);
    List<ProductDTO> findBestSellerProduct(Integer limit);
}
