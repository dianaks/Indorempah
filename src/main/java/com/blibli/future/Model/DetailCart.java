package com.blibli.future.Model;

import javax.persistence.*;

/**
 * Created by Nita on 15/10/2016.
 */
@Entity
public class DetailCart {
    @Id

    private long id;
    private Integer amount;
    private Integer price;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Product product;
    public Product getProduct() {
        return product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
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
}
