package framework;

import framework.util.URL;
import java.util.List;
import java.util.Random;

public class LoadBalancer {

  public static URL getURL(List<URL> urls) {
    int i = new Random(System.currentTimeMillis()).nextInt(urls.size());
    return urls.get(i);
  }

}
