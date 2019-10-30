package com.belatrix.joblogger.question;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLoggerB {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	private boolean initialized;
	private static Map dbParams;
	private static Logger logger;
	
	public static final int TYPE_MESSAGE = 1;
	public static final int TYPE_ERROR = 2;
	public static final int TYPE_WARNING = 3;

	// Por default logMessageParam sería true
	/*
	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
	*/
	public JobLoggerB(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam, Map dbParamsMap) {
		logger = Logger.getLogger("MyLog");
		//logError = logErrorParam;
		//logMessage = logMessageParam;
		//logWarning = logWarningParam;
		
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	//public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
	
	// Por default message sería true, es decir, tipo 1
	public void logMessage(String messageText, int type) throws Exception {
		messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		/*
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}
		*/

		// Sólo si se guarda en base de datos
		/*
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		// Falta el Service Name en la cadena de conexión.
		connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
				+ ":" + dbParams.get("portNumber") + "/", connectionProps);*/
		
		// Se mueve la inicialización de t líneas más abajo y se comentan estas validaciones para incluirlas al validar el tipo de mensaje
		/*
		int t = 0;
		if (message && logMessage) {
			t = 1;
		}

		if (error && logError) {
			t = 2;
		}

		if (warning && logWarning) {
			t = 3;
		}
		*/

		// Sólo si se guarda en base de datos
		// Statement stmt = connection.createStatement();

		// Se comenta porque la variable l es inicializada y asignada pero no es utilizada
		//String l = null;
		
		// No es necesario crear una variable de tipo File
		/*
		File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		*/
		
		// Estas variables se inicializaran según el tipo de log
		/*
		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
		ConsoleHandler ch = new ConsoleHandler();
		*/
		
		/*
		if (message && logMessage) {
			l = l + "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}
		
		if (error && logError) {
			l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}

		if (warning && logWarning) {
			l = l + "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
		}
		*/
		
		Level level = null;
		int t = 0;
		// Valida el Tipo de Log y asigna el level según el tipo de log
		if (type==TYPE_MESSAGE) {
			t = 1;
			level = Level.INFO;
		}else if (type==TYPE_ERROR) {
			t = 2;
			level = Level.SEVERE;
		}else if (type==TYPE_WARNING) {
			t = 3;
			level = Level.WARNING;
		}
		
		/*
		if(logToFile) {
			logger.addHandler(fh);
			logger.log(Level.INFO, messageText);
		}
		
		if(logToConsole) {
			logger.addHandler(ch);
			logger.log(Level.INFO, messageText);
		}
		*/
		
		// Guarda el log en un archivo
		if(logToFile) {
			FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
			
			logger.addHandler(fh);
			logger.log(level, messageText);
		}
		
		// Muestra el log en consola
		if(logToConsole) {
			ConsoleHandler ch = new ConsoleHandler();
			
			logger.addHandler(ch);
			logger.log(level, messageText);
		}
		
		// Guarda el log la base de datos
		if(logToDatabase) {
			Connection connection = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", dbParams.get("userName"));
			connectionProps.put("password", dbParams.get("password"));
			
			// Se agrega el Service Name a la cadena de conexión
			connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
			+ ":" + dbParams.get("portNumber") + "/" + dbParams.get("serviceName"), connectionProps);

			Statement stmt = connection.createStatement();
			
			// Sintaxis SQL inválida
			//stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
			
			// Inserta en la tabla Log_Values, el mensaje (cadena) y el tipo (entero). Se espera que la tabla tenga una llave primaria autogenerada.
			stmt.executeUpdate("insert into Log_Values(message, message_type) values('" + messageText + "', " + t + ")");
		}
	}
}