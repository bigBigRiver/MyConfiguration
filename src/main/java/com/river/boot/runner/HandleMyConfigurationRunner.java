package com.river.boot.runner;

import com.river.boot.annotation.MyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 在springboot初始化的时候，通过java反射机制，为配置有@MyConfiguration注解的类的字段赋值
 *
 * @author river
 * 2020/1/10
 */
@Slf4j
@Component
public class HandleMyConfigurationRunner implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Properties configProperties = new Properties();

    @Override
    public void run(String... args) throws Exception {
        /*在容器中搜索被@MyConfiguration修饰的所有类名*/
        String[] configNames = applicationContext.getBeanNamesForAnnotation(MyConfiguration.class);
        if (StringUtils.isEmpty(configNames)) {
            return;
        }
        Object[] configObjects = new Object[configNames.length];

        /*在spring容器中获取类名对应的对象*/
        for (int i = 0; i < configNames.length; i++) {
            configObjects[i] = applicationContext.getBean(configNames[i]);
        }
        for (Object object : configObjects) {
            Class<?> clazz = object.getClass();
            MyConfiguration myConfiguration = clazz.getAnnotation(MyConfiguration.class);
            /*获取注解的value值并获取输入流*/
            InputStream inputStream = clazz.getResourceAsStream(myConfiguration.value());
            if (null == inputStream) {
                log.info("未找到资源:" + myConfiguration.value());
                return;
            }
            configProperties.load(inputStream);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);//访问private字段
                field.set(object, configProperties.getProperty(field.getName()));//获取字段名并查询字段名在配置文件中对应的value，再设置到字段中去
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
