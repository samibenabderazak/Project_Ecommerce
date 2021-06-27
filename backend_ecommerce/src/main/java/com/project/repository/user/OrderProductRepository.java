package com.project.repository.user;

import com.project.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    @Query(value = "SELECT op.* FROM order_product as op WHERE op.id_order =?1 ", nativeQuery = true)
    List<OrderProduct> findByIdOrder(Integer idOrder);
    @Query(value = "SELECT op.* FROM order_product as op WHERE op.id_order =?1 AND op.id_product=?2 ",
            nativeQuery = true)
    Optional<OrderProduct> findByIdOrderAndIdProduct(Integer idOrder, Integer idProduct);


}
