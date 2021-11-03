package framework.protocol.http;

import java.util.TooManyListenersException;
import org.apache.catalina.Engine;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class HttpServer {

  public void start(String hostname, Integer port) {
    // 获取用户配置的tomcat jetty
    Tomcat tomcat = new Tomcat();

    Server server = tomcat.getServer();
    Service service = server.findService("Tomcat");

    Connector connector = new Connector();
    connector.setPort(port);

    StandardEngine standardEngine = new StandardEngine();
    standardEngine.setDefaultHost(hostname);

    StandardHost standardHost = new StandardHost();
    standardHost.setName(hostname);

    String contextPath = "/MyDubbo";
    StandardContext standardContext = new StandardContext();
    standardContext.setPath(contextPath);
    standardContext.addLifecycleListener(new Tomcat.FixContextListener());

    standardHost.addChild(standardContext);
    standardEngine.addChild(standardHost);

    service.setContainer(standardEngine);
    service.addConnector(connector);

    tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet());
    standardContext.addServletMappingDecoded("/*", "dispatcher");

    try {
      tomcat.start();
      tomcat.getServer().await();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
