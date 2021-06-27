package com.project.service.admin;

import com.project.model.dto.ProductDTO;
import com.project.model.response.PageableModel;
import com.project.model.response.ResponseNormal;
import com.project.model.response.ResponseUploadFile;
import com.project.model.dto.CategoryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ManagerProductService {
    List<ProductDTO> findAll(Pageable pageable);
    PageableModel<ProductDTO> findByCategory(Integer id, Pageable pageable);
    List<CategoryDTO> findAllCategory(Pageable pageable);
    Long countProducts();
    Long countCategories();
    ResponseNormal addProduct(ProductDTO productDTO) ;
    ResponseNormal updateProduct(ProductDTO productDTO) ;
    ResponseNormal deleteProduct(Integer id) ;
    ResponseNormal addCategory(CategoryDTO categoryDTO) ;
    ResponseNormal updateCategory(CategoryDTO categoryDTO) ;
    ResponseNormal deleteCategory(Integer id) ;
    ResponseUploadFile<String> uploadFile(MultipartFile multipartFile);
}
