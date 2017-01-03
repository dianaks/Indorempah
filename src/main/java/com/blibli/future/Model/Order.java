package com.blibli.future.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fransiskus A K on 14/11/2016.
 */
@Entity
@Table(name="indorempah_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date createdDate;
    private long totalPrice;

    @OneToMany(fetch = FetchType.EAGER)
    private List<DetailOrder> detailOrders = new ArrayList<>();

    @ManyToOne
    private Customer customer;


    public Order() {

    }

    public long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<DetailOrder> getDetailOrders() {
        return detailOrders;
    }

    public void setDetailOrders(List<DetailOrder> detailOrders) {
        this.detailOrders = detailOrders;
    }

    public void addDetailOrder(DetailOrder detailOrder){
        this.detailOrders.add(detailOrder);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

