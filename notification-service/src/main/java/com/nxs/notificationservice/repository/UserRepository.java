package com.nxs.notificationservice.repository;

import com.nxs.notificationservice.entity.Notification;
import com.nxs.notificationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u.email FROM User u WHERE u.notificationEnabled = true")
    List<String> findAllEmailsAndNotificationEnabled();
}
