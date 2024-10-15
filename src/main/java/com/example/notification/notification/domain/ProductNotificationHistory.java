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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public ProductNotificationHistory(Integer restockRound, Product product) {
		this.restockRound = restockRound;
		this.notificationStatus = NotificationStatus.IN_PROGRESS;
		this.product = product;
	}

	public void updateLastSendUserAndStatus(Long userId) {
		this.lastSendUserId = userId;
		this.notificationStatus = NotificationStatus.CANCELED_BY_SOLD_OUT;
	}
}
