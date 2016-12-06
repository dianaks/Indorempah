package com.blibli.future.repository;

import com.blibli.future.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Fransiskus A K on 15/10/2016.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long > {
    Customer findByUsername(String username);
}
