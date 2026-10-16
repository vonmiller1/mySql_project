package com.demo.myshop.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionMessageRemover {

	public void removeMessage() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return;
		}
		HttpSession session = attributes.getRequest().getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute("successMessage");
		session.removeAttribute("failMessage");
	}

}
