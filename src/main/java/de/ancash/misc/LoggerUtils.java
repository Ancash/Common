package de.ancash.misc;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtils {

	public static void setPreSetSysOut() {
		setOut("[" + IFormatter.FULL_DATE_TIME + "] "
				+ "[" + IFormatter.THREAD_NAME + "/"
				+ IFormatter.COLOR + IFormatter.LEVEL + IFormatter.RESET + "] "
				+ IFormatter.COLOR + IFormatter.MESSAGE + IFormatter.RESET);
	}
	
	public static void setPreSetSysErr() {
		setErr("[" + IFormatter.FULL_DATE_TIME + "] "
				+ "[" + IFormatter.THREAD_NAME + "/"
				+ IFormatter.COLOR + IFormatter.LEVEL + IFormatter.RESET + "] "
				+ IFormatter.COLOR + IFormatter.MESSAGE + IFormatter.RESET);
	}
	
	public static void setOut(String format) {
		setOut(format, true);
	}
	
	public static void setOut(String format, boolean formatAfterLineSeperator) {
		setOut(format, formatAfterLineSeperator, Level.INFO);
	}
	
	public static void setOut(String format, Level l) {
		setOut(format, true, l);
	}
	
	public static void setOut(String format, boolean formatAfterLineSeperator, Level l) {
		System.setOut(new IPrintStream(System.out, l, format, formatAfterLineSeperator));
	}
	
	public static void setErr(String format) {
		setErr(format, true);
	}
	
	public static void setErr(String format, boolean formatAfterLineSeperator) {
		setErr(format, formatAfterLineSeperator, Level.SEVERE);
	}
	
	public static void setErr(String format, Level l) {
		setErr(format, true, l);
	}
	
	public static void setErr(String format, boolean formatAfterLineSeperator, Level l) {
		System.setErr(new IPrintStream(System.err, l, format, formatAfterLineSeperator));
	}
	
	public static void setUpGlobalLogger(String format, boolean formatAfterLineSeperator) {
		new IConsoleLogger(format, formatAfterLineSeperator).removeConsoleHandler();	
	}
	
	public static void setLogAllLevel(String format, boolean formatAfterLineSeperator) {
		Logger.getGlobal().setLevel(Level.ALL);
		Handler g = new ConsoleHandler();
		g.setFormatter(new IFormatter(format, formatAfterLineSeperator)
				.setFromLevel(Level.ALL)
				.setToLevel(Level.CONFIG));
		g.setLevel(Level.ALL);
		Logger.getGlobal().addHandler(g);
	}
}