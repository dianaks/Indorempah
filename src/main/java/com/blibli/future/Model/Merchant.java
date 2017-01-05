package com.blibli.future.Model;

import com.blibli.future.repository.UserRoleRepository;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fransiskus A K on 15/10/2016.
 */
@Entity
public class Merchant extends User {

    @OneToMany
    private List<Product> products = new ArrayList<>();

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    public void createUserRoleEntry(UserRoleRepository userRoleRepository)
    {
        UserRole r = new UserRole();
        r.setUsername(this.getUsername());
        r.setRole("ROLE_MERCHANT");
        userRoleRepository.save(r);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}