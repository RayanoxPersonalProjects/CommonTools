package com.rb.common.api.logging.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.rb.common.api.logging.LogLevel;

@Component
public class FileLogger extends Logger {

	private final String filePath = "Logs.txt";
	public BufferedWriter writer;
	
	public FileLogger() throws IOException {
		this.writer = new BufferedWriter(new FileWriter(filePath, true));
	}

	@Override
	protected void logSpecific(String message, LogLevel logLevel) throws Exception {
		this.writer.append(message + "\r\n");
		this.writer.flush();
	}
	
}
