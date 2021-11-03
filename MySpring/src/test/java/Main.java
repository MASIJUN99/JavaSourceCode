import com.me.spring.MyApplicationContext;

public class Main {
    public static void main(String[] args) {
        // 使用我们的Spring
        MyApplicationContext applicationContext = new MyApplicationContext(AppConfig.class);

        UserService userService = (UserService) applicationContext.getBean("userService");

        System.out.println(userService);
    }
}
