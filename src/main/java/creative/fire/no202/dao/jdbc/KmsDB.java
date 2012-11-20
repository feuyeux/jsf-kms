package creative.fire.no202.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import creative.fire.no202.entity.KmsUser;

/**
 * @author feuyeux@gmail.com
 * @version 2.1
 */
public class KmsDB {
	Logger logger = Logger.getLogger(getClass());

	public void retrieveAndFill(KmsUser user, String kms_one_line_sql) throws SQLException {
		Connection conn;
		conn =  KmsDBConnectionPool.getInstance().getConnection(500);
		//conn = new C3p0ConnectionPool().getConnection();

		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(kms_one_line_sql);
			while (rs.next()) {
				user.setUserId(rs.getString("userId"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stat != null) {
				stat.close();
			}
			KmsDBConnectionPool.getInstance().release(conn);
		}
	}
}
