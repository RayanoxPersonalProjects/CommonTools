package com.rb.common.api.logging;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rb.common.api.logging.logger.FileLogger;
import com.rb.common.api.logging.logger.MailLogger;
import com.rb.common.api.logging.mail.MailInfos;
import com.rb.common.exceptions.BadFormatPropertyException;
import com.rb.common.exceptions.NotImplementedException;

@Component
public class LogManager {
	
	@Autowired
	FileLogger fileLogger;
	
	@Autowired
	MailLogger mailLogger;
	
	public void registerMailCredentials(String usernameFrom, String passwordFrom, ArrayList<String> recipients, String programName) throws IOException, NotImplementedException, BadFormatPropertyException {
		this.mailLogger.registerCredentialsAndInitInfos(usernameFrom, passwordFrom, recipients, programName);
	}
	
	public MailInfos loadMailInfos() throws NotImplementedException, BadFormatPropertyException, IOException {
		return this.mailLogger.loadMailInfos();
	}
	
	public void log(String message, LogLevel logLevel, LoggingAction... loggingWay) {
		ArrayList<LoggingAction> loggingWayDone = new ArrayList<>();
		for (LoggingAction loggingAction : loggingWay) {
			if(loggingWayDone.contains(loggingAction))
				continue;
			
			if(loggingAction.equals(LoggingAction.Email)) {
				try {
					if(!this.mailLogger.isRegistrationDone())
						try {
							throw new Exception("To use the email logger, you must before register your credentials using the corresponding method.");
						} catch (Exception e) {
							e.printStackTrace();
						}
					else
						this.mailLogger.log(message, logLevel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(loggingAction.equals(LoggingAction.File))
				this.fileLogger.log(message, logLevel);
			
			loggingWayDone.add(loggingAction);
		}
	}
}
