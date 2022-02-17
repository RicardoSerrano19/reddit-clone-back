package com.serrano.app.forum.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {
	private final TemplateEngine template;
	
	public String buildVerificationToken(String username, String link) {
		Context context = new Context(); 
		context.setVariable("username", username);
		context.setVariable("link", link);
		return template.process("MailTemplate", context);
	}
}
