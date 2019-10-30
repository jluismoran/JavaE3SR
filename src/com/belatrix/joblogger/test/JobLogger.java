package com.belatrix.joblogger.test;

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
import java.util.logging.SimpleFormatter;

public class JobLogger {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logToDatabase;
	private boolean initialized;
	private static Map dbParams;
	private static Logger logger;
	
	public static final int TYPE_MESSAGE = 1;
	public static final int TYPE_ERROR = 2;
	public static final int TYPE_WARNING = 3;
	
	public FileHandler fh;
	
	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam, Map dbParamsMap) {
		logger = Logger.getLogger("MyLog");
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}
	
	public void logMessage(String messageText, int type) throws Exception {
		messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Configuración inválida");
		}
		
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
		
		// Guarda el log en un archivo
		if(logToFile) {
			if(fh==null) {
				fh = new FileHandler(dbParams.get("logFileFolder") + File.separator + "logFile.txt", true); // append=true agrega y no sobreescribe al log
				fh.setFormatter(new SimpleFormatter()); // formato simple del log
				fh.setLevel(Level.ALL);
			}
			
			logger.setUseParentHandlers(false); // Evita que se haga print en consola
			logger.addHandler(fh);
			logger.log(level, messageText);
			logger.removeHandler(fh);
		}
		
		// Muestra el log en consola
		if(logToConsole) {
			ConsoleHandler ch = new ConsoleHandler();
			
			logger.setUseParentHandlers(false); // Evita que se haga print 2 veces en consola
			logger.addHandler(ch);
			logger.log(level, messageText);
			logger.removeHandler(ch);
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
	
	public void closeFileHandler() {
		if(fh!=null) {
			fh.close();
		}
	}
}