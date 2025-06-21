package com.example.oldstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.oldstore.exception.ImageUploadException;
import com.example.oldstore.model.dto.ProductDto;
import com.example.oldstore.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查詢所有商品
    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductDto> productDtos = productService.getAllProducts();
        model.addAttribute("productDtos", productDtos);
        return "admin/product/list";
    }

    // 顯示新增表單
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "admin/product/create";
    }

    // 處理新增
    @PostMapping
    public String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto,
                                BindingResult bindingResult,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                HttpServletRequest request,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        try {
            // 圖片上傳檢查
            if (!imageFile.isEmpty()) {
                String uploadDir = request.getServletContext().getRealPath("/static/images/");
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String fileName = imageFile.getOriginalFilename();
                File saveFile = new File(uploadDir, fileName);

                System.out.println("圖片儲存路徑：" + saveFile.getAbsolutePath() + "。");
                imageFile.transferTo(saveFile);
                System.out.println("圖片儲存成功。");

                productDto.setImagePath("static/images/" + fileName);
            } else {
                System.out.println("圖片未選擇或為空。");
            }

            productService.createProduct(productDto);
            return "redirect:/admin/products";

        } catch (IOException e) {
            model.addAttribute("error" ,"找不到圖片:"+ e.getMessage() + "。");
            return "admin/product/create";
        }
    }

    // 顯示編輯表單
    @GetMapping("/{productId}")
    public String showEditForm(@PathVariable Integer productId, Model model) {
        ProductDto productDto = productService.getProductById(productId);
        model.addAttribute("productDto", productDto);
        return "admin/product/edit";
    }

    // 處理更新
    @PutMapping("/{productId}")
    public String updateProduct(@PathVariable Integer productId,
                                @Valid @ModelAttribute("productDto") ProductDto productDto,
                                BindingResult bindingResult,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                HttpServletRequest request,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/product/edit";
        }

        try {
            if (!imageFile.isEmpty()) {
                String uploadDir = request.getServletContext().getRealPath("/static/images/");
                String fileName = imageFile.getOriginalFilename();
                File saveFile = new File(uploadDir, fileName);
                imageFile.transferTo(saveFile);
                productDto.setImagePath("static/images/" + fileName);
            }

            productService.updateProduct(productId, productDto);
            return "redirect:/admin/products";

        } catch (IOException e) {
            throw new ImageUploadException("圖片上傳失敗。", e);
        }
    }

    // 處理刪除
    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return "redirect:/admin/products";
    }
}