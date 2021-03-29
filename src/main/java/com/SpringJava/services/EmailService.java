package com.SpringJava.services;

import org.springframework.mail.SimpleMailMessage;

import com.SpringJava.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
