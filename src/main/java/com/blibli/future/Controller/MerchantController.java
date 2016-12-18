package com.blibli.future.Controller;

import com.blibli.future.Model.Merchant;
import com.blibli.future.Model.Product;
import com.blibli.future.repository.MerchantRepository;
import com.blibli.future.repository.ProductRepository;
import com.blibli.future.repository.UserRoleRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    MerchantRepository merchantRepo;
    @Autowired
    UserRoleRepository userRoleRepository;

    private Logger log = Logger.getLogger(MerchantController.class.getName());

    @RequestMapping("merchant")
    public String dashboard(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsMerchant = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsMerchant) {
            Merchant merchant = merchantRepo.findByUsername(auth.getName());
            model.addAttribute("merchant", merchant);
        }
        model.addAttribute("isLoginAsMerchant", isLoginAsMerchant);
        return"merchant/merchant-home";
    }
    @RequestMapping("/merchant/register")
    public String register (HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "merchant/register" ;
    }

    @PostMapping("/merchant/register")
    public String registerNewUser(@ModelAttribute Merchant merchant, Model model){
        merchant.createUserRoleEntry(userRoleRepository);
        merchantRepo.save(merchant) ;

        //redirect halaman /home
        model.addAttribute("newUser", merchant);
        return "redirect:/merchant";
    }
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
        return "redirect:/merchant/product";
    }

    @RequestMapping("merchant/product")
    public String showProduct(HttpServletRequest request, Model model){
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        List<Product> products = productRepo.findAll();
        model.addAttribute("products",products);
        return "merchant/product";
    }

    @RequestMapping("/merchant/product/{id}/edit")
    public String editProduct(HttpServletRequest request, @PathVariable String id, Model model){
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Product editableProduct = productRepo.findOne(Long.parseLong(id));
        model.addAttribute("product", editableProduct);

        return "merchant/edit-product";
    }

    @RequestMapping("/merchant/product/{id}")
    public String showOneProduct(HttpServletRequest request, @PathVariable String id, Model model){
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Product editableProduct = productRepo.findOne(Long.parseLong(id));
        model.addAttribute("product", editableProduct);

        return "merchant/product/edit-product";
    }


    @PostMapping("/merchant/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id, Model model){
        productRepo.delete(id);

        return "redirect:/merchant/product";
    }

}