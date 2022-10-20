package org.codebetter.redisbase.redismq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ZSetVerTest {
    @Resource
    ZSetVer zSetVer;

    @Test
    public void testProducer() {
       zSetVer.producer();
    }

    @Test
    public void testConsumerDelayMessage() {
       zSetVer.consumerDelayMessage();
    }
}
