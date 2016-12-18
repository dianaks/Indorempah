package com.blibli.future.Model;

import javax.persistence.*;

/**
 * Created by Nita on 15/10/2016.
 */
@Entity
public class DetailCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Integer amount;
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @ManyToOne
    private Cart cart;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return amount*product.getPrice();
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.addDetailCart(this);
    }
}
