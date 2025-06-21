package com.example.oldstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.oldstore.exception.EmailSendException;
import com.example.oldstore.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendVerificationEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to); // 收件人
		message.setSubject(subject); // 標題
		message.setText(body); // 內容
		message.setFrom("bhonb37666@gmail.com"); // 寄件者
		
		mailSender.send(message);
	}
	
	@Override
	public void sendMail(String to, String subject, String content) {
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, false); // 後者 false 表示非 HTML
			mailSender.send(message);
		}catch(MessagingException e) {
			throw new EmailSendException("寄送 Email 驗證信時發生錯誤。");
		}
	}
}
