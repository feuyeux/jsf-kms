package creative.fire.no202.bean.sdae;

import org.json.JSONException;
import org.json.JSONObject;

public class MysqlServiceHelper {
	String dbHost="";
	int dbPort=-1;
	String dbName="";
	String dbUser="";
	String dbPassword="";
	
	public boolean getMysqlService(){
		try {
			String vcap_services = System.getenv("VCAP_SERVICES");
			if (vcap_services == null || vcap_services.equals("")) {
				return false;
			}
			
			JSONObject object = new JSONObject(vcap_services);
			JSONObject credentials = object.getJSONArray("mysql-5.1").getJSONObject(0).getJSONObject("credentials");
			dbHost = credentials.getString("host");
			dbPort = credentials.getInt("port");
			dbUser = credentials.getString("username");
			dbPassword = credentials.getString("password");
			dbName = credentials.getString("name");
			
			System.out.println("host:"+dbHost+",port:"+dbPort+",daName:"+dbName+",username:"+dbUser+",password:"+dbPassword);
			
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			
			return false;
		}
	}

	public String getDbHost() {
		return dbHost;
	}

	public int getDbPort() {
		return dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}
}
