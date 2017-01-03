package com.blibli.future.Model;

import javax.persistence.*;

/**
 * Created by Elisabet Diana K S on 01/01/2017.
 */
@Entity
public class DetailOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Integer amount;
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @ManyToOne
    private Order order;

    public long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        order.addDetailOrder(this);
        this.order = order;
    }
}
