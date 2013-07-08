package org.feuyeux.air.jsf.kms.dao.jdbc;

import org.feuyeux.air.jsf.kms.dao.jdbc.KmsDB;
import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import org.feuyeux.air.jsf.kms.entity.KmsUser;

/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
@Ignore
public class TestKmsDB {
	Logger logger = Logger.getLogger(getClass());

	@Test
	public void testRetrieveAndFill() throws SQLException {
		KmsDB.createDbIfNotExist(); 
		String kms_sql = "select u.userId,u.username,u.password from kms_user u where u.username='admin'";
		KmsDB kmsdb = new KmsDB();
		KmsUser user = new KmsUser();
		kmsdb.retrieveAndFill(user, kms_sql);
		logger.info(user);
		Assert.assertEquals("admin", user.getUsername());
	}
}
