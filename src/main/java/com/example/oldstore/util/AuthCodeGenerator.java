package com.example.oldstore.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthCodeGenerator {
	
	public static void generate(HttpServletResponse response, HttpSession session) throws IOException {
		int width = 113;
		int height = 36;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		
		// 背景色
		g.setColor(new Color(240, 220, 200));
		g.fillRoundRect(0, 0, width, height, 20, 20);
		
		// 干擾線
		Random random = new Random();
		
		g.setColor(new Color(160, 130, 110)); // 干擾線顏色
		for(int i = 0; i < 15; i++) { // 繪製 5 條干擾線
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			g.drawLine(x1, y1, x2, y2);
		}
		
		// 字形設定
		g.setFont(new Font("Arial", Font.BOLD, 26));
		g.setColor(Color.BLACK);
		
		// 隨機產生 4 碼的數字驗證碼
		StringBuilder code = new StringBuilder();
		
		for(int i=0; i<4; i=i+1) {
			int digit = random.nextInt(10); // 0~9
			code.append(digit);
			g.drawString(String.valueOf(digit), 20 + i * 20,28);
		}
		
		// 將驗證碼存入 Session
		session.setAttribute("authCode", code.toString());
		
		// 設定為圖片輸出
		response.setContentType("image/png");
		ImageIO.write(image, "PNG", response.getOutputStream());
	}
}
