package com.transaction.constant;

public enum TransactionTypeEnum {

  COMMIT("commit"),
  ROLLBACK("rollback");

  private final String type;

  TransactionTypeEnum(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public boolean equals(String type) {
    return this.type.equals(type);
  }


}
