package com.blibli.future.repository;

import com.blibli.future.Model.Customer;
import com.blibli.future.Model.Merchant;
import com.blibli.future.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Elisabet Diana K S on 01/01/2017.
 */
public interface OrderRepository  extends JpaRepository<Order,Long> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByMerchant(Merchant merchant);
}
