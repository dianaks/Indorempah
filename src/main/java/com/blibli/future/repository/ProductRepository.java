package com.blibli.future.repository;

import com.blibli.future.Model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Nita on 15/10/2016.
 */
public interface ProductRepository extends CrudRepository <Product,Long> {
    List<Product> findByCategory(String category);

}
