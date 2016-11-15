package com.blibli.future.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nita on 15/10/2016.
 */
@Entity
public class Cart {
    @Id
    private long id;
    private String name;
    private long totalPrice;
    private String picture;

    @OneToMany
    @JoinColumn(name="cart_id")
    private List<DetailCart> detailCarts;
    public List<DetailCart> getDetailCarts() {
        return detailCarts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalPrice() {
        int total = 0;
        for (DetailCart detailCart: this.detailCarts ) {
            total += detailCart.getProduct().getPrice();
        }
        return total;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDetailCarts(List<DetailCart> detailCarts) {
        this.detailCarts = detailCarts;
    }
}
