package com.txmanager;

import com.txmanager.boot.MySpringApplication;
import com.txmanager.boot.MySpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MySpringBootApplication
public class Test {

  public static void main(String[] args) {
    ConfigurableApplicationContext run = MySpringApplication.run(Test.class);
    System.out.println("running...");
  }

}
