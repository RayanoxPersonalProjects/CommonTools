package com.rb.common.api.logging.logger;

import java.time.LocalDateTime;

import com.rb.common.api.logging.LogLevel;
import com.rb.common.exceptions.NotImplementedException;
import com.rb.common.utils.ErrorBuilder;

public abstract class Logger {
	
	public void logError(String message, Exception e) {
		String messageError = "Une erreur a etee detectee dans l'execution du programme.\n\n\tMessage d'erreur :\n" + message;
		messageError += "\n\n +    |||    Stacktrace ===>   " + ErrorBuilder.formatStringException(e);
		
		this.log(messageError, LogLevel.ERROR);
	}
	
	public void log(String message, LogLevel logLevel) {
		LocalDateTime date = LocalDateTime.now();
		
		String logMessage;
		switch (logLevel) {
			case INFO:
				logMessage = String.format("[%s] - %s", date.toString(), getLogInfoLabelMessage(message));
				break;
			case WARN:
				logMessage = String.format("[%s] - %s", date.toString(), getLogWarnLabelMessage(message));
				break;
			case ERROR:
				logMessage = String.format("[%s] - %s", date.toString(), getLogErrorLabelMessage(message));
				break;
			default:
				try {
					throw new NotImplementedException(String.format("The logLevel %s has not been implemented !", logLevel));
				} catch (NotImplementedException e) {
					e.printStackTrace();
					return;
				}
		}
		
		try {
			this.logSpecific(logMessage, logLevel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLogInfoLabelMessage(String message) {
		return "[INFO] | " + message;
	}
	
	private String getLogWarnLabelMessage(String message) {
		return "[WARN] | " + message;
	}
	
	private String getLogErrorLabelMessage(String message) {
		return "[ERROR] | " + message;
	}
	
	protected abstract void logSpecific(String message, LogLevel logLevel) throws Exception;
}
