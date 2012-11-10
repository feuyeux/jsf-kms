package creative.fire.no202.dao.jdbc;

import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import creative.fire.no202.entity.KmsUser;

/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class TestKmsDB {
	Logger logger = Logger.getLogger(getClass());

	@Test
	public void testRetrieveAndFill() throws SQLException {
		String kms_sql = "select u.userId,u.username,u.password from kms_user u where u.username='admin'";
		KmsDB kmsdb = new KmsDB();
		KmsUser user = new KmsUser();
		kmsdb.retrieveAndFill(user, kms_sql);
		logger.info(user);
		Assert.assertEquals("admin", user.getUsername());
	}
}
