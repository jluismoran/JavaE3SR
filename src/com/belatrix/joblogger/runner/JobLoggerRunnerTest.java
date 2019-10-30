package com.belatrix.joblogger.runner;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JobLoggerRunnerTest {

	public Logger logger = Logger.getGlobal();
	
	@Test
	public void runAllTests() {
		Result result = JUnitCore.runClasses(JobLoggerTest.class);
		for(Failure failure : result.getFailures()) {
			logger.severe(failure.toString());
		}
		logger.log(Level.INFO, "Test Exitoso: "+result.wasSuccessful());
	}
}
