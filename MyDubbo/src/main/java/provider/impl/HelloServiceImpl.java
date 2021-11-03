package provider.impl;

import provider.HelloService;

public class HelloServiceImpl implements HelloService {

  @Override
  public String sayHello(String username) {
    return "Hello " + username;
  }
}
