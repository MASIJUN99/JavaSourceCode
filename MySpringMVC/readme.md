# 手写SpringMVC

## （一）SpringMVC简介

## （二）Servlet简介

## （三）实现流程

### 1. DispatcherServlet

先编写DispatcherServlet，他负责“分发”接收到的所有请求。

如何编写呢？我们首先要有一个类，继承HttpServlet抽象类。并且重写doGet doPost doDelete...等方法中的其中一个。

即

```java
public class MyDispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("running...");
  }
}
```

那么一个DispatcherServlet这样写好了还不行，因为没有告诉Servlet要走我的，于是我们在Web.xml配置一下。

```xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>{YourDispatcherServletPath}</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
```

这个时候，你将本应用部署在Tomcat就会发现控制台打印了doGet里的话。至此，你的DispatcherServlet完成了接受请求的基本功能。

### 2. 处理请求

在SpringMVC程序中，我们是拥有一个Controller，在其中的方法上添加RequestMapping来配置路由。这样我们就能运行到其中的方法了。

#### ①RequestMapping

手写这个注解并不难，如果你有学过之前的手写IOC，应该就感觉很简单。

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

  String value() default "";
}
```

那么注解写好了，然后呢？

我们需要建立一个URL和Method的映射！

就比如下面的代码，我们method的映射是什么呢？

```java
@RequestMapping("/url1")
class TestController {
  @RequestMapping("/url2")
  public String method() {
  }
}
```

这个时候他的映射就应该是`"/url1" + "/url2"`，我们可以通过刚刚的DispatcherServlet的doGet找这个映射的HashMap啊！

#### ②BeanPostProcessor

刚刚思路已经十分明显了，那么我们现在要创建这个**映射**。

我们怎么办呢？我们可以利用IOC的这个机制，在Bean“出厂”后，立即检查他们的注解情况。

学了IOC这个地方不用我多说，利用反射寻找注解就行。

#### ③package

思路规定完了，现在开始设计一下包结构。

1. 我们已经有了DispatcherServlet，这个不多说。
2. 我们需要一个RequestMapping注解
3. 我们需要有实现BeanPostProcessor的类
4. 这个实现BeanPostProcessor的类，需要有个map来存放映射关系

这个时候我们看SpringMVC的处理流程，当req进入到DispatcherServlet后，他会去HandlerMapping然后再回来...

大家有没有觉得这个过程似曾相识？不就是我们说的吗，我们有一个HandlerMapping类，实现了BeanPostProcessor，我们每次请求到了，我们先进去找映射，然后拿到映射再回来。

就这么干！

[HandlerMapping](src/main/java/com/txmanager/handler/HandlerMapping.java)

我这里把他做成了接口，方便实现两种映射，即Controller映射和Servlet的映射。

#### ④ApplicationContext

一切准备就绪。但是我才发现，我从头到尾根本没有Spring容器？？？那怎么办。

我们在我们的DispatcherServlet里添加`init()`方法，创建一个不就完事了吗？那创建容器的几种方法，我选哪个？最省事必然是xml方式啊。

```xml
<context:component-scan base-package="{YourPackage}"/>
```

创建完可以用bean标签或者注解的方式添加bean啦

当然，也可以用@Configuration的注解方式，也很方便~，自从学了ioc，创建容器跟玩似的。

[参考代码](src/main/java/com/txmanager/mvc/Servlet.java)


### 3. 执行

大家第一反应必须是通过反射执行代码。

那么我们怎么执行呢？通过**适配器模式**，这里也不介绍了，就是这么简单。

