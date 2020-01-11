package com.river.boot;

import com.river.boot.config.MyConfig;
import com.river.boot.config.OtherConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BootApplicationTests {

    @Autowired
    MyConfig myConfig;

    @Test
    void logSomething() {
        log.info(myConfig.getUserName() + ":" + myConfig.getPassword());
    }

}
