package com.nxs.asteroidalert.client;

import com.nxs.asteroidalert.dto.Asteroid;
import com.nxs.asteroidalert.dto.NasaNeoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NasaClient {

    @Value("${nasa.neo.api.url}")
    public String nasaNeoApiUrl;

    @Value("${nasa.neo.api.key}")
    public String nasaApiKey;

    public List<Asteroid> getNeoAsteroids(final LocalDate fromDate, final LocalDate endDate) {
        final RestTemplate restTemplate = new RestTemplate() ;

        final NasaNeoResponse nasaNeoResponse =
                restTemplate.getForObject(getUrl(fromDate, endDate), NasaNeoResponse.class);

        List<Asteroid> asteroidList = new ArrayList<>();
        if(nasaNeoResponse != null) {
            asteroidList.addAll(nasaNeoResponse.getNearEarthObjects().values().stream().flatMap(List::stream).toList());
        }

        return asteroidList;
    }

    public String getUrl(final LocalDate fromDate, final LocalDate endDate) {
        String apiUrl = UriComponentsBuilder.fromHttpUrl(nasaNeoApiUrl)
                .queryParam("start_date", fromDate)
                .queryParam("end_date", endDate)
                .queryParam("api_key", nasaApiKey)
                .toUriString();
        return apiUrl;
    }
}
