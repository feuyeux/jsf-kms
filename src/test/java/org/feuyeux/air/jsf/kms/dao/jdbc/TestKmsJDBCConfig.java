package org.feuyeux.air.jsf.kms.dao.jdbc;

import org.feuyeux.air.jsf.kms.dao.jdbc.KmsJDBCParams;
import org.feuyeux.air.jsf.kms.dao.jdbc.KmsJDBCConfig;
import java.io.FileNotFoundException;

import junit.framework.Assert;
import org.junit.Ignore;

import org.junit.Test;
/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
@Ignore
public class TestKmsJDBCConfig {
	private final static String dbconfigFile = "src/main/resources/section/development.properties";

	@Test
	public void testPropertiesLoad() throws FileNotFoundException {
		KmsJDBCConfig config = KmsJDBCConfig.load(dbconfigFile);
		KmsJDBCParams params = config.getJDBCParams();
		Assert.assertNotNull(params.getDbDriver());
	}
}
