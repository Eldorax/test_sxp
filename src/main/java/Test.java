import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.http.HttpVersion;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.webapp.WebAppContext;

//import TestServlet;

public class Test {

   
    public static void main(String[] args) throws Exception {
              	
	Server server = new Server();

	// Http config
	HttpConfiguration http_config = new HttpConfiguration();
	http_config.setSecureScheme("https");
	http_config.setSecurePort(8443);
	http_config.setOutputBufferSize(38768);

	// Http Connector
	ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config) );
	http.setPort(8080);
	http.setIdleTimeout(30000);

	// SSL Context factory for HTTPS
	SslContextFactory sslContextFactory = new SslContextFactory();
	sslContextFactory.setKeyStorePath("./keystore.jks");
	sslContextFactory.setKeyStorePassword("123456");
	sslContextFactory.setKeyManagerPassword("123456");

	// HTTPS Config
	HttpConfiguration https_config = new HttpConfiguration(http_config);
	SecureRequestCustomizer src = new SecureRequestCustomizer();
	src.setStsMaxAge(2000);
	src.setStsIncludeSubDomains(true);
	https_config.addCustomizer(src);

	// HTTPS Connector
	ServerConnector https = new ServerConnector(server,
						    new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
						    new HttpConnectionFactory(https_config));
	https.setPort(8443);
	https.setIdleTimeout(500000);

	server.setConnectors(new Connector[] { http, https});
	

	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

	TestServlet temp = new TestServlet();
        context.addServlet( new ServletHolder(temp), "/*" );
	

	server.start();
	server.join();
	
    }	
}

