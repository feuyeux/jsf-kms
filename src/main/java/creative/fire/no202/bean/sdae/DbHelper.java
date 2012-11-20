package creative.fire.no202.bean.sdae;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbHelper {
	public static List<?> getAllBlogs(){
		MysqlServiceHelper sqlServer = new MysqlServiceHelper();
		sqlServer.getMysqlService();
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+sqlServer.getDbHost()+":"+sqlServer.getDbPort()+"/"+sqlServer.getDbName();
		String user = sqlServer.getDbUser();
		String password = sqlServer.getDbPassword();
	
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
			if(!conn.isClosed()){
				Statement statement = conn.createStatement();
				String sql = "select * from blogs";
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					//myBlogs.add(new Blog(rs.getString("title"),rs.getString("context")));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeConn(conn);
		}
		
		return null;
	}
	
	public static void createDbIfNotExist(){
		MysqlServiceHelper sqlServer = new MysqlServiceHelper();
		sqlServer.getMysqlService();
		
		System.out.println("dbHost:"+sqlServer.getDbHost()+",dbPort:"+sqlServer.getDbPort()+"<br/>");
		System.out.println("dbUser:"+sqlServer.getDbUser()+",dbPassword:"+sqlServer.getDbPassword()+"<br/>");
		System.out.println("dbName:"+sqlServer.getDbName()+"<br/>");
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+sqlServer.getDbHost()+":"+sqlServer.getDbPort()+"/"+sqlServer.getDbName();
		String user = sqlServer.getDbUser();
		String password = sqlServer.getDbPassword();
	
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
			if(!conn.isClosed()){
				System.out.println("Succeeded connecting to the Database!");
				Statement statement = conn.createStatement();
				String sql = "show tables";
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					return;
				}else {
					String createSql = "CREATE TABLE blogs ( id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title VARCHAR(50), context VARCHAR(300));";
					statement.execute(createSql);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeConn(conn);
		}
	}
	
	private static void closeConn(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveBlog() {
		MysqlServiceHelper sqlServer = new MysqlServiceHelper();
		sqlServer.getMysqlService();
		
		System.out.println("dbHost:"+sqlServer.getDbHost()+",dbPort:"+sqlServer.getDbPort()+"<br/>");
		System.out.println("dbUser:"+sqlServer.getDbUser()+",dbPassword:"+sqlServer.getDbPassword()+"<br/>");
		System.out.println("dbName:"+sqlServer.getDbName()+"<br/>");
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+sqlServer.getDbHost()+":"+sqlServer.getDbPort()+"/"+sqlServer.getDbName();
		String user = sqlServer.getDbUser();
		String password = sqlServer.getDbPassword();
	
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
			if(!conn.isClosed()){
				String insertSql = "insert into blogs (title,context) values (?,?)";
				PreparedStatement pStatement = conn.prepareStatement(insertSql);
				pStatement.setString(1, "blog.getTitle()");
				pStatement.setString(2, "blog.getContext()");
				
				pStatement.execute();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeConn(conn);
		}
	}
}
