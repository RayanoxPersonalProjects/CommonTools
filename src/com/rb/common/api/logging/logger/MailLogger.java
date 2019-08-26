package com.rb.common.api.logging.logger;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rb.common.api.datafilestorage.DataStorage;
import com.rb.common.api.logging.LogLevel;
import com.rb.common.api.logging.mail.MailInfos;
import com.rb.common.api.logging.mail.MailSender;
import com.rb.common.exceptions.BadFormatPropertyException;
import com.rb.common.exceptions.NotImplementedException;

@Component
public class MailLogger extends Logger {

	@Autowired
	private DataStorage dataStorage;
	
	@Autowired
	private MailSender mailSender;
	
	private final static String KEY_USERNAME = "mailSender_username";
	private final static String KEY_PASSWORD = "mailSender_password";
	private final static String KEY_RECIPIENTS = "mailSender_recipients";
	private final static String KEY_PROGRAMME_NAME = "mailSender_programName";
	
	@Override
	protected void logSpecific(String message, LogLevel logLevel) throws NotImplementedException, BadFormatPropertyException, IOException {
		String programName = (String) this.dataStorage.getData(KEY_PROGRAMME_NAME, String.class);
		
		if(!this.mailSender.isMailInfosSet()) {
			MailInfos mailInfos = loadMailInfos();
			this.mailSender.registerMailsInfos(mailInfos.getUsername(), mailInfos.getPassword(), mailInfos.getRecipients());
		}
		
		this.mailSender.SendNotificationMail(message, programName, "New log", logLevel);
		
	}
	
	public void registerCredentialsAndInitInfos(String usernameFrom, String passwordFrom, ArrayList<String> recipients, String programName) throws IOException, NotImplementedException, BadFormatPropertyException {
		this.dataStorage.setData(KEY_USERNAME, usernameFrom);
		this.dataStorage.setData(KEY_PASSWORD, passwordFrom);
		this.dataStorage.setData(KEY_RECIPIENTS, recipients);
		this.dataStorage.setData(KEY_PROGRAMME_NAME, programName);
	}
	
	public boolean isRegistrationDone() throws NotImplementedException, BadFormatPropertyException, IOException {
		return this.mailSender.isMailInfosSet() || loadMailInfos() != null;
	}
	
	public MailInfos loadMailInfos() throws NotImplementedException, BadFormatPropertyException, IOException {
		String username = (String) this.dataStorage.getData(KEY_USERNAME, String.class);
		String password = (String) this.dataStorage.getData(KEY_PASSWORD, String.class);
		ArrayList<String> destinatairesMails = (ArrayList<String>) this.dataStorage.getData(KEY_RECIPIENTS, ArrayList.class);
		
		if(username != null && password != null && destinatairesMails != null)
			return new MailInfos(username, password, destinatairesMails);
		else 
			return null;
	}

}
