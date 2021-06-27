package com.project.service.admin.impl;

import com.project.model.dto.OrderProductDTO;
import com.project.model.dto.OrdersDTO;
import com.project.model.dto.Revenue;
import com.project.model.response.ResponseNormal;
import com.project.repository.user.OrderRepository;
import com.project.entity.OrderProduct;
import com.project.entity.Orders;
import com.project.entity.User;
import com.project.model.mapper.OrderProductMapper;
import com.project.model.mapper.OrdersMapper;
import com.project.repository.user.OrderProductRepository;
import com.project.repository.user.UserRepository;
import com.project.service.admin.ManageOrderService;
import com.project.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageOrderServiceImpl implements ManageOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrdersDTO> findOrderByStatus(Integer status, Pageable pageable) {
        List<Orders> ordersList = orderRepository.findByStatus(status, pageable);
        List<OrdersDTO> ordersDTOS = new ArrayList<>();
        for(Orders o : ordersList){
            ordersDTOS.add(OrdersMapper.toOrdersDTO(o));
        }
        return ordersDTOS;
    }

    @Override
    public Long countOdersByStatus(Integer status) {
        return orderRepository.countOrdersByStatus(status);
    }

    @Override
    public ResponseNormal confirmOrder(Integer id) {
        Orders order = orderRepository.findById(id).get();
        //Browse orders
        if(order.getStatus() == 1){
            order.setStatus(2);
            orderRepository.save(order);
            return new ResponseNormal("Moved to delivery status!", HttpStatus.OK);
        }else if(order.getStatus() == 2){
            order.setStatus(3);
            orderRepository.save(order);
            return new ResponseNormal("The order has changed to the delivery status!", HttpStatus.OK);
        }
        else{
            return new ResponseNormal("Order is in order status, not approved!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseNormal updateOrder(OrdersDTO ordersDTO) {
        if(ordersDTO.getStatus() == 0 ){
            List<Orders> ordersList = orderRepository.findOrdersByStatusAndIdUser(0, ordersDTO.getIdUser());
            if(ordersList.size() == 0 ){
                Orders orders = OrdersMapper.toOrders(ordersDTO);
                User user = userRepository.findById(ordersDTO.getIdUser()).get();
                orders.setUserByIdUser(user);
                orderRepository.save(orders);
                return new ResponseNormal("Update successful!", HttpStatus.OK);
            } else {
                return new ResponseNormal("User " + ordersDTO.getNameUser() + " Ordering, can't update",
                        HttpStatus.BAD_REQUEST);
            }
        } else {
            Orders orders = OrdersMapper.toOrders(ordersDTO);
            User user = userRepository.findById(ordersDTO.getIdUser()).get();
            orders.setUserByIdUser(user);
            orderRepository.save(orders);
            return new ResponseNormal("Update successful!", HttpStatus.OK);
        }
    }

    @Override
    public ResponseNormal deleteOrder(Integer id) {
        List<OrderProduct> orderProducts = orderProductRepository.findByIdOrder(id);
        if(orderProducts.size() > 0){
            for(OrderProduct o : orderProducts){
                orderProductRepository.delete(o);
            }
        }
        orderRepository.deleteById(id);
        return new ResponseNormal("Delete successfully!", HttpStatus.OK);
    }

    @Override
    public List<Revenue> getRevenue(String year) {
        String sub = year.substring(2, 4);
        List<Object[]> objects = orderRepository.getRevenue(sub);
        List<Revenue> revenues = new ArrayList<>();
        for(Object[] o: objects){
            Revenue revenue = new Revenue();
            revenue.setName("Month "+ DataUtils.safeToString(o[0]));
            revenue.setValue(DataUtils.safeToDouble(o[1]));
            revenues.add(revenue);
        }
        return revenues;
    }

    @Override
    public List<OrderProductDTO> getOrderProductByIdOrder(Integer idOrder) {
        return orderProductRepository.findByIdOrder(idOrder)
                .stream()
                .map(item -> OrderProductMapper.toOrderProductDTO(item))
                .collect(Collectors.toList());
    }

}
