package com.river.boot.config;

import com.river.boot.annotation.MyConfiguration;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@MyConfiguration(value = "/other.properties")
public class OtherConfig {
    private String girlfriendName;
}
