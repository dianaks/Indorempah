package com.blibli.future.Controller;

import com.blibli.future.Model.Cart;
import com.blibli.future.Model.Customer;
import com.blibli.future.Model.DetailCart;
import com.blibli.future.Model.Product;
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
    UserRoleRepository userRoleRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private DetailCartRepository detailCartRepository;


    private Logger log = Logger.getLogger(MainController.class.getName());

    @RequestMapping("/")
    public String home (Model model) {

        List<Product> otherProduct = productRepo.findByCategory(Product.OTHER);
        List<Product> spiceProduct = productRepo.findByCategory(Product.SPICE);
        List<Product> herbsProduct = productRepo.findByCategory(Product.HERBS);

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

    @RequestMapping("/herbs")
    public String herbs (Model model) {

        List<Product> herbsProduct = productRepo.findByCategory(Product.HERBS);
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

        List<Product> spiceProduct = productRepo.findByCategory(Product.SPICE);
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

        List<Product> otherProduct = productRepo.findByCategory(Product.OTHER);
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

    @RequestMapping("/cart")
    public String cart(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);
        Cart cart = cartRepo.findByCustomer(customer);
        model.addAttribute("cart", cart);
        System.out.println(cart.getDetailCarts().get(0).getProduct().getName());
        return "cart";
    }

    @RequestMapping("/product/{id}")
    public String productDetail(@PathVariable String id, Model model){

        Product detailProduct = productRepo.findOne(Long.parseLong(id));
        model.addAttribute("product", detailProduct);

        return "product-details";
    }

    @RequestMapping("/product/in/{id}")
    public String productOrder(@PathVariable String id, Model model){
        // Cari produk yang diinginkan
        Product orderedProduct = productRepo.findOne(Long.parseLong(id));

        // Apakah sudah customer sudah login

        // Cek apa customer yang sedang login sudah punya cart
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);
        Cart cart = cartRepo.findByCustomer(customer);


        // Jika belum, buat cart baru dan masukkan produk
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepo.save(cart);
        }

        // tambahkan produk ke dalam cart
        DetailCart detailCart = new DetailCart();
        detailCart.setProduct(orderedProduct);
        detailCart.setAmount(1);
        detailCart.updatePrice();
        detailCartRepository.save(detailCart);
        detailCart.setCart(cart);
        detailCartRepository.save(detailCart);

        cart.updateTotalPrice();
        cartRepo.save(cart);

        return "redirect:/cart";
    }

}
