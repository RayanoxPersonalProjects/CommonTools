package com.rb.common.api.logging.mail;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MailInfos {
	
	public MailInfos(String username, String password, ArrayList<String> recipients) {
		this.username = username;
		this.password = password;
		this.recipients = recipients.stream().collect(Collectors.joining(","));
	}
	
	public MailInfos(String username, String password, String recipients) {
		this.username = username;
		this.password = password;
		this.recipients = recipients;
	}
	
	private String username;
	private String password;
	private String recipients; // destinataires (separes pas un point virgule)
	
	public String getPassword() {
		return password;
	}
	public String getRecipients() {
		return recipients;
	}
	public String getUsername() {
		return username;
	}
}
