package com.example.kafkafinal.controller;

import com.example.kafkafinal.consumer.KafkaConsumer;
import com.example.kafkafinal.dto.Vehicle;
import com.example.kafkafinal.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @Autowired
    private KafkaConsumer kafkaConsumer;
    @Autowired
    private KafkaProducer kafkaProducer;


    @PostMapping("/")
    public String vehicleLotion(@RequestBody Vehicle vehicle) {
        kafkaProducer.send("meow", vehicle);
        return "Okay";
    }

    @GetMapping("/{id}")
    public Vehicle getDistance(@PathVariable String id) {
      return  kafkaConsumer.data.get(id);
    }
}
