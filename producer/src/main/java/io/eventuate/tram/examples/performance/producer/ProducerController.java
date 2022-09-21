package io.eventuate.tram.examples.performance.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping("/producer")
    public void produce() {
        int n = 1000;
        producerService.produce(n);
    }

}
