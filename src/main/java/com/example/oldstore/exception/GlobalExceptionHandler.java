package com.example.oldstore.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error"; // 指向 error.jsp 頁面顯示錯誤訊息
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherExceptions(Exception ex, Model model) {
        model.addAttribute("error", "發生錯誤：" + ex.getMessage());
        return "error";
    }
}