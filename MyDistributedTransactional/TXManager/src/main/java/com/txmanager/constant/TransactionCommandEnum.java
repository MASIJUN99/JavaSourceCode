package com.txmanager.constant;

public enum TransactionCommandEnum {
  CREATE("create"),
  ADD("add");

  private final String command;

  TransactionCommandEnum(String command) {
    this.command = command;
  }

  public String getCommand() {
    return this.command;
  }

  public boolean equals(String command) {
    return this.command.equals(command);
  }
}
