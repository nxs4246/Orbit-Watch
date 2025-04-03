package com.nxs.asteroidalert.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsteroidCollisionEvent {
    private String asteroidName;
    private String closeApproachDate;
    private String missDistanceKilometers;
    private double estimatedDiameterAvgMeters;
}
