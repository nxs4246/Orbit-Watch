package com.nxs.asteroidalert.controller;

import com.nxs.asteroidalert.service.AsteroidAlertingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asteroid-alert")
public class AsteroidAlertingController {

    private final AsteroidAlertingService asteroidAlertingService;

    @Autowired
    public AsteroidAlertingController(AsteroidAlertingService asteroidAlertingService) {
        this.asteroidAlertingService = asteroidAlertingService;
    }
    @PostMapping("/alert")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void alert() {
        asteroidAlertingService.alert();
    }
}
