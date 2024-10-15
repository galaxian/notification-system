package com.example.notification.notification.domain;

import java.time.LocalDateTime;

import com.example.notification.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class ProductUserNotificationHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long userId;

	@Column
	private Integer restockRound;

	@Column
	private LocalDateTime sendDate;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	public ProductUserNotificationHistory(Long userId, Integer restockRound, Product product) {
		this.userId = userId;
		this.restockRound = restockRound;
		this.product = product;
		this.sendDate = LocalDateTime.now();
	}
}
