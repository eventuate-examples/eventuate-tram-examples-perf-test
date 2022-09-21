package io.eventuate.tram.examples.performance.subscriber;

import io.eventuate.common.spring.jdbc.EventuateCommonJdbcOperationsConfiguration;
import io.eventuate.messaging.kafka.consumer.TopicPartitionToSwimlaneMapping;
import io.eventuate.tram.events.subscriber.*;
import io.eventuate.tram.examples.performance.common.PerformanceTestEvent;
import io.eventuate.tram.examples.performance.common.TestAggregate;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({EventuateCommonJdbcOperationsConfiguration.class}) // Not right
public class ConsumerMain {


    static class TramEventTestEventConsumer {

        private final MeterRegistry meterRegistry;
        private final Counter counter;

        public TramEventTestEventConsumer(MeterRegistry meterRegistry) {
            this.meterRegistry = meterRegistry;
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
            counter.increment();
        }
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory,
                                                       TramEventTestEventConsumer target) {
        return domainEventDispatcherFactory.make("eventDispatcher", target.domainEventHandlers());
    }

    @Bean
    public TopicPartitionToSwimlaneMapping topicPartitionToSwimlaneMapping() {
        return (topicPartition, messageKey) -> messageKey.hashCode() % 200;
    }

    @Bean
    public TramEventTestEventConsumer tramEventTestTarget(MeterRegistry meterRegistry) {
        return new TramEventTestEventConsumer(meterRegistry);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain.class, args);
    }
}
