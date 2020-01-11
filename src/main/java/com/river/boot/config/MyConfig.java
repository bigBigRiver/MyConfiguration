package com.river.boot.config;

import com.river.boot.annotation.MyConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@MyConfiguration(value = "/config.properties")
public class MyConfig {
    private String userName;
    private String password;
}
