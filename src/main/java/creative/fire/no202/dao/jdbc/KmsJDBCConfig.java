package creative.fire.no202.dao.jdbc;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class KmsJDBCConfig {
	final String DB_DRIVER = "db.driver";
	final String DB_ADDRESS = "db.server.address";
	final String DB_USERNAME = "db.username";
	final String DB_PASSWORD = "db.password";
	private static KmsJDBCConfig instance;
	private Properties properties;
	private Logger loger = Logger.getLogger(KmsJDBCConfig.class);

	private KmsJDBCConfig(String propertiesFile){
		properties = new Properties();
		InputStream inputStream;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(propertiesFile));
			properties.load(inputStream);
		} catch (IOException e) {
			loger.error("load jdbc configuration file failed.");
		}
	}

	public static KmsJDBCConfig load(String propertiesFile){
		if (instance == null) {
			instance = new KmsJDBCConfig(propertiesFile);
		}
		return instance;
	}

	public KmsJDBCParams getJDBCParams() {
		KmsJDBCParams params = new KmsJDBCParams();
		params.setDbDriver(properties.getProperty(DB_DRIVER));
		params.setUsername(properties.getProperty(DB_USERNAME));
		params.setPassword(properties.getProperty(DB_PASSWORD));
		params.setDbUrl("jdbc:mysql://"+properties.getProperty(DB_ADDRESS)+":3306/kms");
		return params;
	}
}
