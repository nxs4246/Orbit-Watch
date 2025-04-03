package com.nxs.notificationservice.service;

import com.nxs.asteroidalert.event.AsteroidCollisionEvent;
import com.nxs.notificationservice.entity.Notification;
import com.nxs.notificationservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics="asteroid-alert", groupId = "notification-service")
    public void alertEvent(AsteroidCollisionEvent notificationEvent) {
        log.info("Received asteroid alert event: {}", notificationEvent);

        // create entity for notification
        final Notification notification = Notification.builder()
                .asteroidName(notificationEvent.getAsteroidName())
                .closeApproachDate(LocalDate.parse(notificationEvent.getCloseApproachDate()))
                .estimatedDiameterAvgMeters(notificationEvent.getEstimatedDiameterAvgMeters())
                .missDistanceKilometers(new BigDecimal(notificationEvent.getMissDistanceKilometers()))
                .emailSent(false)
                .build();

        // save notification
        final Notification savedNotification = notificationRepository.saveAndFlush(notification);
        log.info("Notification saved: {}", savedNotification);
    }

    @Scheduled(fixedRate = 10000)
    public void sendAlertEmail() {
        log.info("triggering scheduled job to send email alerts");

        emailService.sendAsteroidAlertEmail();
    }
}
