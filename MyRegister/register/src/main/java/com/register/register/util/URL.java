package com.register.register.util;

public class URL {

  private String hostname;
  private Integer port;

  public URL() {
  }

  public URL(String hostname, Integer port) {
    this.hostname = hostname;
    this.port = port;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "URL{" +
        "hostname='" + hostname + '\'' +
        ", port=" + port +
        '}';
  }
}
