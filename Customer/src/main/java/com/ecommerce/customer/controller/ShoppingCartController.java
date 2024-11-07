package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getCart();
        if (shoppingCart == null) {
            model.addAttribute("check");
        }
        if (shoppingCart != null) {
            model.addAttribute("grandTotal", shoppingCart.getTotalPrice());
        }
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("title", "Cart");
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {
        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(productDto, quantity, username);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("shoppingCart", shoppingCart);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        ;
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action-delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if(principal==null){
            return "redirect:/login";
        }else {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }
}
