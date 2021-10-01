package com.example.client.controllers;

import com.example.client.bean.CartBean;
import com.example.client.bean.CartItemBean;
import com.example.client.bean.ProductBean;
import com.example.client.proxy.MsCartProxy;
import com.example.client.proxy.MsProductProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ClientController {

        @Autowired
        private MsProductProxy msProductProxy;

        @Autowired
        private MsCartProxy msCartProxy;

        private Long idCurrentCart = null;
    @RequestMapping("/")
        public String index(Model model) {
            if (idCurrentCart == null) {
                idCurrentCart = msCartProxy.createNewCart().getBody().getId();
            }
            List<ProductBean> products = msProductProxy.list();
            model.addAttribute("products", products);
            return "index";
        }

   /* @RequestMapping("/")
    public String details(Model model) {
        List<CartBean> cart = msCartProxy.list();
        model.addAttribute("products", products);
        return "ProductDetails";
    }*/

    @RequestMapping (value="/product/{id}")
    public String productDetail (@PathVariable Long id, Model model) {
        ProductBean product = msProductProxy.get(id).get();
        System.out.println(product.getName());
        model.addAttribute("product", product);
        return "productdetails";
    }

    @RequestMapping (value="/cart/addProduct/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public RedirectView addProductToCart (@PathVariable Long id, CartItemBean cartItemBean) {
        msCartProxy.addProductToCart(this.idCurrentCart, msCartProxy.createNewCartItem(cartItemBean).getBody());
        return new RedirectView("/cart");
    }



    @RequestMapping (value="/cart", method = RequestMethod.GET)
    public String cartDetail (Model model) {
        HashMap<ProductBean,Integer> prodcutMap = new HashMap<ProductBean,Integer>();
        //ArrayList<ProductBean>  productBeans = new ArrayList<ProductBean>();

        CartBean cartbean = msCartProxy.getCart(idCurrentCart).get();
        for (CartItemBean cartItemBean: cartbean.getProducts()) {
                prodcutMap.put(msProductProxy.get(cartItemBean.getProductId()).get(),cartItemBean.getQuantity());

        }
        model.addAttribute("products", prodcutMap);
        return "cart";
    }
}
