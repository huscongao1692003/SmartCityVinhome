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


    @RequestMapping(value = {"/payment"})
    public String displayHomePage() {
        return "shopping_cart.html";
    }

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
    public ModelAndView addItem(HttpSession session, @RequestParam int productId, Model model) {
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

        ModelAndView modelAndView = new ModelAndView("products.html");

        model.addAttribute("cart", session.getAttribute("cart")); // Add the cart attribute to the model
        return modelAndView;
    }


}

