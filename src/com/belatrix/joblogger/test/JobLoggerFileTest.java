package com.belatrix.joblogger.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JobLoggerFileTest {

	static Map<String, String> param = new HashMap<String, String>() {
		{
			put("logFileFolder", System.getProperty("user.home"));
		}
	};
	private static JobLogger jobLoggerFile = new JobLogger(true, false, false, param);

	@Test
	public void ejecutarJobLogger() {
		try {
			jobLoggerFile.logMessage("Mensaje de prueba", JobLogger.TYPE_MESSAGE);
			jobLoggerFile.logMessage("Mensaje de error", JobLogger.TYPE_ERROR);
			jobLoggerFile.logMessage("Mensaje de advertencia", JobLogger.TYPE_WARNING);
			jobLoggerFile.closeFileHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
