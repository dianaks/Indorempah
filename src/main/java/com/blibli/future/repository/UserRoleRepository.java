package com.blibli.future.repository;

import com.blibli.future.Model.User;
import com.blibli.future.Model.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Fransiskus A K on 23/10/2016.
 */
public interface UserRoleRepository extends CrudRepository< UserRole, Long> {

}