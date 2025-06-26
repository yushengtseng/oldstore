package com.example.oldstore.controller;

import com.example.oldstore.model.dto.OrderDto;
import com.example.oldstore.model.dto.OrderItemDto;
import com.example.oldstore.service.OrderService;
import com.example.oldstore.util.EcpayUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;

    @Value("${site.base.url}")
    private String siteBaseUrl;

    // 步驟一：使用訂單 ID 顯示歐付寶付款表單
    @GetMapping("/pay/{orderId}")
    public String pay(@PathVariable Integer orderId, Model model) {
        OrderDto order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("查無此訂單。");
        }
        
        String merchantTradeNo = "DX" + orderId + System.currentTimeMillis();
        
        orderService.saveMerchantTradeNo(orderId, merchantTradeNo);

        // 組合歐付寶所需欄位
        Map<String, String> params = new HashMap<>();
        params.put("MerchantID", "2000132");
        
        params.put("MerchantTradeNo", merchantTradeNo);
        params.put("MerchantTradeDate", order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        params.put("PaymentType", "aio");
        params.put("TotalAmount", order.getTotalPrice().intValue() + ""); // 必須為整數字串
        params.put("TradeDesc", "建立信用卡訂單");

        // 將商品名稱組合成字串（以 # 分行）
        StringBuilder itemNameBuilder = new StringBuilder();
        for (OrderItemDto item : order.getItems()) {
            itemNameBuilder
                .append(item.getProductName())
                .append("#");
        }
        // 移除最後的 #
        if (itemNameBuilder.length() > 0) {
            itemNameBuilder.setLength(itemNameBuilder.length() - 1);
        }
        params.put("ItemName", itemNameBuilder.toString());

        // 回傳網址（伺服器端）
        params.put("ReturnURL", siteBaseUrl + "/payment/result");
        // Client 返回網址（使用者返回頁）
        params.put("ClientBackURL", siteBaseUrl + "/order/history");

        // 其他必要參數
        params.put("ChoosePayment", "Credit");
        params.put("EncryptType", "1");

        // 加入 CheckMacValue
        String checkMacValue = EcpayUtil.generateCheckMacValue(params);
        params.put("CheckMacValue", checkMacValue);

        // 傳給 JSP 畫面
        model.addAllAttributes(params);
        return "payment/pay";
    }
    
    @PostMapping("/result")
    public String handleOpayResult(HttpServletRequest request, Model model) {
        Map<String, String> resultMap = new HashMap<>();

        // 將所有參數存進 map
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            resultMap.put(name, request.getParameter(name));
        }

        // 取出 MerchantTradeNo、RtnCode
        String tradeNo = resultMap.get("MerchantTradeNo");
        String rtnCode = resultMap.get("RtnCode"); // 1 表示付款成功

        boolean success = "1".equals(rtnCode);
        if (success && tradeNo != null) {
            try {
                orderService.markOrderAsPaid(tradeNo); // 用商店交易編號更新為已付款
            } catch (Exception e) {
                model.addAttribute("error", "更新訂單狀態失敗：" + e.getMessage());
                return "payment/payment_result";
            }
        }

        model.addAttribute("result", resultMap);
        model.addAttribute("message", success ? "付款成功！" : "付款失敗或尚未完成");
        return "payment/payment_result";
    }
}
