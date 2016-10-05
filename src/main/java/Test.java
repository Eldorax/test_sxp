import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.HttpConfiguration;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.webapp.WebAppContext;

//import TestServlet;

public class Test {

   

    public static void main(String[] args) {
              	
	Server server = new Server();

	ServerConnector connector = new ServerConnector(server);
	connector.setPort(9999);
	HttpConfiguration https = new HttpConfiguration();
	https.addCustomizer(new SecureRequestCustomizer());
	SslContextFactory sslContextFactory = new SslContextFactory();
	System.out.println("Hey !\n");
	System.out.println("Hey !\n");
	 System.out.println("Working Directory = " + System.getProperty("user.dir") + "\n");
	 System.out.println("Hey !\n");
	 System.out.println("Hey !\n");
	sslContextFactory.setKeyStorePath("./keystore.jks");
	sslContextFactory.setKeyStorePassword("123456");
	sslContextFactory.setKeyManagerPassword("123456");

	ServerConnector sslConnector = new ServerConnector(server,
							   new SslConnectionFactory(sslContextFactory, "http/5.0"),
							   new HttpConnectionFactory(https));
	sslConnector.setPort(9998);
	server.setConnectors(new Connector[] { connector, sslConnector });




	WebAppContext context = new WebAppContext();
	context.setServer(server);
	context.setContextPath("/");
	context.setWar("./keystore.jks");

	TestServlet temp = new TestServlet();
        context.addServlet( new ServletHolder(temp), "/*" );
	server.setHandler(context);
	

	
	/*
	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);


	TestServlet temp = new TestServlet();
        context.addServlet( new ServletHolder(temp), "/*" );
	*/

	
	
	while (true) {
	    try {
		server.start();
		server.join();
		break;
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	
	/*
	try {
	    System.in.read();
	    server.stop();
	    server.join();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(100);
	    }
	*/
	
    }
}
