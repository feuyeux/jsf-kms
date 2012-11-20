package creative.fire.no202.bean.sdae;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class MysqlServiceTest {
	String vcap_services = "{\"mysql-5.1\":" +
			"[{\"name\":\"1892990657-testssj5-javassj1346-mysql\"," +
			"\"label\":\"mysql-5.1\",\"plan\":\"free\",\"tags\":[\"mysql\",\"mysql-5.1\",\"relational\"]," +
			"\"credentials\":{\"name\":\"deb96ac0098354a1cb95f2518d9c16a70\",\"hostname\":\"10.177.32.234\",\"host\":\"10.177.32.234\",\"port\":3306,\"user\":\"udreLUNo5g743\",\"username\":\"udreLUNo5g743\",\"password\":\"pSONlXyOJxmbc\"}" +
			"}]" +
			"}";
	
	@Test
	public void hello(){
		try {
			JSONObject object = new JSONObject(vcap_services);
			JSONObject credentials = object.getJSONArray("mysql-5.1").getJSONObject(0).getJSONObject("credentials");
			String host = credentials.getString("host");
			int port = credentials.getInt("port");
			String username = credentials.getString("username");
			String password = credentials.getString("password");
			
			System.out.println("host:"+host+",port:"+port+",username:"+username+",password:"+password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
