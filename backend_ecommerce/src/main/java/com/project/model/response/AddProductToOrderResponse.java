package com.project.model.response;

import com.project.model.dto.OrdersDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddProductToOrderResponse {
    private OrdersDTO ordersDTO;
    private String msg;

}
