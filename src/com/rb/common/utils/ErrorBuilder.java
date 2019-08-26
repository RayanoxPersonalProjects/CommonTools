package com.rb.common.utils;

import com.rb.common.utils.Converter;

public class ErrorBuilder {
		
	public static String formatStringException(Exception e) {
    	String stringStackTrace;
    	if(e.getCause() != null)
    		stringStackTrace = Converter.convertStacktraceToString(e.getCause().getStackTrace());
    	else
    		stringStackTrace = Converter.convertStacktraceToString(e.getStackTrace());
    	
    	return e.getMessage() + "       -----||-----     " + e.getClass().getName() + " ----- " + stringStackTrace;
    }
}
