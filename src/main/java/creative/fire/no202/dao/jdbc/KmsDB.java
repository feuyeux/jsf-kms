package creative.fire.no202.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		conn = KmsDBConnectionPool.getInstance().getConnection(500);
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

	private static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void createDbIfNotExist() {
		Connection conn = KmsDBConnectionPool.getInstance().getConnection(1000);
		try {
			if (!conn.isClosed()) {
				System.out.println("Succeeded connecting to the Database!");
				Statement statement = conn.createStatement();
				String sql = "show tables";
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					return;
				} else {
					ArrayList<String> sqls = getCreateSqls();
					for (String createSql : sqls) {
						statement.execute(createSql);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(conn);
		}
	}

	private static ArrayList<String> getCreateSqls() {
		ArrayList<String> sqlList = new ArrayList<String>();
		StringBuilder bf = new StringBuilder();
		bf.append("CREATE TABLE `kms_user` (");
		bf.append("`userId` varchar(255) NOT NULL,");
		bf.append("`password` varchar(255) DEFAULT NULL,");
		bf.append("`username` varchar(255) DEFAULT NULL,");
		bf.append("PRIMARY KEY (`userId`)");
		bf.append(")");
		sqlList.add(bf.toString());

		bf = new StringBuilder();
		bf.append("INSERT INTO `kms_user` VALUES ('kms_user_1300000000000', 'admin', 'admin')");
		bf.append("CREATE TABLE `kms_article` (");
		bf.append("`articleId` varchar(255) NOT NULL,");
		bf.append("`attachment` varchar(255) DEFAULT NULL,");
		bf.append("`content` longtext NOT NULL,");
		bf.append("`insertTime` date NOT NULL,");
		bf.append("`summary` varchar(255) DEFAULT NULL,");
		bf.append("`title` varchar(255) NOT NULL,");
		bf.append("`knowledgeId` varchar(255) NOT NULL,");
		bf.append("`userId` varchar(255) NOT NULL,");
		bf.append("PRIMARY KEY (`articleId`),");
		bf.append("KEY `FK3A8232084F1AD8DD` (`knowledgeId`),");
		bf.append("KEY `FK3A82320812B27EB` (`userId`),");
		bf.append("CONSTRAINT `FK3A82320812B27EB` FOREIGN KEY (`userId`) REFERENCES `kms_user` (`userId`),");
		bf.append("CONSTRAINT `FK3A8232084F1AD8DD` FOREIGN KEY (`knowledgeId`) REFERENCES `kms_knowledge` (`knowledgeId`)");
		bf.append(")");
		sqlList.add(bf.toString());

		bf = new StringBuilder();
		bf.append("CREATE TABLE `kms_book` (");
		bf.append("`bookId` varchar(255) NOT NULL,");
		bf.append("`author` varchar(255) DEFAULT NULL,");
		bf.append("`bookname` varchar(255) DEFAULT NULL,");
		bf.append("`buyTime` date DEFAULT NULL,");
		bf.append("`location` varchar(255) DEFAULT NULL,");
		bf.append("`press` varchar(255) DEFAULT NULL,");
		bf.append("`publishTime` date DEFAULT NULL,");
		bf.append("`knowledgeId` varchar(255) NOT NULL,");
		bf.append("`userId` varchar(255) NOT NULL,");
		bf.append("PRIMARY KEY (`bookId`),");
		bf.append("KEY `FKBACB3F174F1AD8DD` (`knowledgeId`),");
		bf.append("KEY `FKBACB3F1712B27EB` (`userId`),");
		bf.append("CONSTRAINT `FKBACB3F1712B27EB` FOREIGN KEY (`userId`) REFERENCES `kms_user` (`userId`),");
		bf.append("CONSTRAINT `FKBACB3F174F1AD8DD` FOREIGN KEY (`knowledgeId`) REFERENCES `kms_knowledge` (`knowledgeId`)");
		bf.append(")");
		sqlList.add(bf.toString());

		bf = new StringBuilder();
		bf.append("CREATE TABLE `kms_knowledge` (");
		bf.append("`knowledgeId` varchar(255) NOT NULL,");
		bf.append("`description` varchar(255) DEFAULT NULL,");
		bf.append("`name` varchar(255) DEFAULT NULL,");
		bf.append("`touchTime` date DEFAULT NULL,");
		bf.append("`userId` varchar(255) NOT NULL,");
		bf.append("PRIMARY KEY (`knowledgeId`),");
		bf.append("KEY `FKDDC5D11012B27EB` (`userId`),");
		bf.append("CONSTRAINT `FKDDC5D11012B27EB` FOREIGN KEY (`userId`) REFERENCES `kms_user` (`userId`)");
		bf.append(")");
		sqlList.add(bf.toString());

		return sqlList;
	}
}
