package com.example.notification.notification.domain;

import com.example.notification.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class ProductNotificationHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Integer restockRound;

	@Enumerated(EnumType.STRING)
	@Column
	private NotificationStatus notificationStatus;

	@Column
	private Long lastSendUserId;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
}
