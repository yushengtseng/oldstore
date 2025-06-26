package com.example.oldstore.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class EcpayUtil {

    // 測試用金鑰（正式環境請改成你自己的）
    private static final String HASH_KEY = "5294y06JbISpM5x9";
    private static final String HASH_IV = "v77hoKGq4kWxNNIS";

    public static String generateCheckMacValue(Map<String, String> params) {
        try {
            // Step 1：按參數名稱升冪排序
            SortedMap<String, String> sortedParams = new TreeMap<>(params);

            // Step 2：組合成 key=value&key=value...
            StringBuilder paramStr = new StringBuilder();
            for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
                if (entry.getValue() != null && entry.getValue().length() > 0) {
                    paramStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }
            // 移除最後的 &
            if (paramStr.length() > 0) {
                paramStr.setLength(paramStr.length() - 1);
            }

            // Step 3：加上 HashKey 與 HashIV
            String raw = "HashKey=" + HASH_KEY + "&" + paramStr + "&HashIV=" + HASH_IV;

            // Step 4：URL Encode + 特殊轉換
            String encoded = urlEncode(raw).toLowerCase();
            encoded = replaceSpecialChars(encoded);

            // Step 5：SHA256 並轉大寫
            return sha256(encoded).toUpperCase();

        } catch (Exception e) {
            throw new RuntimeException("產生 CheckMacValue 時發生錯誤", e);
        }
    }

    private static String urlEncode(String raw) throws UnsupportedEncodingException {
        return URLEncoder.encode(raw, "UTF-8");
    }

    // 歐付寶特殊字元處理（根據官方替換對照表）
    private static String replaceSpecialChars(String str) {
        return str.replaceAll("%2d", "-")
                  .replaceAll("%5f", "_")
                  .replaceAll("%2e", ".")
                  .replaceAll("%21", "!")
                  .replaceAll("%2a", "*")
                  .replaceAll("%28", "(")
                  .replaceAll("%29", ")")
                  .replaceAll("%20", "+")
                  .replaceAll("%2b", "%2B"); // 修正 + 號
    }
    
    private static String sha256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes("UTF-8"));

        // 轉十六進位字串
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
