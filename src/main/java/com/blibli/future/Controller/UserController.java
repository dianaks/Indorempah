package com.blibli.future.Controller;

import com.blibli.future.Model.Product;
import com.blibli.future.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Fransiskus A K on 05/11/2016.
 */
@Controller
public class UserController {
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