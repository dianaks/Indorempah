package com.blibli.future.Controller;

import com.blibli.future.Model.Customer;
import com.blibli.future.repository.CustomerRepository;
import com.blibli.future.repository.UserRoleRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Fransiskus A K on 05/11/2016.
 */
@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
//    @Autowired
//    ProductRepository productRepo;
//
//    @RequestMapping("/herbs")
//    public String showProduct(Model model) {
//        List<Product> products = (List<Product>) productRepo.findAll();
//        model.addAttribute("products", products);
//        return "/user/allProductUser";
//
//    }
    @RequestMapping("/register")
    public String register (HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "register" ;
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute Customer customer, Model model){
        customer.createUserRoleEntry(userRoleRepository);
        customerRepository.save(customer) ;

        //redirect halaman /home
        model.addAttribute("newUser", customer);
        return "redirect:/";
    }

    @RequestMapping(value="/user/logout")
    public String logoutCustomer(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @RequestMapping("/user/checkout")
    public String userCheckout (Model model){
        return "/user/checkout";
    }

    @RequestMapping("/user/order/history")
    public String userOrderHistory(Model model){
        return "/user/history";
    }

    @RequestMapping("/user/profile")
    public String userProfile (Model model){
        return "/user/profile";
    }

    @RequestMapping("/user/profile/edit")
    public String userEditProfile (Model model){
        return "/user/edit-profile";
    }
}