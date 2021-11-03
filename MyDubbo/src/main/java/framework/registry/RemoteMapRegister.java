package framework.registry;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import framework.util.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteMapRegister {

  private static Map<String, List<URL>> REGISTER = new HashMap<>();

  public static void regist(String interfaceName, URL url) {
    if (REGISTER.containsKey(interfaceName)) {
      REGISTER.get(interfaceName).add(url);
    } else {
      REGISTER.put(interfaceName, new ArrayList<>(){{add(url);}});
    }

    saveRegister();
  }

  public static List<URL> get(String interfaceName) {
    loadRegister();
    return REGISTER.get(interfaceName);
  }

  public static void saveRegister() {
    try {
      FileOutputStream outputStream = new FileOutputStream("MyDubbo/map.txt");
      outputStream.write(JSONObject.toJSONString(REGISTER).getBytes());
      outputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public static void loadRegister() {
    try {
      FileInputStream inputStream = new FileInputStream("MyDubbo/map.txt");
      byte[] bytes = inputStream.readAllBytes();
      String s = bytesToString(bytes);
      REGISTER = JSONObject.parseObject(s, new TypeReference<HashMap<String, List<URL>>>() {});
      inputStream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static String bytesToString(byte[] b) {
    StringBuffer result = new StringBuffer("");
    int length = b.length;
    for (int i=0; i<length; i++) {
      result.append((char)(b[i]));
    }
    return result.toString();
  }



}
