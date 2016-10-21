package com.blibli.future.Controller;

import com.blibli.future.Model.Product;
import com.blibli.future.repository.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Nita on 21/10/2016.
 */
public class MerchantController {
    @Autowired
    ProductRepository productRepo;

    @Autowired
    private Logger log = Logger.getLogger(MerchantController.class.getName());

    @PostMapping("/merchant/product/upload")
    public String addNewProduct(@ModelAttribute Product newProduct, Model model){
        log.info(newProduct.getDescription());
        productRepo.save(newProduct);

        return "redirect:/merchant/allProduct";
    }
}