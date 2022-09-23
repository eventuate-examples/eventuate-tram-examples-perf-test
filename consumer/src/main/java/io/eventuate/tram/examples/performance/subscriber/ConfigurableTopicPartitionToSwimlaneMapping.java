package io.eventuate.tram.examples.performance.subscriber;

import io.eventuate.messaging.kafka.consumer.TopicPartitionToSwimlaneMapping;
import org.apache.kafka.common.TopicPartition;

import java.util.Objects;

class ConfigurableTopicPartitionToSwimlaneMapping implements TopicPartitionToSwimlaneMapping {

    private int concurrency;

    public ConfigurableTopicPartitionToSwimlaneMapping(int concurrency) {
        this.concurrency = concurrency;
    }

    @Override
    public Integer toSwimlane(TopicPartition topicPartition, String messageKey) {
        return Math.abs(Objects.hash(topicPartition, messageKey)) % concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }
}
