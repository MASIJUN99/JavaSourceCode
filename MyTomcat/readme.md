# 手写Tomcat

## （一）架构

Tomcat是一个Servlet容器。

### 三种部署方式:
1. war包
2. war （解压后的war包）
3. jar包

### jar包和war包区别是什么？

jar包可以作为依赖和项目出现，war包就是一个单独的项目。

### 配置文件

```xml
<Engine>
  Pipeline
  Valve
    <Host>  <!-- 可以多个 -->
      Pipeline
      Valve
        <Context>  <!-- 可以多个 -->
          Pipeline
          Valve
            <mvc.Servlet>  <!-- 可以多个 -->
            </mvc.Servlet>
        </Context>
    </Host>
</Engine>
```
或者
```xml
<Engine>
  Pipeline
  Valve
    <Host>  <!-- 可以多个 -->
      Pipeline
      Valve
        <Context>  <!-- 可以多个 -->
          Pipeline
          Valve
            <Wrapper>  <!-- 可以多个 -->
              Pipeline
              Valve
              <mvc.Servlet>  <!-- 可以多个 -->
              </mvc.Servlet>
            </Wrapper>
        </Context>
    </Host>
</Engine>
```
