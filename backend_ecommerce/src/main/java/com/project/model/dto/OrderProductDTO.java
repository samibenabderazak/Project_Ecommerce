package com.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {
    private int id;
    private Integer quantity;
    private Double price;
    private OrdersDTO ordersDTO;
    private ProductDTO productDTO;
}
