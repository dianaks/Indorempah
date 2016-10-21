package com.blibli.future.repository;

import com.blibli.future.Model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Nita on 15/10/2016.
 */
public interface ProductRepository extends CrudRepository <Product,Long> {

}
