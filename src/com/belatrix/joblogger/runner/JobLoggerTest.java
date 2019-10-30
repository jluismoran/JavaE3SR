package com.belatrix.joblogger.runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.belatrix.joblogger.test.JobLoggerConsoleTest;
import com.belatrix.joblogger.test.JobLoggerFileTest;
import com.belatrix.joblogger.test.JobLoggerInvalidTest;

import org.junit.runners.Suite;

@SuiteClasses({JobLoggerInvalidTest.class, JobLoggerConsoleTest.class, JobLoggerFileTest.class})
@RunWith(Suite.class)
public class JobLoggerTest {
	
}
