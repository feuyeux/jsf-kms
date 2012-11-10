package creative.fire.no202.dao.jdbc;

/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class KmsJDBCParams {
	private String dbUrl;
	private String dbDriver;
	private String username;
	private String password;

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("dbDriver=").append(dbDriver);
		buf.append(", dbUrl=").append(dbUrl);
		buf.append(", username=").append(username);
		buf.append(", password=").append(password);
		return buf.toString();
	}
}
