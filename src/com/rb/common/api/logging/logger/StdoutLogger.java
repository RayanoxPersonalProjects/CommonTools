package com.rb.common.api.logging.logger;

import java.io.IOException;
import org.springframework.stereotype.Component;
import com.rb.common.api.logging.LogLevel;

@Component
public class StdoutLogger extends Logger {
	
	public StdoutLogger() throws IOException {
	}

	@Override
	protected void logSpecific(String message, LogLevel logLevel) throws Exception {
		System.out.println(message + "\r\n");
	}
	
}
