package com.project.model.response;

import com.project.model.dto.OrderProductDTO;
import com.project.model.dto.OrdersDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPage {
    private OrdersDTO ordersDTO;
    private List<OrderProductDTO> orderProducts;
}
