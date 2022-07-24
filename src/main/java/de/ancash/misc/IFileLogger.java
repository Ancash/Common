package de.ancash.misc;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import de.ancash.libs.org.apache.commons.lang3.exception.ExceptionUtils;

public class IFileLogger {

	private final Logger logger;
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	private static final DateTimeFormatter DATE_FORMATTER_FILE = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
	
	public IFileLogger(String logger, String dir, boolean useParentHandlers) throws SecurityException, IOException  {
		File fileDir = new File(dir);
		if(!fileDir.exists())
			fileDir.mkdirs();
		if(fileDir.isFile())
			throw new IllegalArgumentException(dir + " is a file not a directory!");
		this.logger = Logger.getLogger(logger);
		this.logger.setLevel(Level.ALL);
		FileHandler fh = new FileHandler(dir+ "/" + LocalDateTime.now().format(DATE_FORMATTER_FILE) + ".log");
		fh.setFormatter(new Formatter() {
			
			@Override
			public String format(LogRecord record) {
				Level level = record.getLevel();
				Throwable throwable = record.getThrown();
				String message = record.getMessage();
				StringBuilder builder = new StringBuilder();
				builder.append(LocalDateTime.now().format(DATE_FORMATTER) + " - ");
				builder.append(level.getLocalizedName());
				builder.append(" - ");
				builder.append(message);
				if(throwable != null) 
					builder.append("\n" + ExceptionUtils.getStackTrace(throwable));
				builder.append("\n");
				return builder.toString();
			}
		});
		this.logger.addHandler(fh);
		this.logger.setUseParentHandlers(useParentHandlers);
	}
	
	public Logger getLogger() {
		return logger;
	}
}