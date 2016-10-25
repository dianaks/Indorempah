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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    @PostMapping("merchant/product/save")
    public String addNewProduct(@ModelAttribute Product newProduct, Model model, @RequestParam("imagefile") MultipartFile file){
        log.info(newProduct.getDescription());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "picture");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + newProduct.getName() +".jpg");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());

                System.out.println("You successfully uploaded file=" + newProduct.getName() +".jpg");
                newProduct.setPicture(serverFile.getAbsolutePath());
            } catch (Exception e) {
                return "You failed to upload " + newProduct.getName() +".jpg" + " => " + e.getMessage();
            }
        } else {
            System.out.println("You failed to upload " + newProduct.getName() +".jpg" + " because the file was empty.");
        }

        productRepo.save(newProduct);
        model.addAttribute("newProduct",newProduct);
        return "redirect:/merchant/allProduct";
    }

    @RequestMapping("merchant/allProduct")
    public String showProduct(Model model){
        List<Product> products = (List<Product>) productRepo.findAll();
        model.addAttribute("products",products);
        return "merchant/allProduct";

    }

}