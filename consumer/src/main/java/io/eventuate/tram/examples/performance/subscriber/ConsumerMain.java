package io.eventuate.tram.examples.performance.subscriber;

import io.eventuate.common.spring.jdbc.EventuateCommonJdbcOperationsConfiguration;
import io.eventuate.tram.events.subscriber.*;
import io.eventuate.tram.examples.performance.common.PerformanceTestEvent;
import io.eventuate.tram.examples.performance.common.TestAggregate;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({EventuateCommonJdbcOperationsConfiguration.class}) // Not right
public class ConsumerMain {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerMain.class);

    static class TramEventTestEventConsumer {

        private final Counter counter;

        public TramEventTestEventConsumer(MeterRegistry meterRegistry) {
            this.counter = meterRegistry.counter("consumer.events.consumed");
        }

        public DomainEventHandlers domainEventHandlers() {
            return DomainEventHandlersBuilder
                    .forAggregateType(TestAggregate.class.getName())
                    .onEvent(PerformanceTestEvent.class, this::handlePerformanceTestEvent)
                    .build();
        }

        public void handlePerformanceTestEvent(DomainEventEnvelope<PerformanceTestEvent> event) {
            // System.out.println("Received: " + event);
            logger.trace("start");
            counter.increment();
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            logger.trace("end");
        }
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                       TramEventTestEventConsumer target) {
        return domainEventDispatcherFactory.make("eventDispatcher", target.domainEventHandlers());
    }

    @Bean
    public ConfigurableTopicPartitionToSwimlaneMapping topicPartitionToSwimlaneMapping(@Value("${consumer.concurrency}") int consumerConcurrency) {
        return new ConfigurableTopicPartitionToSwimlaneMapping(consumerConcurrency);
    }

    @Bean
    public TramEventTestEventConsumer tramEventTestTarget(MeterRegistry meterRegistry) {
        return new TramEventTestEventConsumer(meterRegistry);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class, args);
    }
}
