package com.txmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class MyDispatcherConfig {

  @Bean
  DispatcherServlet dispatcher() {
    DispatcherServlet dispatcherServlet = new DispatcherServlet();
    // 进行配置
    return dispatcherServlet;
  }

}
