package io.eventuate.tram.examples.performance.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ConfigurableTopicPartitionToSwimlaneMapping partitionToSwimlaneMapping;

    @PostMapping("/concurrency/{concurrency}")
    public void setConcurrency(@PathVariable int concurrency) {
        partitionToSwimlaneMapping.setConcurrency(concurrency);
    }

}
