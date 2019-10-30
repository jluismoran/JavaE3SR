package com.belatrix.joblogger.prueba;

import java.util.HashMap;
import java.util.Map;

import com.belatrix.joblogger.test.JobLogger;

public class PruebaJobLogger {

	static Map<String, String> param = new HashMap<String, String>() {
		{
			put("logFileFolder", System.getProperty("user.home"));
		}
	};
	private static JobLogger jobLoggerC = new JobLogger(true, false, false, param);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			jobLoggerC.logMessage("Mensaje de prueba", JobLogger.TYPE_MESSAGE);
			jobLoggerC.logMessage("Mensaje de error", JobLogger.TYPE_ERROR);
			jobLoggerC.logMessage("Mensaje de advertencia", JobLogger.TYPE_WARNING);
			jobLoggerC.closeFileHandler();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
