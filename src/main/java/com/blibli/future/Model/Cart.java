package com.blibli.future.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nita on 15/10/2016.
 */
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long totalPrice;

    // relasi
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private List<DetailCart> detailCarts = new ArrayList<>();

    @ManyToOne
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DetailCart> getDetailCarts() {
        return detailCarts;
    }

    public void setDetailCarts(List<DetailCart> detailCarts) {
        this.detailCarts = detailCarts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void updateTotalPrice(){
        int total = 0;
        for (DetailCart detailCart: this.detailCarts ) {
            total += detailCart.getProduct().getPrice();
        }
        totalPrice = total;
    }

    public void addDetailCart(DetailCart detailCart){
        this.detailCarts.add(detailCart);
    }
}
