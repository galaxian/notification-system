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
import lombok.Getter;

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
}
