package com.transaction.transactional;

import com.transaction.constant.TransactionTypeEnum;
import com.transaction.util.Task;

public class Transaction {

  private String groupId;
  private String transactionId;
  private TransactionTypeEnum transactionType;
  private Task task;

  public Transaction(String groupId, String transactionId,
      TransactionTypeEnum transactionType) {
    this.groupId = groupId;
    this.transactionId = transactionId;
    this.transactionType = transactionType;
    this.task = new Task();
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionTypeEnum getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }
}
