package com.txmanager.controller;

import com.txmanager.mvc.RequestMapping;
import com.txmanager.mvc.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

  @RequestMapping("/test")
  public String test(@RequestParam("name") String name) {
    System.out.println(name + " 进入test()");
    return "test";
  }
}
