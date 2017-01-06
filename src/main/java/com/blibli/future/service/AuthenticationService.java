package com.blibli.future.service;

import com.blibli.future.Model.Customer;
import com.blibli.future.Model.Merchant;
import com.blibli.future.Model.User;
import com.blibli.future.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Elisabet Diana K S on 06/01/2017.
 */
@Component
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    public boolean isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        return loggedIn;
    }

    public User getAuthenticatedUser() {
        if (isLoggedIn()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findByUsername(auth.getName());
        }
        return null;
    }

    public boolean isLoggedAsCustomer() {
        if (isLoggedIn()) {
            return getAuthenticatedUser() instanceof Customer;
        }
        return false;
    }

    public boolean isLoggedAsMerchant() {
        if (isLoggedIn()) {
            return getAuthenticatedUser() instanceof Merchant;
        }
        return false;
    }
}
