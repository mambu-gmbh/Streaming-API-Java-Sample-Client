package streamingapi.client;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

/**
 * Logger factory
 */
public class LoggerFactory {

	static {
		String path = requireNonNull(LoggerFactory.class.getClassLoader()
				.getResource("logging.properties"))
				.getFile();
		System.setProperty("java.util.logging.config.file", path);
	}

	public static Logger getLogger(String name) {
		return Logger.getLogger(name);
	}
}
