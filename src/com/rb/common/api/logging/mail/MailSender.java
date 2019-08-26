package com.rb.common.api.logging.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.rb.common.api.logging.LogLevel;
import com.rb.common.exceptions.NotImplementedException;

@Component
public class MailSender {

	private Session session;
	private MailInfos mailInfos;
	
	
	public void registerMailsInfos(String username, String password, String recipients) {
		this.mailInfos = new MailInfos(username, password, recipients);
	}
	
	public boolean isMailInfosSet() {
		return this.mailInfos != null;
	}
	
	public void SendNotificationMail(String message, String programName, String subject, LogLevel logLevel) {
		try {
			SendMail("", getSubject(logLevel, programName, subject), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getSubject(LogLevel logLevel, String programName, String subject) throws NotImplementedException {
		String subjectResult;
		switch (logLevel) {
			case INFO:
				subjectResult = String.format("[%s] - [INFO] - %s", programName, subject);
				break;
			case WARN:
				subjectResult = String.format("[%s] - [WARN] - %s", programName, subject);
				break;
			case ERROR:
				subjectResult = String.format("[%s] - [ERROR] - An error occured in the program", programName);
				break;
			default:
				throw new NotImplementedException("Mail subject building not implemented for log level " + logLevel);
		}
		return subjectResult;
	}
	
	
	
	private void SendMail(String mailDst, String object, String bodyContent) throws Exception {

		if(this.mailInfos == null)
			throw new Exception("The mail infos must be set to use the mail sending");
		
		final String username = this.mailInfos.getUsername();
		final String password = this.mailInfos.getPassword();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		if(this.session == null)
			this.session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  }); 

		try {

			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress("RAYANOX_MAAAN@gmail.com"));
			
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.mailInfos.getRecipients()));
			message.setSubject(object);
			message.setText(bodyContent);

			Transport.send(message);

			System.out.println("MailSent");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}