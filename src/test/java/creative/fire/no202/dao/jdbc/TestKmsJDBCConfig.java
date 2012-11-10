package creative.fire.no202.dao.jdbc;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;
/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class TestKmsJDBCConfig {
	private final static String dbconfigFile = "src/main/resources/section/development.properties";

	@Test
	public void testPropertiesLoad() throws FileNotFoundException {
		KmsJDBCConfig config = KmsJDBCConfig.load(dbconfigFile);
		KmsJDBCParams params = config.getJDBCParams();
		Assert.assertNotNull(params.getDbDriver());
	}
}
