package com.blibli.future.repository;

import com.blibli.future.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Elisabet Diana K S on 05/01/2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
