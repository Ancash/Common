package de.ancash.misc;

import java.lang.reflect.Field;

public final class ReflectionUtils{
   
	public static String toString(Object o) {
		return toString(o, false);
	}
	
	public static String toString(Object o, boolean lineSeperator) {
		StringBuilder builder = new StringBuilder();
		if(o == null) {
			builder.append("null");
			return builder.toString();
		}
		
		if(lineSeperator)
			builder.append(System.lineSeparator());
		builder.append(o.getClass().getName());
		builder.append("{");
		if(lineSeperator)
			builder.append(System.lineSeparator());
		
		Field[] fields = o.getClass().getDeclaredFields();
		
		for(Field f : fields) {
			if(!f.isAccessible())
				f.setAccessible(true);
			builder.append((lineSeperator ? "" : ";") + f.getName());
			builder.append("=");
			try {
				builder.append(f.get(o));
			} catch (IllegalArgumentException | IllegalAccessException e) {				
				e.printStackTrace();
			}
			if(lineSeperator)
				builder.append(System.lineSeparator());
		}
		
		builder.append("}");
		return builder.toString().replaceFirst(";", "");
	}
	
}
