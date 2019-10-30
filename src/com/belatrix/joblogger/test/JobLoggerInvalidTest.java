package com.belatrix.joblogger.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JobLoggerInvalidTest {

	static Map<String, String> param = new HashMap<String, String>() {
		{
			put("logFileFolder", System.getProperty("user.home"));
		}
	};
	private static JobLogger jobLoggerFile = new JobLogger(false, false, false, param);

	@Test
	public void ejecutarJobLogger() {
		try {
			jobLoggerFile.logMessage("La inicialización no será válida", JobLogger.TYPE_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
