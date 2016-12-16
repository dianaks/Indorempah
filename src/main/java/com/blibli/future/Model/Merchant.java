package com.blibli.future.Model;

import com.blibli.future.repository.UserRoleRepository;

import javax.persistence.Entity;

/**
 * Created by Fransiskus A K on 15/10/2016.
 */
@Entity
public class Merchant extends User {

    public void createUserRoleEntry(UserRoleRepository userRoleRepository)
    {
        UserRole r = new UserRole();
        r.setUsername(this.getUsername());
        r.setRole("ROLE_MERCHANT");
        userRoleRepository.save(r);
    }
}