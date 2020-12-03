package config;

import lombok.SneakyThrows;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesReader {
	private static final String APPLICATION_PROPERTIES = "application.properties";
	private Properties properties;

	@SneakyThrows
	public PropertiesReader() {
		properties = new Properties();
		properties.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES), Charset.forName("cp1251")));
	}

	public String getProperties(String name) {
		if (name == null) {
			return null;
		}

		return properties.getProperty(name);
	}
}
