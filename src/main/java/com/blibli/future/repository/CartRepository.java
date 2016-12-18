package com.blibli.future.repository;

import com.blibli.future.Model.Cart;
import com.blibli.future.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Elisabet Diana K S on 16/12/2016.
 */
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByCustomer(Customer customer);
}
