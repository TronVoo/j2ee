package com.example.LAB05.controller;

import com.example.LAB05.model.Order;
import com.example.LAB05.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId,
                            @RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
                            @RequestHeader(value = "Referer", required = false) String referer,
                            HttpSession session) {
        cartService.addToCart(session, productId, quantity);

        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }

        return "redirect:/products";
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        model.addAttribute("cartItems", cartService.getCartItems(session));
        model.addAttribute("totalAmount", cartService.getTotalAmount(session));
        model.addAttribute("cartCount", cartService.getTotalQuantity(session));

        return "cart/index";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam("productId") Integer productId,
                                 @RequestParam("quantity") Integer quantity,
                                 HttpSession session) {
        cartService.updateQuantity(session, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeItem(@PathVariable Integer productId, HttpSession session) {
        cartService.removeItem(session, productId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        Order order = cartService.checkout(session);

        if (order == null) {
            return "redirect:/cart";
        }

        return "redirect:/cart/checkout-success?orderId=" + order.getId();
    }

    @GetMapping("/checkout-success")
    public String checkoutSuccess(@RequestParam("orderId") Long orderId,
                                  HttpSession session,
                                  Model model) {
        Order order = cartService.getOrderById(orderId);

        if (order == null) {
            return "redirect:/cart";
        }

        model.addAttribute("order", order);
        model.addAttribute("cartCount", cartService.getTotalQuantity(session));

        return "cart/checkout-success";
    }
}
