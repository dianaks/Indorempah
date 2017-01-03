package com.blibli.future.Controller;

import com.blibli.future.Model.*;
import com.blibli.future.repository.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import java.util.Date;
import java.util.List;

/**
 * Created by Fransiskus A K on 05/11/2016.
 */
@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    DetailOrderRepository detailOrderRepository;

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
    public String userCheckout (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);
        Customer customer = null;
        if (isLoginAsCustomer) {
            customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

       Order order = new Order();
       // Mengambil object cart yang ada di repositori cart
       Cart cart = cartRepo.findByCustomer(customer);

       order.setTotalPrice(cart.getTotalPrice());
       order.setCreatedDate(new Date());
       order.setCustomer(customer);
       orderRepo.save(order);
        // Untuk setiap DC yang ada didalam Cart, dapatkan detail cart
        for (DetailCart detailCart: cart.getDetailCarts()) {
            DetailOrder detailOrder = new DetailOrder();
            detailOrder.setAmount(detailCart.getAmount());
            detailOrder.setPrice(detailCart.getPrice());
            detailOrder.setProduct(detailCart.getProduct());

            detailOrder.setOrder(order);
            detailOrderRepository.save(detailOrder);
        }
        orderRepo.save(order);
        model.addAttribute("order",order);
        //copy

       return "/user/checkout";
    }

    @RequestMapping("/user/order/history")
    public String userOrderHistory(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;

        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

        List<Order> order = orderRepo.findByCustomer(customer);
        model.addAttribute("order",order);

        return "/user/history";
    }

    @RequestMapping("/user/profile")
    public String userProfile (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "/user/profile";
    }

    @RequestMapping("/user/profile/edit")
    public String userEditProfile (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "/user/edit-profile";
    }

    @RequestMapping("/checkout")
    public String checkout (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "/user/checkout";
    }
}