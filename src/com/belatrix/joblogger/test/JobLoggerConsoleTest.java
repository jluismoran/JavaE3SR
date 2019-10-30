package com.belatrix.joblogger.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JobLoggerConsoleTest {
	
	private static JobLogger jobLoggerConsole = new JobLogger(false, true, false, null);

	@Test
	public void ejecutarJobLogger() {
		// TODO Auto-generated method stub
		try {
			jobLoggerConsole.logMessage("Mensaje de prueba", JobLogger.TYPE_MESSAGE);
			jobLoggerConsole.logMessage("Mensaje de error", JobLogger.TYPE_ERROR);
			jobLoggerConsole.logMessage("Mensaje de advertencia", JobLogger.TYPE_WARNING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
