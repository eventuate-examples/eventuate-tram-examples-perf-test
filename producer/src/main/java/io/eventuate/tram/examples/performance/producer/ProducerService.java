package io.eventuate.tram.examples.performance.producer;


import io.eventuate.tram.events.publisher.DomainEventPublisher;
import io.eventuate.tram.examples.performance.common.PerformanceTestEvent;
import io.eventuate.tram.examples.performance.common.TestAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Component
public class ProducerService {

    @Autowired
    private DomainEventPublisher domainEventPublisher;


    @Transactional
    public void produce(int n) {
        for (int i = 0; i < n; i++) {
                domainEventPublisher.publish(TestAggregate.class,
                        UUID.randomUUID().toString(),
                        Collections.singletonList(new PerformanceTestEvent(System.currentTimeMillis())));
        }
    }
}
