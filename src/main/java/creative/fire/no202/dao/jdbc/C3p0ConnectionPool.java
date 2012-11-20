package creative.fire.no202.dao.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * @author feuyeux@gmail.com
 * @version 2.1
 * http://www.mchange.com/projects/c3p0/index.html
 */
public class C3p0ConnectionPool {
	private Logger logger = Logger.getLogger(KmsDBConnectionPool.class);
	private KmsJDBCParams params;
	private final static String dbconfigFile = "src/main/resources/section/development.properties";
	ComboPooledDataSource ds;

	public C3p0ConnectionPool() {
		KmsJDBCConfig config = KmsJDBCConfig.load(dbconfigFile);
		if (config != null) {
			params = config.getJDBCParams();
		}
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(params.getDbDriver());
		} catch (PropertyVetoException e) {
			logger.error(e);
		}
		ds.setJdbcUrl(params.getDbUrl());
		ds.setUser(params.getUsername());
		ds.setPassword(params.getPassword());
		ds.setMaxPoolSize(10);
		ds.setMinPoolSize(5);
		ds.setMaxStatements(0);
	}

	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error(e);
			return null;
		}
	}
}
