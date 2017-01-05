package com.blibli.future.Controller;

import com.blibli.future.Model.Customer;
import com.blibli.future.Model.Merchant;
import com.blibli.future.Model.Product;
import com.blibli.future.Model.User;
import com.blibli.future.repository.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Nita on 02/09/2016.
 */

@Controller
public class MainController {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    private Logger log = Logger.getLogger(MainController.class.getName());

    @RequestMapping("/")
    public String home (Model model) {

        List<Product> otherProduct = productRepository.findByCategory(Product.OTHER);
        List<Product> spiceProduct = productRepository.findByCategory(Product.SPICE);
        List<Product> herbsProduct = productRepository.findByCategory(Product.HERBS);

        model.addAttribute("herbsProduct",herbsProduct);
        model.addAttribute("spiceProduct",spiceProduct);
        model.addAttribute("otherProduct",otherProduct);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "index";
    }

    @RequestMapping("/login")
    public String login (HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "login" ;
    }

    @RequestMapping("/login/process")
    public String loginProcess (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);
        User u = userRepository.findByUsername(auth.getName());

        if(u instanceof Customer) {

            return "redirect:/";

        } else if (u instanceof Merchant) {

            return "redirect:/merchant";
        }

        return "redirect:/";
    }

    @RequestMapping("/herbs")
    public String herbs (Model model) {

        List<Product> herbsProduct = productRepository.findByCategory(Product.HERBS);
        model.addAttribute("herbsProduct",herbsProduct);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

        return "herbs";
    }

    @RequestMapping("/spices")
    public String spice (Model model) {

        List<Product> spiceProduct = productRepository.findByCategory(Product.SPICE);
        model.addAttribute("spiceProduct",spiceProduct);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

        return "spices";
    }

    @RequestMapping("/others")
    public String other (Model model) {

        List<Product> otherProduct = productRepository.findByCategory(Product.OTHER);
        model.addAttribute("otherProduct",otherProduct);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

        return "others";
    }

    @RequestMapping("/product/{id}")
    public String productDetail(@PathVariable String id, Model model){

        Product detailProduct = productRepository.findOne(Long.parseLong(id));
        model.addAttribute("product", detailProduct);

        return "product-details";
    }
}
