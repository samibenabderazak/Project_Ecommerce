package com.project.controller.admin;

import com.project.model.dto.OrdersDTO;
import com.project.service.admin.ManageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManageOrderController {
    @Autowired
    private ManageOrderService manageOrderService;

    @GetMapping(value = "/admin/order")
    public ResponseEntity<?> getOrderByStatus(@RequestParam(value = "status") Integer status,
                                              @RequestParam(value = "page") Integer page,
                                              @RequestParam(value = "limit") Integer limit){
        Pageable pageable = PageRequest.of(page-1, limit);
        return ResponseEntity.ok(manageOrderService.findOrderByStatus(status, pageable));
    }

    @GetMapping(value = "/admin/order-count")
    public ResponseEntity<?> countOrderByStatus(@RequestParam(value = "status") Integer status){
        return ResponseEntity.ok(manageOrderService.countOdersByStatus(status));
    }

    @GetMapping(value = "/admin/revenue")
    public ResponseEntity<?> getYears(@RequestParam(value = "year") String year){
        return ResponseEntity.ok(manageOrderService.getRevenue(year));
    }

    @GetMapping(value = "/admin/order-detail")
    public ResponseEntity<?> getOrderProductByIdOrder(@RequestParam(value = "id") Integer id){
        return ResponseEntity.ok(manageOrderService.getOrderProductByIdOrder(id));
    }

    @PutMapping(value = "/admin/order-confirm/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(manageOrderService.confirmOrder(id));
    }

    @PutMapping(value = "/admin/order")
    public ResponseEntity<?> updateOrder(@RequestBody OrdersDTO ordersDTO){
        return ResponseEntity.ok(manageOrderService.updateOrder(ordersDTO));
    }

    @DeleteMapping(value = "/admin/order")
    public ResponseEntity<?> deleteOrder(@RequestParam Integer id){
        return ResponseEntity.ok(manageOrderService.deleteOrder(id));
    }
}
