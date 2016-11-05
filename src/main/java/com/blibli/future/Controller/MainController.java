package com.blibli.future.Controller;

import com.blibli.future.Model.Product;
import com.blibli.future.Model.User;
import com.blibli.future.repository.ProductRepository;
import com.blibli.future.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ProductRepository productRepo;

    private Logger log = Logger.getLogger(MainController.class.getName());;

    @RequestMapping("/")
    public String greeting(Model model) {

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
    public String greeting2(Model model) {
        String nama2 = "pengunjung kami :)";
        model.addAttribute("pengunjung", nama2);
        return "register" ;
    }
    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute User newUser, Model model){
         userRepo.save(newUser) ;
         //redirect halaman /home
        model.addAttribute("newUser", newUser);
        return "index";

    }
    @RequestMapping("/login2")
    public String greeting4(Model model) {
        String nama3 = "pengunjung kami :)";
        model.addAttribute("pengunjung2", nama3);
        return "login2";
    }
    @RequestMapping("/login")
    public String greeting5() {
        return "login" ;
        }

    @RequestMapping("/care")
    public String greeting6() {
        return "care" ;

    }
    @RequestMapping("/kitchen")
    public String greeting7() {
        return "kitchen";
    }

}
