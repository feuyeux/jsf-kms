package creative.fire.no202.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class KmsDBConnectionPool {
	private Logger logger = Logger.getLogger(KmsDBConnectionPool.class);
	private KmsJDBCParams params;
	private BlockingQueue<Connection> pool;

	private final static String dbconfigFile = "src/main/resources/section/development.properties";
	private final int poolSize = 100;
	private AtomicInteger poolCount;
	private static KmsDBConnectionPool instance = null;

	private KmsDBConnectionPool() {
		init();
	}

	public static KmsDBConnectionPool getInstance() {
		if (instance == null) {
			instance = new KmsDBConnectionPool();
		}
		return instance;
	}

	private void init() {
		KmsJDBCConfig config = KmsJDBCConfig.load(dbconfigFile);
		if (config != null) {
			params = config.getJDBCParams();
		}
		poolCount = new AtomicInteger();
		pool = new LinkedBlockingQueue<Connection>();
	}

	public Connection getConnection(int timeout) {
		Connection conn;
		try {
			conn = pool.poll(timeout, TimeUnit.MILLISECONDS);
			if (conn == null) {
				synchronized (poolCount) {
					if (poolCount.get() < poolSize) {
						Class.forName(params.getDbDriver());
						conn = DriverManager.getConnection(params.getDbUrl(), params.getUsername(), params.getPassword());
						pool.offer(conn);
						poolCount.incrementAndGet();
					}
				}
			}
			return conn;
		} catch (InterruptedException e) {
			logger.error("get connection failed: ", e);
		} catch (ClassNotFoundException e) {
			logger.error("get connection failed: ", e);
		} catch (SQLException e) {
			logger.error("get connection failed: ", e);
		}
		return null;
	}

	public void release(Connection conn) {
		try {
			pool.add(conn);
		} catch (Exception e) {
			logger.error("release connection failed: ", e);
			try {
				conn.close();
			} catch (SQLException e2) {
				logger.error("close connection failed: ", e2);
			}
		}
	}

	public void closePool() {
		for (int i = 0; i < pool.size(); i++) {
			try {
				pool.poll().close();
			} catch (SQLException e) {
				logger.error("close connection failed: ", e);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		closePool();
		super.finalize();
	}
}
