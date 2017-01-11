package com.blibli.future.Controller;

import com.blibli.future.Model.Merchant;
import com.blibli.future.Model.Order;
import com.blibli.future.Model.Product;
import com.blibli.future.Model.User;
import com.blibli.future.repository.MerchantRepository;
import com.blibli.future.repository.OrderRepository;
import com.blibli.future.repository.ProductRepository;
import com.blibli.future.repository.UserRoleRepository;
import com.blibli.future.service.AuthenticationService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private AuthenticationService authenticationService;

    private Logger log = Logger.getLogger(MainController.class.getName());

    @ModelAttribute("authService")
    public AuthenticationService authService() {
        return authenticationService;
    }

    @ModelAttribute("activeUser")
    public User getActiveUser() {
        return authenticationService.getAuthenticatedUser();
    }

    @RequestMapping("merchant")
    public String dashboard(HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsMerchant = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);

        Merchant merchant = merchantRepo.findByUsername(auth.getName());
        model.addAttribute("merchant", merchant);

        model.addAttribute("isLoginAsMerchant", isLoginAsMerchant);

        List<Order> order = orderRepository.findByMerchant(merchant);
        model.addAttribute("order", order);
        return "merchant/merchant-home";
    }

    @RequestMapping("merchant/order/{id}/accept")
    public String accept(HttpServletRequest request, Model model, @PathVariable String id) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Order acceptedOrder = orderRepository.findOne(Long.parseLong(id));
        acceptedOrder.setStatus(Order.STATUS_DONE);
        orderRepository.save(acceptedOrder);
        model.addAttribute("order", acceptedOrder);

        return "redirect:/merchant";
    }

    @RequestMapping("/register/merchant")
    public String register(HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "merchant/register";
    }

    @PostMapping("/register/merchant")
    public String registerNewUser(@ModelAttribute Merchant merchant, Model model) {
        merchant.createUserRoleEntry(userRoleRepository);
        merchant.setPicture("/assets/images/av.png");
        merchant.setCompanyName("");
        merchant.setCompanyAddress("");
        merchant.setPhoneNumber("");
        merchant.setBankAccountNumber("");
        merchantRepo.save(merchant);

        //redirect halaman /home
        model.addAttribute("newUser", merchant);
        return "redirect:/merchant";
    }

//    @RequestMapping("merchant/profile/edit")
//    public String merchantEditProfile(Model model){
//        return"merchant/edit-profile";
//    }


    //dikomen frans 10.27 alias terakhir
//    @RequestMapping("/merchant/profile/edit")
//    public String merchantEditProfile(HttpServletRequest request, Model model) {
//        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
//        model.addAttribute("_csrf", _csrf);
//
//        Merchant editableProfileMerchant = (Merchant) authService().getAuthenticatedUser();
//        model.addAttribute("merchant", editableProfileMerchant);
//
//        return "merchant/edit-profile";
//    }


//    @RequestMapping("/merchant/profile/edit")
//    public String merchantEditProfile(HttpServletRequest request, Model model) {
//        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
//        model.addAttribute("_csrf", _csrf);
//
//        Merchant editableProfileMerchant = (Merchant) authService().getAuthenticatedUser();
//        model.addAttribute("merchant", editableProfileMerchant);
//
//        return "merchant/edit-profile";
//    }

    @RequestMapping("merchant/product/upload")
    public String greeting81(HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);
        return "merchant/upload";
    }

    @PostMapping("merchant/product/save")
    public String addNewProduct(@ModelAttribute Product newProduct, Model model, @RequestParam("imagefile") MultipartFile file) {
        log.info(newProduct.getDescription());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "picture");
                if (!dir.exists())
                    dir.mkdirs();

                long timeStamp = System.currentTimeMillis();
                // Create the file on server
                File serverFile = new File("D:\\xampp\\htdocs\\picture\\" +
                        newProduct.getName() + timeStamp + ".jpg");
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());
                newProduct.setPicture("http://localhost/picture/" + newProduct.getName() + timeStamp + ".jpg");
            } catch (Exception e) {
                return "You failed to upload " + newProduct.getName() + ".jpg" + " => " + e.getMessage();
            }
        } else {
            System.out.println("You failed to upload " + newProduct.getName() + ".jpg" + " because the file was empty.");
        }
        Merchant merchant;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        merchant = merchantRepo.findByUsername(auth.getName());
        model.addAttribute("merchant", merchant);

        newProduct.setMerchant(merchant);
        productRepo.save(newProduct);
        model.addAttribute("newProduct", newProduct);

        return "redirect:/merchant/product";
    }

    @RequestMapping("merchant/product")
    public String showProduct(HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Merchant merchant = merchantRepo.findByUsername(auth.getName());

        List<Product> products = productRepo.findByMerchant(merchant);
        model.addAttribute("products", products);
        return "merchant/product";
    }


    @RequestMapping(value = "/merchant/product/{id}/edit", method = RequestMethod.GET)
    public String editProduct(
            HttpServletRequest request,
            Model model,
            @PathVariable Long id
    ) {

        Product editableProduct = productRepo.findOne(id);
        model.addAttribute("product", editableProduct);
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);
        return "merchant/edit-product";
    }

    @RequestMapping(value = "/merchant/product/{id}/edit", method = RequestMethod.POST)
    public String saveEditProduct( HttpServletRequest request, @PathVariable Long id, Model model)
    {
        Product editableProduct = productRepo.findOne(id);
        model.addAttribute("product", editableProduct);

        editableProduct.setName(request.getParameter("name"));
        editableProduct.setCategory(request.getParameter("category"));
        editableProduct.setDescription(request.getParameter("description"));
        editableProduct.setUnit(request.getParameter("unit"));
        editableProduct.setFirstPrice(Integer.parseInt(request.getParameter("firstPrice")));
        editableProduct.setSecondPrice(Integer.parseInt(request.getParameter("secondPrice")));
        editableProduct.setFirstMinQuantity(Integer.parseInt(request.getParameter("firstMinQuantity")));
        editableProduct.setSecondMinQuantity(Integer.parseInt(request.getParameter("secondMinQuantity")));
//        editableProduct.setStatus(request.getParameter("status"));
//        editableProduct.setPicture(request.getParameter("picture"));
        editableProduct.setAmount(request.getParameter("amount"));

        productRepo.save(editableProduct);
        return "redirect:/merchant";
    }


        //last saved by frans 11.21
//    @RequestMapping("/merchant/product/{id}/edit")
//    public String editProduct(HttpServletRequest request, @PathVariable String id, Model model) {
//        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
//        model.addAttribute("_csrf", _csrf);
//
//        Product editableProduct = productRepo.findOne(Long.parseLong(id));
//        model.addAttribute("product", editableProduct);
////
//        return "merchant/edit-product";
//    }


//
//@RequestMapping("merchant/product/{{id}}/editedSave" method= RequestMethod.POST  )
//public String saveEditedProduct(
//        @RequestParam("file") MultipartFile file,
//        HttpServletRequest request)
//{
//    Product product = helper.getCurrentProduct();
//
////    photo
////    Calendar cal = Calendar.getInstance();
////    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
////    String formatted = format1.format(cal.getTime());
//
//    if(product != null){
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//
//                String fileName = UUID.randomUUID().toString().replaceAll("-","");
//
//                // Creating the directory to store file
//                File dir = new File(env.getProperty("Indorempah.PhotoDir.path") + "/Catering/" + formatted);
//                if (!dir.exists())
//                    dir.mkdirs();
//
//                // Create the file on server
//                File serverFile = new File(dir.getAbsolutePath()
//                        + File.separator + fileName + ".jpg");
//                product.setPicture("http://localhost/gambar/Catering"
//                        + File.separator + formatted + File.separator + fileName + ".jpg");
//                BufferedOutputStream stream = new BufferedOutputStream(
//                        new FileOutputStream(serverFile));
//                stream.write(bytes);
//                stream.close();
//
//                logger.info("Server File Location="
//                        + serverFile.getAbsolutePath());
//
//            } catch (Exception e) {
//                return "You failed to upload " + product.getName() + " => " + e.getMessage();
//            }
//        } else {
//            return "You failed to upload " + product.getName()
//                    + " because the file was empty.";
//        }
//    }
//
//    product.setName(request.getParameter("name"));
//    product.setFirstPrice(request.getParameter("firstPrice"));
//    product.setSecondPrice(request.getParameter("secondPrice"));
//    product.setDescription(request.getParameter("description"));
//    product.setCategory(request.getParameter("category"));
//    product.setUnit(request.getParameter("unit"));
//
//    //product.setDp(request.getParameter("dp"));
//    productRepo.save(product);
//    return "redirect:/merchant/product";
//}

    @RequestMapping("/merchant/product/{id}")
    public String showOneProduct(HttpServletRequest request, @PathVariable String id, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Product editableProduct = productRepo.findOne(Long.parseLong(id));
        model.addAttribute("product", editableProduct);

        return "merchant/product";
    }


    @PostMapping("/merchant/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        productRepo.delete(id);

        return "redirect:/merchant/product";
    }

    @RequestMapping(value = "/merchant/logout")
    public String logoutMerchant(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

//dikomen 10.52 frans terakhir
//    @RequestMapping(value="/merchant/profile/edit")
//    public String editMerchantProfile(
//            HttpServletRequest request,
//            Model model) {
//        Merchant merchant = (Merchant) authenticationService.getAuthenticatedUser();
//        model.addAttribute("merchant", merchant);
//        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
//        model.addAttribute("_csrf", _csrf);
//
//        merchant.setPassword(request.getParameter("password"));
//        merchant.setEmail(request.getParameter("email"));
//        merchant.setCompanyName(request.getParameter("companyName"));
//        merchant.setCompanyAddress(request.getParameter("companyAddress"));
//        merchant.setPhoneNumber(request.getParameter("phoneNumber"));
//        merchant.setBankAccountNumber(request.getParameter("bankAccountNumber"));
//        merchantRepo.save(merchant);
//        return "redirect:/merchant";
//    }
//}

    @RequestMapping("/merchant/profile")
    public String merchantProfile (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Merchant merchant = merchantRepo.findByUsername(auth.getName());
            model.addAttribute("merchant", merchant);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "/merchant/profile";
    }
    @RequestMapping(value = "/merchant/profile/edit", method = RequestMethod.GET)
    public String editMerchant(
            HttpServletRequest request,
            Model model) {
        Merchant merchant = (Merchant) authenticationService.getAuthenticatedUser();
        model.addAttribute("merchant", merchant);
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);
        return "merchant/edit-profile";
    }

    @RequestMapping(value = "/merchant/profile/edit", method = RequestMethod.POST)
    public String saveEditMerchant(
            HttpServletRequest request) {
        Merchant merchant = (Merchant) authenticationService.getAuthenticatedUser();

        merchant.setPassword(request.getParameter("password"));
        merchant.setEmail(request.getParameter("email"));
        merchant.setCompanyName(request.getParameter("companyName"));
        merchant.setCompanyAddress(request.getParameter("companyAddress"));
        merchant.setPhoneNumber(request.getParameter("phoneNumber"));
        merchant.setBankAccountNumber(request.getParameter("bankAccountNumber"));
        merchantRepo.save(merchant);
        return "redirect:/merchant";
    }
}