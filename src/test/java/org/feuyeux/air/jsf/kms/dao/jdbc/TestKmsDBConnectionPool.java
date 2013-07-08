package org.feuyeux.air.jsf.kms.dao.jdbc;

import org.feuyeux.air.jsf.kms.dao.jdbc.KmsDBConnectionPool;
import java.sql.Connection;

import junit.framework.Assert;
import org.junit.Ignore;

import org.junit.Test;
/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
@Ignore
public class TestKmsDBConnectionPool {
	KmsDBConnectionPool connectionPool = KmsDBConnectionPool.getInstance();

	@Test
	public void testGetConnection() {
		Connection connection = connectionPool.getConnection(500);
		Assert.assertNotNull(connection);
		connectionPool.release(connection);
	}
}
