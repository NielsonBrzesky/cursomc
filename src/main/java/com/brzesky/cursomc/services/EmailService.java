package com.brzesky.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.brzesky.cursomc.domain.Pedido;

public interface EmailService 
{
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
