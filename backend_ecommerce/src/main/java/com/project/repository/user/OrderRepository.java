package com.project.repository.user;

import com.project.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query(value = "SELECT o.* FROM orders as o WHERE id_user = ?1 AND status = 0", nativeQuery = true)
    Optional<Orders> findByIdUser(Integer idUser);

    @Query(value = "SELECT o.* FROM orders as o WHERE id_user = ?1 ", nativeQuery = true)
    List<Orders> findAllByIdUser(Integer idUser);

    @Query(value = "SELECT o.* FROM orders as o WHERE status = ?1 ORDER BY o.id DESC", nativeQuery = true)
    List<Orders> findByStatus(Integer status, Pageable pageable);

    @Query(value = "SELECT COUNT(o.id) FROM orders as o WHERE status = ?1", nativeQuery = true)
    Long countOrdersByStatus(Integer status);

    @Query(value = "SELECT o.* FROM orders as o WHERE status = ?1 AND id_user=?2", nativeQuery = true)
    Optional<Orders> findByStatusAndIdUser(Integer status, Integer idUser);

    @Query(value = "SELECT o.* FROM orders as o WHERE status != 0 AND id_user=?1 ORDER BY o.id DESC", nativeQuery = true)
    List<Orders> getListOrderHistory(Integer idUser);

    //admin ;;;;
    @Query(value = "SELECT o.* FROM orders as o WHERE status = ?1 AND id_user=?2", nativeQuery = true)
    List<Orders> findOrdersByStatusAndIdUser(Integer status, Integer idUser);

    @Query(value = "SELECT date_format(order_date,'%m') as name, sum(totalprice) as value from orders \n" +
            " WHERE date_format(order_date,'%y') = ?1 AND status = 3 GROUP BY date_format(order_date,'%m')",
    nativeQuery = true)
    List<Object[]> getRevenue(String year);
}
