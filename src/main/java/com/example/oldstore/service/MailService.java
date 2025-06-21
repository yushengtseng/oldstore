package com.example.oldstore.service;

public interface MailService {
	
	void sendVerificationEmail(String to , String subject, String body);
	void sendMail(String to, String subject, String content);

}
