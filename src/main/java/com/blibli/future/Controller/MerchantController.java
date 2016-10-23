package com.blibli.future.Controller;

import com.blibli.future.Model.Product;
import com.blibli.future.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Nita on 21/10/2016.
 */
@Controller
public class MerchantController {
    @Autowired
    ProductRepository productRepo;

    private Logger log = Logger.getLogger(MerchantController.class.getName());

    @RequestMapping("merchant/product/upload")
    public String greeting8(){
        return "merchant/upload"; }

    @RequestMapping("merchant/allProduct")
    public String greeting9(){
        return "merchant/allProduct";
    }

    @PostMapping("merchant/product/save")
    public String addNewProduct(@ModelAttribute Product newProduct, Model model){
        log.info(newProduct.getDescription());
        productRepo.save(newProduct);
        model.addAttribute("newProduct",newProduct);
        return "redirect:/merchant/product/show";
    }

    @RequestMapping("merchant/product/show")
    public String showProduct(Model model){
        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products",products);
        return "merchant/allProduct";

    }

}