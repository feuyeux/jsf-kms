package creative.fire.no202.dao.jdbc;

import java.sql.Connection;

import junit.framework.Assert;

import org.junit.Test;
/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class TestKmsDBConnectionPool {
	KmsDBConnectionPool connectionPool = KmsDBConnectionPool.getInstance();

	@Test
	public void testGetConnection() {
		Connection connection = connectionPool.getConnection(500);
		Assert.assertNotNull(connection);
		connectionPool.release(connection);
	}
}
