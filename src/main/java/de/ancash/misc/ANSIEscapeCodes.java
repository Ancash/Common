package de.ancash.misc;

public class ANSIEscapeCodes {

	public static final String ERASE_IN_LINE = "\033[K";
	public static final String ERASE_CURSOR_TO_END_OF_LINE = "\033[0K";
	public static final String ERASE_START_OF_LINE_TO_CURSOR = "\033[1K";
	public static final String ERASE_LINE = "\033[2K";
	
	public static final String ERASE_IN_DISPLAY = "\033[J";
	public static final String ERASE_CURSOR_TO_END_OF_SCREEN = "\033[0J";
	public static final String ERASE_CURSOR_TO_BEGINNING_OF_SCREEN = "\033[1J";
	public static final String ERASE_ENTIRE_SCREEN = "\033[2J";
	
	public static final String MOVE_CURSOR_TO_HOME = "\033[H";
	public static final String MOVE_CURSOR_N_LINES_UP = "\033[nA";
	public static final String MOVE_CURSOR_N_LINES_DOWN = "\033[nB";
	public static final String MOVE_CURSOR_N_COLUMNS_RIGHT = "\033[nC";
	public static final String MOVE_CURSOR_N_COLUMNS_LEFT = "\033[nD";
	public static final String MOVE_CURSOR_TO_BEGINNING_OF_NEXT_LINE_N_LINES_DOWN = "\033[nE";
	public static final String MOVE_CURSOR_TO_BEGINNING_OF_PREVIOUS_LINE_N_LINES_UP = "\033[nF";
	public static final String MOVE_CURSOR_TO_COLUMN_N = "\033[nG";
}