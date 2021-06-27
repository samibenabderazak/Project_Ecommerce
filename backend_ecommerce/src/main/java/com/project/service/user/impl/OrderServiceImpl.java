package com.project.service.user.impl;

import com.project.repository.user.OrderRepository;
import com.project.entity.OrderProduct;
import com.project.entity.Orders;
import com.project.entity.Product;
import com.project.model.dto.OrderProductDTO;
import com.project.model.dto.OrdersDTO;
import com.project.model.dto.ProductDTO;
import com.project.model.mapper.OrderProductMapper;
import com.project.model.mapper.OrdersMapper;
import com.project.model.mapper.ProductMapper;
import com.project.repository.user.UserRepository;
import com.project.repository.user.OrderProductRepository;
import com.project.repository.user.ProductRepository;
import com.project.service.user.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public OrdersDTO findOrderByIdUser(Integer idUser) {
        Optional<Orders> order = orderRepository.findByIdUser(idUser);
        if (order.isPresent()){
            System.out.println(order.get().getOrderDate());
            return OrdersMapper.toOrdersDTO(order.get());
        }else{
            return null;
        }
    }

    @Override
    public List<OrderProductDTO> findOrderProductByIdOrder(Integer idOrder) {
        List<OrderProduct> orderProducts = orderProductRepository.findByIdOrder(idOrder);
        List<OrderProductDTO> orderProductDTOS = new ArrayList<>();
        for (OrderProduct x : orderProducts){
            Optional<Product> product = productRepository.findById(x.getProductByIdProduct().getId());
            ProductDTO productDTO = ProductMapper.toProductDTO(product.get());
            OrderProductDTO orderProductDTO = OrderProductMapper.toOrderProductDTO(x);
            orderProductDTO.setProductDTO(productDTO);
            orderProductDTOS.add(orderProductDTO);
        }
        return orderProductDTOS;
    }

    @Override
    public OrdersDTO findOrderById(Integer id) {
        return null;
    }

    @Override
    public OrdersDTO addOrder(OrdersDTO ordersDTO) {
        Orders orders = OrdersMapper.toOrders(ordersDTO);
        orders.setUserByIdUser(userRepository.findById(ordersDTO.getIdUser()).get());
        return OrdersMapper.toOrdersDTO(orderRepository.save(orders));
    }

    @Override
    public OrdersDTO updateOrder(OrdersDTO ordersDTO) {
        Orders orders = OrdersMapper.toOrders(ordersDTO);
        orders.setUserByIdUser(userRepository.findById(ordersDTO.getIdUser()).get());
        return OrdersMapper.toOrdersDTO(orderRepository.save(orders));
    }

    @Override
    public Boolean deleteOrderById(Integer id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public OrderProductDTO addOrderProduct(OrderProductDTO orderProductDTO) {
        OrderProduct orderProduct = OrderProductMapper.toOrderProduct(orderProductDTO);
        orderProduct.setProductByIdProduct(ProductMapper.toProduct(orderProductDTO.getProductDTO()));
        orderProduct.setOrdersByIdOrder(
                orderRepository.findByStatusAndIdUser(
                        0,
                        orderProductDTO.getOrdersDTO().getIdUser()
                ).get()
        );
        return OrderProductMapper.toOrderProductDTO(orderProductRepository.save(orderProduct));
    }

    @Override
    public OrderProductDTO findOrderProductByIdOrderAndIdProduct(Integer idOrder, Integer idProduct) {
        Optional<OrderProduct> orderProduct = orderProductRepository.findByIdOrderAndIdProduct(idOrder, idProduct);
        if(orderProduct.isPresent()){
            return OrderProductMapper.toOrderProductDTO(orderProduct.get());
        }else{
            return null;
        }
    }

    @Override
    public OrderProductDTO updateOrderProduct(OrderProductDTO orderProductDTO) {
        OrderProduct orderProduct = OrderProductMapper.toOrderProduct(orderProductDTO);
        orderProduct.setProductByIdProduct(ProductMapper.toProduct(orderProductDTO.getProductDTO()));
        orderProduct.setOrdersByIdOrder(OrdersMapper.toOrders(orderProductDTO.getOrdersDTO()));
        OrderProductDTO result = OrderProductMapper
                                            .toOrderProductDTO(
                                                    orderProductRepository.save(orderProduct)
                                            );
        result.setProductDTO(orderProductDTO.getProductDTO());
        result.setOrdersDTO(orderProductDTO.getOrdersDTO());
        return result;
    }

    @Override
    public Boolean deleteOrderProductById(Integer id) {
        orderProductRepository.deleteById(id);
        return true;
    }

    @Override
    public OrderProductDTO findOrderProductById(Integer id) {
        OrderProduct orderProduct = orderProductRepository.findById(id).get();
        OrderProductDTO orderProductDTO = OrderProductMapper.toOrderProductDTO(orderProduct);
        OrdersDTO ordersDTO = OrdersMapper.toOrdersDTO(orderProduct.getOrdersByIdOrder());
        ordersDTO.setIdUser(orderProduct.getOrdersByIdOrder().getUserByIdUser().getId());
        orderProductDTO.setOrdersDTO(ordersDTO);
        return orderProductDTO;
    }

    @Override
    public List<OrdersDTO> getListOrderHistory(Integer idUser) {
        List<Orders> ordersList = orderRepository.getListOrderHistory(idUser);
        List<OrdersDTO> result = new ArrayList<>();
        for(Orders o: ordersList){
            result.add(OrdersMapper.toOrdersDTO(o));
        }
        return result;
    }
}
