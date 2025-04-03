package com.nxs.asteroidalert.service;

import com.nxs.asteroidalert.client.NasaClient;
import com.nxs.asteroidalert.dto.Asteroid;
import com.nxs.asteroidalert.event.AsteroidCollisionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AsteroidAlertingService {

    private final NasaClient nasaClient;
    private final KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate;

    @Autowired
    public AsteroidAlertingService(NasaClient nasaClient, KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate) {
        this.nasaClient = nasaClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void alert() {
        log.info("Alerting Service Called!");

        // from and to dates
        final LocalDate fromDate = LocalDate.now();
        final LocalDate endDate = LocalDate.now().plusDays(7);

        // call nasa api to get asteroid data
        log.info("getting asteroid list for dates: {} to {}", fromDate, endDate);
        final List<Asteroid> asteroidList = nasaClient.getNeoAsteroids(fromDate, endDate);

        // if there is any dangerous asteroid, send an alert
        final List<Asteroid> dangerousAsteroids = asteroidList.stream()
                .filter(Asteroid::isPotentiallyHazardous)
                .toList();
        log.info("Found {} hazardous asteroids", dangerousAsteroids.size());

        // create an alert and put on kafka
        final List<AsteroidCollisionEvent> asteroidCollisionEventList =
                createEventListOfDangerousAsteroids(dangerousAsteroids);

        log.info("Sending {} asteroid alerts to Kafka", asteroidCollisionEventList.size());
        asteroidCollisionEventList.forEach(event -> {
            kafkaTemplate.send("asteroid-alert", event);
            log.info("Asteroid alert sent to Kafka topic: {}", event);
        });
    }

    private List<AsteroidCollisionEvent> createEventListOfDangerousAsteroids(final List<Asteroid> dangerousAsteroids) {
        return dangerousAsteroids.stream()
                .map(asteroid ->{
                    if (asteroid.isPotentiallyHazardous()) {
                        return AsteroidCollisionEvent.builder()
                                .asteroidName(asteroid.getName())
                                .closeApproachDate(asteroid.getCloseApproachData().getFirst().getCloseApproachDate().toString())
                                .missDistanceKilometers(asteroid.getCloseApproachData().getFirst().getMissDistance().getKilometers())
                                .estimatedDiameterAvgMeters((asteroid.getEstimatedDiameter().getMeters().getMinDiameter() +
                                        asteroid.getEstimatedDiameter().getMeters().getMaxDiameter()) / 2)
                                .build();
                    }
                    return null;
                })
                .toList();
    }
}
