package com.project.repository.user;

import com.project.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p.* FROM product p WHERE p.id_category=?1", nativeQuery = true)
    List<Product> findProductByCategory(Integer idCategory);

    @Query(value = "SELECT COUNT(p.id) FROM product p WHERE p.id_category=?1", nativeQuery = true)
    Long countProductByCategory(Integer idCategory);

    @Query(value = "SELECT p.* FROM product p WHERE p.id_category=?1", nativeQuery = true)
    List<Product> findByCategory(Integer idCategory, Pageable pageable);

    @Query(value = "SELECT p.* FROM product as p WHERE p.price <= ?1 ", nativeQuery = true)
    List<Product> findByPrice(Double price, Pageable pageable);

    @Query(value = "SELECT COUNT(p.id) FROM product as p WHERE p.price <= ?1 ", nativeQuery = true)
    int countProductFindByPrice(Double price);

    @Query(value = "SELECT p.* FROM product as p WHERE id != ?1 ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Product> findProductRelateById(Integer id);

    @Query(value = "SELECT p.* FROM product as p ORDER BY create_date DESC LIMIT ?1", nativeQuery = true)
    List<Product> findNewProduct(Integer limit);

    @Query(value = "SELECT p.* FROM product as p " +
            "  JOIN order_product as op ON p.id=op.id_product " +
            "GROUP BY p.id ORDER BY count(op.quantity) DESC limit ?1", nativeQuery = true)
    List<Product> findBestSellerProduct(Integer limit);
}
