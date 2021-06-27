package com.project.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_product", schema = "migi_project", catalog = "")
public class OrderProduct {
    private int id;
    private Integer quantity;
    private Double price;
    private Product productByIdProduct;
    private Orders ordersByIdOrder;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id", nullable = false)
    public Product getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(Product productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }

    @ManyToOne
    @JoinColumn(name = "id_order", referencedColumnName = "id", nullable = false)
    public Orders getOrdersByIdOrder() {
        return ordersByIdOrder;
    }

    public void setOrdersByIdOrder(Orders ordersByIdOrder) {
        this.ordersByIdOrder = ordersByIdOrder;
    }
}
