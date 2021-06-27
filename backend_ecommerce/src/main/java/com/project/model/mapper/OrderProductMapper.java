package com.project.model.mapper;

import com.project.entity.OrderProduct;
import com.project.model.dto.OrderProductDTO;

public class OrderProductMapper {
    public static OrderProductDTO toOrderProductDTO(OrderProduct orderProduct){
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setId(orderProduct.getId());
        orderProductDTO.setQuantity(orderProduct.getQuantity());
        orderProductDTO.setPrice(orderProduct.getPrice());
        orderProductDTO.setProductDTO(ProductMapper.toProductDTO(orderProduct.getProductByIdProduct()));
        orderProductDTO.setOrdersDTO(OrdersMapper.toOrdersDTO(orderProduct.getOrdersByIdOrder()));
        return orderProductDTO;
    }

    public static OrderProduct toOrderProduct(OrderProductDTO orderProductDTO){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(orderProductDTO.getId());
        orderProduct.setQuantity(orderProductDTO.getQuantity());
        orderProduct.setPrice(orderProductDTO.getPrice());
        orderProduct.setOrdersByIdOrder(OrdersMapper.toOrders(orderProductDTO.getOrdersDTO()));
        orderProduct.setProductByIdProduct(ProductMapper.toProduct(orderProductDTO.getProductDTO()));
        return orderProduct;
    }
}
