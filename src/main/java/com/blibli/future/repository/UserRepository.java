package com.blibli.future.repository;

import com.blibli.future.Model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Fransiskus A K on 15/10/2016.
 */
public interface UserRepository extends CrudRepository<User, Long > {
}
