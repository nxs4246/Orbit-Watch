package com.nxs.asteroidalert.service;

import com.nxs.asteroidalert.client.NasaClient;
import com.nxs.asteroidalert.dto.Asteroid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AsteroidAlertingService {

    private final NasaClient nasaClient;


    @Autowired
    public AsteroidAlertingService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
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

    }
}
