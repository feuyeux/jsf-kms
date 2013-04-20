package creative.fire.no202.bean;

import java.io.File;
import java.io.IOException;
import javax.faces.component.UIComponent;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.jsfunit.api.InitialPage;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Version of the HelloJSFTest that uses Arquillian
 * 
 * @author Stan Silvert
 */
@RunWith(Arquillian.class)
public class TestArticle {
	// property surefire sys prop setting
	public static final boolean IS_JETTY = (System.getProperty("jetty-embedded") != null);

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
				.addPackage(Package.getPackage("creative.fire.no202.bean"))
				// my test package
				.addAsWebResource(new File("src/main/webapp", "index.xhtml"))
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml");
		//      System.out.println(war.toString(true)); // for debugging
		return war;
	}

	@Test
	@InitialPage("/index.faces")
	public void testInitialPage(JSFServerSession server, JSFClientSession client) throws IOException {
		// Test navigation to initial viewID
		Assert.assertEquals("/index.xhtml", server.getCurrentViewID());
	}
}