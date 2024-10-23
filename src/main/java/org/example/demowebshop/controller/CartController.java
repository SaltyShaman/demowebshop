package org.example.demowebshop.controller;

import jakarta.servlet.http.HttpSession;
import org.example.demowebshop.model.Cart;
import org.example.demowebshop.model.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private Cart cart; // Reference to the shopping cart

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        // Check if the 'cart' attribute exists in the session object
        cart = (Cart) session.getAttribute("cart");

        // If not, create a new cart and set it in the session
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            session.setMaxInactiveInterval(30); // Set session lifetime to 30 seconds for testing purposes
        }

        // Add 'items' and 'total' attributes to the model object
        model.addAttribute("items", cart.getItems());
        model.addAttribute("total", cart.getTotal());

        // Return cart.html to display the UI
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String name, @RequestParam double price, @RequestParam int quantity) {
        // Create a new CartItem
        CartItem newItem = new CartItem(name, price, quantity);

        // Add item to the cart
        cart.addItem(newItem);

        // Redirect to /cart
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam int index) {
        // Remove item at 'index' in the cart
        cart.removeItem(index);

        // Redirect to /cart
        return "redirect:/cart";
    }

    @PostMapping("/cart/empty")
    public String emptyCart(HttpSession session) {
        // Remove 'cart' attribute from session object
        session.removeAttribute("cart");

        // Redirect to /cart
        return "redirect:/cart";
    }
}

