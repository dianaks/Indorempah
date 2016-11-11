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

        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products",products);

        List<Product> herbsProduct = new ArrayList<>();
        List<Product> spiceProduct = new ArrayList<>();
        List<Product> otherProduct = new ArrayList<>();

        for (Product p: products) {
            System.out.println(p);
            if(p.isHerbs()){
                herbsProduct.add(p);
                System.out.println(herbsProduct);
            }
            else if(p.isSpice()) {
                spiceProduct.add(p);
            }
            else if(p.isOther()) {
                otherProduct.add(p);
            }
        }

        model.addAttribute("herbsProduct",herbsProduct);
        model.addAttribute("spiceProduct",spiceProduct);
        model.addAttribute("otherProduct",otherProduct);
        return "index";
    }

    @RequestMapping("/register")
    public String register (HttpServletRequest request, Model model) {
        String nama2 = "pengunjung kami :)";
        model.addAttribute("pengunjung", nama2);

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
        return "index";
    }

    @RequestMapping("/login")
    public String login (HttpServletRequest request, Model model) {


        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "login" ;
    }

    @RequestMapping("/herbs")
    public String herbs (Model model) {

        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products",products);

        List<Product> herbsProduct = new ArrayList<>();

        for (Product p: products) {
            if(p.isHerbs()){
                herbsProduct.add(p);
            }
        }

        model.addAttribute("herbsProduct",herbsProduct);

        return "herbs";
    }

    @RequestMapping("/spices")
    public String spice (Model model) {

        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products",products);

        List<Product> spiceProduct = new ArrayList<>();

        for (Product p: products) {
            if(p.isSpice()) {
                spiceProduct.add(p);
            }
        }

        model.addAttribute("spiceProduct",spiceProduct);
        return "spices";
    }

    @RequestMapping("/other")
    public String other (Model model) {

        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products",products);

        List<Product> otherProduct = new ArrayList<>();

        for (Product p: products) {
            if(p.isOther()) {
                otherProduct.add(p);
            }
        }
        model.addAttribute("otherProduct",otherProduct);
        return "other";
    }

}
