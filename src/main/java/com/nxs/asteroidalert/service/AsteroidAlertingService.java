package com.nxs.asteroidalert.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class AsteroidAlertingService {

    public void alert() {
        log.info("Alerting Service Called!");

        // from and to dates
        final LocalDate fromDate = LocalDate.now();
        final LocalDate endDate = LocalDate.now().plusDays(7);

        // call nasa api to get asteroid data

        // if there is any dangerous asteroid, send an alert

        // create an alert and put on kafka

    }
}
