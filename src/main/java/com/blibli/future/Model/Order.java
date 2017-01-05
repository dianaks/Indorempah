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
    public static final String STATUS_DONE="Done";
    public static final String STATUS_ONGOING="Ongoing";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date createdDate;
    private long totalPrice;
    private String status;
    @OneToMany(fetch = FetchType.EAGER)
    private List<DetailOrder> detailOrders = new ArrayList<>();

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Merchant merchant;


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

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", totalPrice=" + totalPrice +
                ", detailOrders=" + detailOrders +
                ", customer=" + customer +
                ", merchant=" + merchant +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStatusDone(){
        return status.equals(STATUS_DONE);
    }

    public boolean isStatusOngoing(){
        return status.equals(STATUS_ONGOING);
    }

}

