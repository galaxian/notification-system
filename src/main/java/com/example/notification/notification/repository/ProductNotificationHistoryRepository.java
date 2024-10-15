package com.example.notification.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.notification.notification.domain.ProductNotificationHistory;

@Repository
public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {
}
