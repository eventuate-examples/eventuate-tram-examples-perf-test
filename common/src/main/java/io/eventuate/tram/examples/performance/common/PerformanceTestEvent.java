package io.eventuate.tram.examples.performance.common;

import io.eventuate.tram.events.common.DomainEvent;

public class PerformanceTestEvent implements DomainEvent {
    private long publishTime;

    public PerformanceTestEvent() {
    }
    public PerformanceTestEvent(long publishTime) {

        this.publishTime = publishTime;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}
