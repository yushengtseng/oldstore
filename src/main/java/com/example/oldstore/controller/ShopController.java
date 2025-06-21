package com.example.oldstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.oldstore.model.dto.ProductDto;
import com.example.oldstore.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public String showProductList(Model model) {
		List<ProductDto> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "shop/product_list";
	}
}
