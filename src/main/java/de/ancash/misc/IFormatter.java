package de.ancash.misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import de.ancash.misc.IPrintStream.ConsoleColor;

public class IFormatter extends Formatter{

	public static final DateTimeFormatter PART_DATE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
	public static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static final String THREAD_NAME = "$t$";
	public static final String LEVEL = "$l$";
	public static final String FULL_DATE_TIME = "$dt$";
	public static final String PART_DATE_TIME = "$d$";
	public static final String MESSAGE = "$s$";
	public static final String COLOR = "$c$";
	public static final String RESET = ConsoleColor.RESET;
	
	private final String format;
	private final Map<Level, String> levelColors = new HashMap<>();
	private final boolean formatAfterLineSeperator;
	private Level fromLevel = Level.INFO;
	private Level toLevel = Level.SEVERE;
	
	public IFormatter(String format) {
		this(format, true);
	}
	
	public IFormatter(String format, boolean formatAfterLineSeperator) {
		this.format = format;
		this.formatAfterLineSeperator = formatAfterLineSeperator;
		levelColors.put(Level.ALL, "");
		levelColors.put(Level.CONFIG, "");
		levelColors.put(Level.FINE, "");
		levelColors.put(Level.FINER, "");
		levelColors.put(Level.FINEST, "");
		levelColors.put(Level.INFO, "");
		levelColors.put(Level.OFF, "");
		levelColors.put(Level.SEVERE, ConsoleColor.RED_BOLD_BRIGHT);
		levelColors.put(Level.WARNING, ConsoleColor.YELLOW_BOLD_BRIGHT);
	}
	
	public void setLevelColor(Level l, String c) {
		levelColors.put(l, c == null ? "" : c);
	}

	@Override
	public String format(LogRecord record) {
		if(record.getLevel().intValue() < fromLevel.intValue() || record.getLevel().intValue() > toLevel.intValue())
			return "";
		if(!formatAfterLineSeperator)
			return format(record.getMessage(), record.getLevel(), true);
		StringBuilder builder = new StringBuilder();
		Arrays.asList(record.getMessage().split(System.lineSeparator())).stream().map(s -> format(s, record.getLevel(), true)).forEach(builder::append);
		return builder.toString();
	}
	
	public String format(String str, Level l, boolean appendLineSeperator) {
		if(str == null)
			str = "null";
		return format.replace(LEVEL, l.toString())
				.replace(FULL_DATE_TIME, LocalDateTime.now().format(FULL_DATE_FORMATTER))
				.replace(PART_DATE_TIME, LocalDateTime.now().format(PART_DATE_FORMATTER))
				.replace(THREAD_NAME, Thread.currentThread().getName())
				.replace(COLOR, levelColors.get(l))
				.replace(MESSAGE, str)
				+ (appendLineSeperator ? System.lineSeparator() : "");
	}

	public Level getFromLevel() {
		return fromLevel;
	}

	public IFormatter setFromLevel(Level fromLevel) {
		this.fromLevel = fromLevel;
		return this;
	}

	public Level getToLevel() {
		return toLevel;
	}

	public IFormatter setToLevel(Level toLevel) {
		this.toLevel = toLevel;
		return this;
	}
}