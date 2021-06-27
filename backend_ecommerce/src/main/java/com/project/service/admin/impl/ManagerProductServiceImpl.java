package com.project.service.admin.impl;

import com.project.model.dto.ProductDTO;
import com.project.model.response.ResponseNormal;
import com.project.model.response.ResponseUploadFile;
import com.project.repository.user.CategoryRepository;
import com.project.entity.Category;
import com.project.entity.Product;
import com.project.model.dto.CategoryDTO;
import com.project.model.mapper.CategoryMapper;
import com.project.model.mapper.ProductMapper;
import com.project.model.response.PageableModel;
import com.project.repository.user.ProductRepository;
import com.project.service.admin.ManagerProductService;
import com.project.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ManagerProductServiceImpl implements ManagerProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> findAll(Pageable pageable) {
        List<Product> productList = productRepository.findAll(pageable).getContent();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product p : productList ){
            productDTOList.add(ProductMapper.toProductDTO(p));
        }
        return productDTOList;
    }

    @Override
    public PageableModel<ProductDTO> findByCategory(Integer id, Pageable pageable) {
        List<Product> productList = productRepository.findByCategory(id, pageable);
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product p : productList ){
            productDTOList.add(ProductMapper.toProductDTO(p));
        }
        PageableModel<ProductDTO> response = new PageableModel<>();
        response.setList(productDTOList);
        response.setTotal(productRepository.countProductByCategory(id));
        return response;
    }

    @Override
    public List<CategoryDTO> findAllCategory(Pageable pageable) {
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(Category category : categories ){
            categoryDTOList.add(CategoryMapper.toCategoryDTO(category));
        }
        return categoryDTOList;
    }

    @Override
    public Long countProducts() {
        return productRepository.count();
    }

    @Override
    public Long countCategories() {
        return categoryRepository.count();
    }

    @Override
    public ResponseNormal addProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).get();
        Product product = ProductMapper.toProduct(productDTO);
        product.setCategoryByIdCategory(category);
        product.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        productRepository.save(product);
        ResponseNormal response = new ResponseNormal("Thêm mới thành công", HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseNormal updateProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).get();
        Product product = ProductMapper.toProduct(productDTO);
        product.setCategoryByIdCategory(category);
        productRepository.save(product);
        ResponseNormal response = new ResponseNormal("Sửa thành công", HttpStatus.OK);
        return response;
    }

    @Override
    public ResponseNormal deleteProduct(Integer id) {
        try{
            productRepository.deleteById(id);
            return new ResponseNormal("Xóa thành công", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseNormal("Xóa không thành công", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseNormal addCategory(CategoryDTO categoryDTO) {
        //Check the same name
        List<Category> categoryList = categoryRepository.findAll();
        long result = categoryList.stream()
                                .filter(c -> categoryDTO.getName().trim().equalsIgnoreCase(c.getName()))
                                .count();
        if(result > 0){
            return new ResponseNormal("Category name already exists, cannot be added!", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(CategoryMapper.toCategory(categoryDTO));
        return new ResponseNormal("Successfully added new", HttpStatus.OK);
    }

    @Override
    public ResponseNormal updateCategory(CategoryDTO categoryDTO) {
        //Check trùng tên
        List<Category> categoryList = categoryRepository.findAll();
        long result = categoryList.stream()
                .filter(c -> categoryDTO.getName().trim().equalsIgnoreCase(c.getName()))
                .count();
        if(result > 0){
            return new ResponseNormal("Category name already exists, cannot be edited!", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(CategoryMapper.toCategory(categoryDTO));
        return new ResponseNormal("Successfully edited", HttpStatus.OK);
    }

    @Override
    public ResponseNormal deleteCategory(Integer id) {
        List<Product> productList = productRepository.findProductByCategory(id);
        if(productList.size()>0){
            return new ResponseNormal("Products in this category cannot be deleted!", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.deleteById(id);
        return new ResponseNormal("Delete successfully!", HttpStatus.OK);
    }

    private static String UPLOAD_DIR = FileUtils.getResourceBasePath()
                                        .substring(0, FileUtils.getResourceBasePath().length() - 12)+ "\\website-angular\\src\\assets\\images";
    @Override
    public ResponseUploadFile<String> uploadFile(MultipartFile multipartFile) {
        //Create a folder containing images if not existing
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        //Get file name and file extension
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (originalFilename.length() > 0) {

            //Check if the file is in the correct format
            if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("svg") && !extension.equals("jpeg")) {
                return new ResponseUploadFile("This file format is not supported!", HttpStatus.BAD_REQUEST, "");
            }
            try {
                String nameFile = UUID.randomUUID().toString() + "." +extension;

                //Create file
                File file = new File(UPLOAD_DIR + "\\" + nameFile);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(multipartFile.getBytes());
                bos.close();

                return new ResponseUploadFile("Upload photo successfully!", HttpStatus.OK, nameFile);

            } catch (Exception e) {
                System.out.println("An error occurred while uploading the file!");
            }
        }
        return new ResponseUploadFile("Invalid file!", HttpStatus.BAD_REQUEST, "");
    }
}
