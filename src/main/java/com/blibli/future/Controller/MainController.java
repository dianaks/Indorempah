package com.blibli.future.Controller;

import com.blibli.future.Model.Product;
import com.blibli.future.Model.User;
import com.blibli.future.Model.UserRole;
import com.blibli.future.repository.ProductRepository;
import com.blibli.future.repository.UserRepository;
import com.blibli.future.repository.UserRoleRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nita on 02/09/2016.
 */

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepo;
    private Logger log = Logger.getLogger(MainController.class.getName());;

    @Autowired
    UserRoleRepository userRoleRepo;

    @Autowired
    ProductRepository productRepo;

    @RequestMapping("/")
    public String home (Model model) {

        List<Product> otherProduct = (List<Product>) productRepo.findByCategory(Product.OTHER);
        List<Product> spiceProduct = (List<Product>) productRepo.findByCategory(Product.SPICE);
        List<Product> herbsProduct = (List<Product>) productRepo.findByCategory(Product.HERBS);

        model.addAttribute("herbsProduct",herbsProduct);
        model.addAttribute("spiceProduct",spiceProduct);
        model.addAttribute("otherProduct",otherProduct);

        model.addAttribute("userMode", true);
        return "index";
    }

    @RequestMapping("/register")
    public String register (HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "register" ;
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute User newUser,  Model model){
         userRepo.save(newUser) ;
         //redirect halaman /home
        UserRole r = new UserRole();
        r.setEmail(newUser.getEmail());
        r.setRole("ROLE_USER");
        userRoleRepo.save(r);


        model.addAttribute("newUser", newUser);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login (HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "login" ;
    }

    @RequestMapping("/herbs")
    public String herbs (Model model) {

        List<Product> herbsProduct = (List<Product>) productRepo.findByCategory(Product.HERBS);
        model.addAttribute("herbsProduct",herbsProduct);

        return "herbs";
    }

    @RequestMapping("/spices")
    public String spice (Model model) {

        List<Product> spiceProduct = (List<Product>) productRepo.findByCategory(Product.SPICE);
        model.addAttribute("spiceProduct",spiceProduct);

        return "spices";
    }

    @RequestMapping("/others")
    public String other (Model model) {

        List<Product> otherProduct = (List<Product>) productRepo.findByCategory(Product.OTHER);
        model.addAttribute("otherProduct",otherProduct);

        return "others";
    }

    @RequestMapping("/cart")
    public String cart(){
        return "cart";
    }

    @RequestMapping("/product-details")
    public String productDetail(Model model){
        return "product-details";
    }

}
