package io.eventuate.tram.examples.performance.subscriber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ConsumerMainTest.Config.class)
public class ConsumerMainTest {


    @Configuration
    @EnableAutoConfiguration
    @Import(ConsumerMain.class)
    static public class Config {
    }

    @Test
    public void shouldLoad() {

    }
}