package de.ancash.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class IConsoleLogger extends Formatter{

	private final ConsoleHandler handler = new ConsoleHandler();
	private final Map<Level, String> levelColors = new HashMap<>();
	private final IFormatter formatter;
	
	public IConsoleLogger(String format) {
		this(Logger.getLogger(""), new IFormatter(format, true));
	}
	public IConsoleLogger(String format, boolean b) {
		this(Logger.getLogger(""), new IFormatter(format, b));
	}
	
	public IConsoleLogger(Logger logger, IFormatter formatter) {
		this.formatter = formatter;
		handler.setFormatter(this);
		logger.addHandler(handler);
	}
	
	public void setLevelColor(Level l, String c) {
		levelColors.put(l, c == null ? "" : c);
	}
	
	public void removeConsoleHandler() {
		Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler)
            rootLogger.removeHandler(handlers[0]);
	}

	@Override
	public String format(LogRecord record) {
		return formatter.format(record);
	}
}