package com.blibli.future.Controller;

import com.blibli.future.Model.*;
import com.blibli.future.repository.*;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fransiskus A K on 05/11/2016.
 */
@Controller
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DetailOrderRepository detailOrderRepository;
    @Autowired
    private DetailCartRepository detailCartRepository;
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
    @RequestMapping("/register")
    public String register (HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        return "register" ;
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute Customer customer, Model model){
        customer.createUserRoleEntry(userRoleRepository);
        customerRepository.save(customer) ;

        //redirect halaman /home
        model.addAttribute("newUser", customer);
        return "redirect:/";
    }

    @RequestMapping(value="/user/logout")
    public String logoutCustomer(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @RequestMapping("/product/in/{id}")
    public String productOrder(@PathVariable String id, Model model){
        // Cari produk yang diinginkan
        Product orderedProduct = productRepository.findOne(Long.parseLong(id));

        // Apakah sudah customer sudah login

        // Cek apa customer yang sedang login sudah punya cart
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);
        Cart cart = cartRepository.findByCustomer(customer);


        // Jika belum, buat cart baru dan masukkan produk
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
        }
        // jika produk sudah ada di cart maka tinggal di increment
        for (DetailCart detailCart: cart.getDetailCarts()
                ) {
            if(detailCart.getProduct().getId() == Long.valueOf(id)){
                int newAmount = detailCart.getAmount();
                newAmount+=1;
                detailCart.setAmount(newAmount);
                detailCart.updatePrice();
                detailCartRepository.save(detailCart);

                cart.updateTotalPrice();
                cartRepository.save(cart);
                return "redirect:/cart";
            }
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
        cartRepository.save(cart);

        return "redirect:/cart";
    }

    @RequestMapping("/cart")
    public String cart(Model model, HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);

        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Cart cart = cartRepository.findByCustomer(customer);
        model.addAttribute("cart", cart);

        if(cart == null){
            model.addAttribute("cartIsNull", true);
        }
        return "cart";
    }

    @RequestMapping("/detail-cart/{id}/delete")
    public String deleteCart(@PathVariable("id") Long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);

        detailCartRepository.delete(id);
        return "redirect:/cart";
    }

    @PostMapping("/detail-cart/update")
    public String updatingCart(HttpServletRequest request, Model model){
        DetailCart detailCart = detailCartRepository.findOne(Long.parseLong(request.getParameter("id")));
        detailCart.setAmount(Integer.parseInt(request.getParameter("amount")));
        detailCart.updatePrice();
        Cart cart = detailCart.getCart();
        cart.updateTotalPrice();
        cartRepository.save(cart);
        detailCartRepository.save(detailCart);

        return "redirect:/cart";
    }

    @RequestMapping("/user/checkout")
    public String userCheckout (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);

        Cart cart = cartRepository.findByCustomer(customer);
        model.addAttribute("cart", cart);

        return "/user/checkout";
    }

    @RequestMapping("/user/order/confirmation")
    public String userOrderConfirmation (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);
        Customer customer = null;
        if (isLoginAsCustomer) {
            customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

        Cart cart = cartRepository.findByCustomer(customer);

        List<Order> orders = new ArrayList<>();
        for (DetailCart detailCart : cart.getDetailCarts()) {
            System.out.println(detailCart.toString());
            Boolean found = false;
            for (Order o: orders) {
                System.out.println(o.toString());
                long id_in_order = o.getMerchant().getId();
                long id_in_detail_cart = detailCart.getProduct().getMerchant().getId();
                if(id_in_order == id_in_detail_cart){
                    Order order = o;
                    DetailOrder detailOrder = new DetailOrder();
                    detailOrder.setAmount(detailCart.getAmount());
                    detailOrder.setPrice(detailCart.getPrice());
                    detailOrder.setProduct(detailCart.getProduct());
                    detailOrder.setOrder(order);
                    detailOrderRepository.save(detailOrder);
                    found = true;
                }
            }
            if(found == false){
                Order order = new Order();
                order.setTotalPrice(cart.getTotalPrice());
                order.setCreatedDate(new Date());
                order.setCustomer(customer);
                order.setStatus(Order.STATUS_ONGOING);
                order.setMerchant(detailCart.getProduct().getMerchant());
                orderRepository.saveAndFlush(order);
                orders.add(order);

                DetailOrder detailOrder = new DetailOrder();
                detailOrder.setAmount(detailCart.getAmount());
                detailOrder.setPrice(detailCart.getPrice());
                detailOrder.setProduct(detailCart.getProduct());
                detailOrder.setOrder(order);
                detailOrderRepository.save(detailOrder);
                orderRepository.save(order);
            }
        }
        cartRepository.delete(cart);

        return "redirect:/user/order/history";
    }

    @RequestMapping("/user/order/history")
    public String userOrderHistory(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;

        Customer customer = customerRepository.findByUsername(auth.getName());
        model.addAttribute("customer", customer);
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);

        List<Order> order = orderRepository.findByCustomer(customer);
        model.addAttribute("order",order);

        return "/user/history";
    }

    @RequestMapping("/user/profile")
    public String userProfile (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "/user/profile";
    }

//    @RequestMapping("/user/profile/edit")
//    public String userEditProfile (Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        boolean isLoginAsCustomer = auth.isAuthenticated() &&
//                !(auth instanceof AnonymousAuthenticationToken) ;
//        if (isLoginAsCustomer) {
//            Customer customer = customerRepository.findByUsername(auth.getName());
//            model.addAttribute("customer", customer);
//        }
//        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
//        return "/user/edit-profile";
//    }


    @RequestMapping("/user/profile/edit")
    public String editProfile(HttpServletRequest request, Model model) {
        String _csrf = ((CsrfToken) request.getAttribute("_csrf")).getToken();
        model.addAttribute("_csrf", _csrf);

        Customer editableProfile = (Customer)authService().getAuthenticatedUser();
        model.addAttribute("customer", editableProfile);

        return "user/edit-profile";
    }



//space for saving edited profile
    //semangaat


    @RequestMapping("/checkout")
    public String checkout (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoginAsCustomer = auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken) ;
        if (isLoginAsCustomer) {
            Customer customer = customerRepository.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        model.addAttribute("isLoginAsCustomer", isLoginAsCustomer);
        return "/user/checkout";
    }


}