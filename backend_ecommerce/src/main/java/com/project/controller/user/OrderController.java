package com.project.controller.user;

import com.project.model.dto.OrderProductDTO;
import com.project.model.dto.OrdersDTO;
import com.project.model.response.AddProductToOrderResponse;
import com.project.model.response.OrderPage;
import com.project.security.CustomUserDetails;
import com.project.service.user.OrderService;
import com.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping(value = "/order")
    public ResponseEntity<?> loadOrderPage(){
        //get user f session courante
        User user =
                ((CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUser();
        OrderPage orderPage= new OrderPage();
        //Get user's current order by user id
        OrdersDTO ordersDTO = orderService.findOrderByIdUser(user.getId());
        if(ordersDTO != null){
            orderPage.setOrdersDTO(ordersDTO);
            //Lấy danh sách các sản phẩm trong order hiện tại và số lượng từng sản phẩm
            orderPage.setOrderProducts(orderService.findOrderProductByIdOrder(ordersDTO.getId()));
        }
        return ResponseEntity.ok(orderPage);
    }

    @GetMapping(value = "/order/history")
    public ResponseEntity<?> getListOrderHistory(@RequestParam(value = "id-user") Integer idUser){
        return ResponseEntity.ok(orderService.getListOrderHistory(idUser));
    }

    @PostMapping(value = "/order/payment/{id}")
    public ResponseEntity<?> paymentOrder(@RequestBody OrdersDTO orderDTO,
                                          @PathVariable(value = "id") Integer idUser){
        orderDTO.setIdUser(idUser);
        AddProductToOrderResponse response = new AddProductToOrderResponse(
                                                    orderService.updateOrder(orderDTO),
                                                    "Đơn hàng đã đang được đợi xác nhận!"
                                                );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/order/remove")
    public ResponseEntity<?> deleteProductOrder(
            @RequestParam(value = "id-product-order")Integer idProductOrder)
    {
        //Lấy order product theo id gửi lên
        OrderProductDTO orderProductDTO = orderService.findOrderProductById(idProductOrder);
        OrdersDTO ordersDTO = orderProductDTO.getOrdersDTO();
        double price = ordersDTO.getTotalprice() - orderProductDTO.getPrice();
        //Xóa orderService
        orderService.deleteOrderProductById(idProductOrder);
        //Cập nhật lại tổng thanh toán nếu order không còn sản phẩm nào thì xóa đi
        OrderPage orderPage = new OrderPage();
        if(price > 0){
            ordersDTO.setTotalprice(price);
            orderService.updateOrder(ordersDTO);
            orderPage.setOrderProducts(orderService.findOrderProductByIdOrder(ordersDTO.getId()));
            orderPage.setOrdersDTO(ordersDTO);
        }else {
            orderService.deleteOrderById(ordersDTO.getId());
        }
        //Trả về order hiện tại sau khi đã cập nhật
        return ResponseEntity.ok(orderPage);
    }

}
