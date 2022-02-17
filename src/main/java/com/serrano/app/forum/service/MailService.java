package com.serrano.app.forum.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
	private final MailContentBuilder mailBuilder;
	private final JavaMailSender sender;
    private final String BASE_URL = "http://localhost:8080/api/v1";
    
	@Autowired
	public MailService(JavaMailSender sender, MailContentBuilder mailBuilder) {
		this.sender = sender;
		this.mailBuilder = mailBuilder;
	}
	
	@Async
    public void send(String name, String token) {
        try{
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(mailBuilder.buildVerificationToken(name, BASE_URL + "/verification/" + token), true);
            helper.setTo("javierarriaga1906@gmail.com");
            helper.setSubject("Confirm your email");
            helper.setFrom("javierarriaga1906@gmail.com");
            sender.send(mimeMessage);
            log.info("Mail sended");
        }catch(MessagingException ex){
        	log.error(ex.getMessage());
        }
    }
	

}
