package com.blibli.future.Model;

import com.blibli.future.repository.UserRoleRepository;

import javax.persistence.Entity;

/**
 * Created by Elisabet Diana K S on 06/12/2016.
 */
@Entity
public class Customer extends User
{
    private String firstName;
    private String lastName;

    public Customer()
    {
        super();
    }

    public void createUserRoleEntry(UserRoleRepository userRoleRepository)
    {
        UserRole r = new UserRole();
        r.setUsername(this.getUsername());
        r.setRole("ROLE_USER");
        userRoleRepository.save(r);
    }
}
