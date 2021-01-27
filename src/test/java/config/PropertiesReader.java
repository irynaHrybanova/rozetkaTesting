package config;

import lombok.SneakyThrows;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReader {
	private Properties properties;

	@SneakyThrows
	public PropertiesReader(String propertiesName) {
		properties = new Properties();
		properties.load(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(propertiesName)), Charset.forName("cp1251")));
	}

	public String getProperties(String name) {
		if (name == null) {
			return null;
		}

		return properties.getProperty(name);
	}
}
