package com.txmanager.boot;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {

  public static ConfigurableApplicationContext run(Class config) {
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
    applicationContext.register(config);
    applicationContext.refresh();

    // 到底启用tomcat还是jetty还是undertow？
    startTomcat(applicationContext);
    return applicationContext;
  }

  private static Tomcat startTomcat(AnnotationConfigWebApplicationContext applicationContext) {
    Tomcat tomcat = new Tomcat();

    Server server = tomcat.getServer();
    Service service = server.findService("Tomcat");

    Connector connector = new Connector();
    connector.setPort(8081);

    StandardEngine engine = new StandardEngine();
    engine.setDefaultHost("localhost");

    StandardHost host = new StandardHost();
    host.setName("localhost");

    String contextPath = "/MySpringBootOnlyWithTomcat";
    StandardContext context = new StandardContext();
    context.setPath(contextPath);
    context.addLifecycleListener(new Tomcat.FixContextListener());

    host.addChild(context);
    engine.addChild(host);

    service.setContainer(engine);
    service.addConnector(connector);

    DispatcherServlet dispatcher = (DispatcherServlet) applicationContext.getBean("dispatcher");
    tomcat.addServlet(contextPath, "dispatcher", dispatcher);
    context.addServletMappingDecoded("/*", "dispatcher");

    try {
      tomcat.start();
    } catch (LifecycleException e) {
      e.printStackTrace();
    }

    Thread thread = new Thread(() -> {
      tomcat.getServer().await();
    });
    thread.setDaemon(false);
    thread.start();

    return tomcat;
  }

}
