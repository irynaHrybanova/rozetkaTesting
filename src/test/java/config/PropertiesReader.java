package config;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
	private static final String propertyFile = "application.properties";

	public static String getProperties(String name) {
		if (name == null) {
			return null;
		}

		Properties properties = new Properties();

		try {
			properties.load(PropertiesReader.class.getClassLoader().getResourceAsStream(propertyFile));
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		return properties.getProperty(name);
	}
}
