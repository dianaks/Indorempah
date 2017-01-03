package com.blibli.future.repository;

import com.blibli.future.Model.Customer;
import com.blibli.future.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Elisabet Diana K S on 01/01/2017.
 */
public interface OrderRepository  extends JpaRepository<Order,Long> {
    Order findByCustomer(Customer customer);
}
