package com.example.notification.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notification.notification.service.RestockNotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class NotificationController {

	private final RestockNotificationService restockNotificationService;

	@PostMapping("/products/{productId}/notifications/re-stock")
	public ResponseEntity<Void> reStockNotification(@PathVariable("productId") Long productId) {
		restockNotificationService.notifyRestock(productId);
		return ResponseEntity.noContent().build();
	}
}
