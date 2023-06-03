package com.thanhnguyen.smartCity.controller;

import com.thanhnguyen.smartCity.model.Item;
import com.thanhnguyen.smartCity.model.Product;
import com.thanhnguyen.smartCity.repository.ProductRepository;
import com.thanhnguyen.smartCity.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private ProductRepository productRepository;



    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "products/index";
    }

    private int exists(int productId, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getProductId() == productId) {
                return i; // Return the index if the item exists
            }
        }
        return -1; // Return -1 if the item does not exist
    }
    @RequestMapping(value = "/addItemToCart", method = RequestMethod.GET)
    public String addItem(HttpSession session, @RequestParam int productId, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.get() != null){
            if (session.getAttribute("cart") == null) {
                List<Item> cart = new ArrayList<Item>();
                cart.add(new Item(optionalProduct.get(), 1));
                session.setAttribute("cart", cart);
            } else {
                List<Item> cart = (List<Item>) session.getAttribute("cart");
                int index = this.exists(productId, cart);
                if (index == -1) {
                    cart.add(new Item(optionalProduct.get(), 1));
                } else {
                    int quantity = cart.get(index).getQuantity() + 1;
                    cart.get(index).setQuantity(quantity);
                }
                session.setAttribute("cart", cart);
            }
        } else {
            System.out.println("No Product!!!!");
        }
        List<Item> cartItem = (List<Item>) session.getAttribute("cart");
        int totalItems = 0;
        if (cartItem != null) {
            totalItems = cartItem.stream().mapToInt(Item::getQuantity).sum();
        }

        // Calculate total cost of items in the cart
        List<Item> cartItems = (List<Item>) session.getAttribute("cart");
        double totalPrice = 0;
        if (cartItems != null) {
            totalPrice = cartItems.stream()
                    .mapToDouble(item -> item.getProduct().getFees() * item.getQuantity())
                    .sum();
        }
        model.addAttribute("totalPrice", totalPrice);
        session.setAttribute("totalPrice", totalPrice);

        model.addAttribute("totalItems", totalItems);
        session.setAttribute("totalItems", totalItems);
        model.addAttribute("cart", session.getAttribute("cart"));

        return "redirect:/product";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String remove(@RequestParam int productId, HttpSession session) {
        Product productModel = new Product();
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index = this.exists(productId, cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        return "redirect:/product";
    }

    @RequestMapping(value = {"/payment"})
    public ModelAndView displayHomePage(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("shopping_cart.html");
        modelAndView.addObject("cart",session.getAttribute("cart"));
        // Calculate total cost of items in the cart
        List<Item> cartItems = (List<Item>) session.getAttribute("cart");
        double totalPrice = 0;
        if (cartItems != null) {
            totalPrice = cartItems.stream()
                    .mapToDouble(item -> item.getProduct().getFees() * item.getQuantity())
                    .sum();
        }
        model.addAttribute("totalPrice", totalPrice);
        session.setAttribute("totalPrice", totalPrice);


        return modelAndView;
    }
}

