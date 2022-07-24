package de.ancash.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import de.ancash.libs.org.simpleyaml.configuration.ConfigurationSection;
import de.ancash.libs.org.simpleyaml.configuration.file.YamlFile;
import de.ancash.libs.org.simpleyaml.exceptions.InvalidConfigurationException;

import de.ancash.misc.IPrintStream.ConsoleColor;

public class FileUtils {
  
	public static void setMissingConfigurationSections(YamlFile original, InputStream src) throws IOException, InvalidConfigurationException {
		setMissingConfigurationSections(original, src, new HashSet<>());
	}
	
	public static void setMissingConfigurationSections(YamlFile original, InputStream src, Set<String> ignoreSectionIfSectionContains) throws IOException, InvalidConfigurationException {
		YamlFile srcYaml = new YamlFile(new File("temp-" + System.nanoTime()));
		de.ancash.libs.org.apache.commons.io.FileUtils.copyInputStreamToFile(src, srcYaml.getConfigurationFile());
		setMissingConfigurationSections(original, srcYaml, ignoreSectionIfSectionContains);
		srcYaml.deleteFile();
	}
	
	public static void setMissingConfigurationSections(YamlFile original, YamlFile src) throws InvalidConfigurationException, IOException {
		setMissingConfigurationSections(original, src, new HashSet<>());
	}
	
	public static void setMissingConfigurationSections(YamlFile original, YamlFile src, Set<String> ignoreSectionIfSectionContains) throws InvalidConfigurationException, IOException {
		original.loadWithComments();
		src.loadWithComments();
		for(String key : src.getKeys(false))
			compute(original, ignoreSectionIfSectionContains, key, src);
		original.save();
		src.save();
	}
	
	private static void compute(YamlFile original, Set<String> ignoreSectionIfSectionContains, String key, ConfigurationSection curSection) {		
		if(curSection.isConfigurationSection(key)) {
			ConfigurationSection value = curSection.getConfigurationSection(key);
			
			if(!original.contains(value.getCurrentPath()))
				set(original, curSection, key);
			else {
				for(String keys : curSection.getKeys(false))
					if(ignoreSectionIfSectionContains.contains(keys))
						return;
				
				for(String keys : value.getKeys(false))
					compute(original, ignoreSectionIfSectionContains, keys, value);
			}			
		} else {
			String path = curSection.getParent() == null ? key : curSection.getCurrentPath() + "." + key;
			
			if(!original.contains(path)) {
				
				for(String keys : curSection.getKeys(false)) {
					if(!ignoreSectionIfSectionContains.contains(keys)) 
						continue;
					if(curSection.getParent() == null) {
						if(original.contains(keys))
							return;
					} else {
						if(original.contains(curSection.getCurrentPath() + "." + keys))
							return;
					}
				}
				set(original, curSection, key);
			}
		}		
	}
	
	private static void set(YamlFile file, ConfigurationSection section, String key) {
		String path = "".equals(section.getCurrentPath()) ? key : section.getCurrentPath() + "." + key;
		
		if(section.isConfigurationSection(key)) {
			file.getConfigurationSection(section.getCurrentPath()).createSection(key, section.getConfigurationSection(key).getMapValues(true));
			System.out.println(ConsoleColor.YELLOW_BOLD_BRIGHT + "Could not find key '" + ConsoleColor.RESET + path + ConsoleColor.YELLOW_BOLD_BRIGHT + "' in '" + ConsoleColor.RESET + file.getFilePath() + ConsoleColor.YELLOW_BOLD_BRIGHT + "'. Set to '" + ConsoleColor.RESET + section.getConfigurationSection(key).getMapValues(true) + ConsoleColor.YELLOW_BOLD_BRIGHT + "'" + ConsoleColor.RESET);
		} else {
			file.set(path, section.get(key));
			System.out.println(ConsoleColor.YELLOW_BOLD_BRIGHT + "Could not find key '" + ConsoleColor.RESET + path + ConsoleColor.YELLOW_BOLD_BRIGHT + "' in '" + ConsoleColor.RESET + file.getFilePath() + ConsoleColor.YELLOW_BOLD_BRIGHT + "'. Set to '" + ConsoleColor.RESET + section.get(key) + ConsoleColor.YELLOW_BOLD_BRIGHT + "'" + ConsoleColor.RESET);
		}
	}
}