package com.me.spring;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyApplicationContext {

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> singletonObjects = new HashMap<>();

    private List<BeanPostProcessor> beanPostProcessorsList = new ArrayList<>();

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;
        scan(configClass);  // bean扫描，生成BeanDefinition对象并放入map
        preInstantiateSingletons();  // 单例实例化

    }

    private void preInstantiateSingletons() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            String beanName = entry.getKey();
            if (beanDefinition.getScope().equals("singleton")) {
                // 为单例对象创建对象并放入单例池
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {

        try {
            Class clazz = beanDefinition.getClazz();
            Object instance = clazz.newInstance();
            // 依赖注入
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowire.class)) {
                    String name = field.getName();
                    Object bean = getBean(name);
                    if (bean == null && field.getAnnotation(Autowire.class).required()) {
                        // 这个时候报错
                    }
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }

            // 初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessorBeforeInitialization(instance, beanName);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // 初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessorAfterInitialization(instance, beanName);
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scan(Class configClass) {
        // 解析配置类
        ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        String path = componentScan.value();  // 获取ComponentScan的属性
        path = path.replace('.', '/');

        // 找到path包下所有Component的类
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
        URL target = classLoader.getResource(path);
        File dir = new File(target.getFile());  // 得到类对象
        File[] files = dir.listFiles();

        for (File file : files) {
            String absolutePath = file.getAbsolutePath();
            if (absolutePath.endsWith(".class")) {
                String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                className = className.replace('\\', '.');  // 获得属性的包

                try {
                    Class<?> clazz = classLoader.loadClass(className);  // 加载类对象
                    if (clazz.isAnnotationPresent(Component.class)) {  // 判断Component注解存在

                        if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                            // 把初始化前后的类加进去
                            BeanPostProcessor beanPostProcessor = (BeanPostProcessor) clazz.newInstance();
                            beanPostProcessorsList.add(beanPostProcessor);
                        }

                        // 1. 获取beanName
                        Component component = clazz.getAnnotation(Component.class);
                        String beanName = component.value();
                        // 2. 包装BeanDefinition
                        BeanDefinition beanDefinition = new BeanDefinition();
                        beanDefinition.setClazz(clazz);
                        if (clazz.getAnnotation(Scope.class) != null) {
                            beanDefinition.setScope(clazz.getAnnotation(Scope.class).value());
                        } else {
                            beanDefinition.setScope("singleton");
                        }

                        beanDefinitionMap.put(beanName, beanDefinition);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object getBean(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException();
        } else {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            String scope = beanDefinition.getScope();

            if (scope == null || scope.equals("singleton")) {
                // 单例bean
                return singletonObjects.get(beanName);
            } else if (scope.equals("prototype")) {
                // 原型bean
                return createBean(beanName, beanDefinition);
            }


            return null;
        }
    }
}
