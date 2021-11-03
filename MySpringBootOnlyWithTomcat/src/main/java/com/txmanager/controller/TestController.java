package com.txmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


  // try to open http://localhost:8081/MySpringBootOnlyWithTomcat/test
  @GetMapping("/test")
  public String test() {
    return "hello spring";
  }

}
