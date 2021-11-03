package com.txmanager.controller;

import com.txmanager.mvc.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/hello")
public class HelloController2 {

  @RequestMapping("/test")
  public String test2() {
    System.out.println("test2 is running...");
    return "hello world!";
  }

}
