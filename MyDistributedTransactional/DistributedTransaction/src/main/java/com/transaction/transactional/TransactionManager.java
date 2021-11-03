package com.transaction.transactional;

import com.alibaba.fastjson.JSONObject;
import com.transaction.constant.TransactionCommandEnum;
import com.transaction.constant.TransactionTypeEnum;
import com.transaction.netty.NettyClient;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionManager {

  private static NettyClient nettyClient;

  private static Map<String, Transaction> localTransaction = new HashMap<>();

  //  private static ThreadLocal<LbTransaction> current = new ThreadLocal<>();
    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();
  //  private static ThreadLocal<Integer> transactionCount = new ThreadLocal<>();

  @Autowired
  public void setNettyClient(NettyClient nettyClient) {
    TransactionManager.nettyClient = nettyClient;
  }

  public static String createTransactionGroup() {
    String groupId = UUID.randomUUID().toString();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("groupId", groupId);
    jsonObject.put("command", TransactionCommandEnum.CREATE.getCommand());

    nettyClient.send(jsonObject);
    currentGroupId.set(groupId);

    System.out.println("创建事务组");

    return groupId;
  }

  public static void addTransaction(String groupId, TransactionTypeEnum transactionType) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("groupId", groupId);
    jsonObject.put("transactionType", transactionType.getType());
    jsonObject.put("command", TransactionCommandEnum.ADD.getCommand());

    Transaction transaction = new Transaction(groupId, UUID.randomUUID().toString(),
        transactionType);
    localTransaction.put(groupId, transaction);

    nettyClient.send(jsonObject);

    System.out.println("添加分支");
  }

  public static Transaction getTransaction(String groupId) {
    return localTransaction.get(groupId);
  }

  public static int getTransactionCount() {
    return localTransaction.size();
  }

  public static String getCurrentGroupId() {
    return currentGroupId.get();
  }
}
