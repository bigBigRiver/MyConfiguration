package com.river.boot.annotation;

import java.lang.annotation.*;

/**
 * @author river
 * 2020/1/10
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyConfiguration {
    String value() default "application.properties";
}
