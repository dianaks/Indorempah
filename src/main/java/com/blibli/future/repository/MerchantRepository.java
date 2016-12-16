package com.blibli.future.repository;

import com.blibli.future.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Elisabet Diana K S on 16/12/2016.
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Merchant findByUsername(String username);
}



