package com.demo.myshop.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.myshop.dto.Customer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailSendingHelper {

	@Autowired
	JavaMailSender mailSender;

	@Value("${spring.mail.username:}")
	private String mailFromAddress;

	public void sendOtp(Customer customer) {
		sendOtpMessage(customer);
	}

	public void resendOtp(Customer customer) {
		sendOtpMessage(customer);
	}

	private void sendOtpMessage(Customer customer) {
		if (!StringUtils.hasText(mailFromAddress)) {
			return;
		}
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(mailFromAddress, "MyShop");
			helper.setTo(customer.getEmail());
			helper.setSubject("Verification OTP");
			String salutation = "male".equalsIgnoreCase(customer.getGender()) ? "Mr. " : "Ms. ";
			String body = "<html><body><h1>Hello " + salutation + customer.getName() + ",</h1><h2>Your OTP is: "
					+ customer.getOtp() + "</h2><p>Thank you</p></body></html>";
			helper.setText(body, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			throw new IllegalStateException("Failed to send OTP email", e);
		} catch (Exception e) {
			throw new IllegalStateException("Failed to prepare OTP email", e);
		}
	}

}
