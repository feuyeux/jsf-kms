package org.feuyeux.air.jsf.kms;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.jsfunit.api.InitialPage;
import org.jboss.jsfunit.jsfsession.JSFClientSession;
import org.jboss.jsfunit.jsfsession.JSFServerSession;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author feuyeux@gmail.com
 * @version 2.2
 */
@Ignore
@RunWith(Arquillian.class)
public class TestArticle {
	public static final boolean IS_JETTY = (System.getProperty("jetty-embedded") != null);

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive war0 = ShrinkWrap.create(WebArchive.class, "kms.war");
		WebArchive war1 = war0.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
		boolean recursived=true;
		WebArchive war2 = war1.addPackages(recursived, Package.getPackage("creative.fire.no202")) ;
		WebArchive war3 = war2.addAsWebResource(new File("src/main/webapp", "signin.xhtml"));
		war3.addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml");
		war3.addAsWebInfResource(new File("src/main/resources/kmsMessage.properties"), "classes/kmsMessage.properties");
		war3.addAsWebInfResource(new File("src/main/resources/kmsMessage_en.properties"), "classes/kmsMessage_en.properties");
		war3.addAsWebInfResource(new File("src/main/resources/META-INF/persistence.dev.xml"), "classes/META-INF/persistence.xml");

		System.out.println(war3.toString(true)); // for debugging
		return war3;
	}

	@Test
	@InitialPage("/signin.faces")
	public void testInitialPage(JSFServerSession server, JSFClientSession client) throws IOException {
		// Test navigation to initial viewID
		Assert.assertEquals("/signin.xhtml", server.getCurrentViewID());
	}
}