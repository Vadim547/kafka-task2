package com.example.kafkafinal.consumer;

import com.example.kafkafinal.dto.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    public Map<String, Vehicle> data = new HashMap<>();

    @KafkaListener(topics = "meow", groupId = "myGroup", containerFactory = "factory", concurrency = "2")
    public void listener(Vehicle vehicle) {
        LOGGER.info("received payload='{}'", vehicle.toString());
        calculateDistance(vehicle);

    }

    private void calculateDistance(Vehicle vehicle) {
        if (data.containsKey(vehicle.getId())) {
            Vehicle previous = data.get(vehicle.getId());
            double distance = calculateDistance(
                    previous.getLatitude(),
                    previous.getLongitude(),
                    vehicle.getLatitude(),
                    previous.getLongitude());

            vehicle.setDistance(vehicle.getDistance() + Double.valueOf(distance));
            data.put(vehicle.getId(), vehicle);
            LOGGER.info("received payload='{}'", vehicle.getDistance());
        } else
            data.put(vehicle.getId(), vehicle);
    }


    public double calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {
        double AVERAGE_RADIUS_OF_EARTH = 6371;

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (AVERAGE_RADIUS_OF_EARTH * c);

    }

}
