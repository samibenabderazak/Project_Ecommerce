package com.project.model.response;

import com.project.model.dto.CategoryDTO;
import com.project.model.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductPage {
    private int page;
    private int totalPage;
    private int totalProduct;
    private List<ProductDTO> productDTOList;
    private List<CategoryDTO> categoryDTOList;
}
