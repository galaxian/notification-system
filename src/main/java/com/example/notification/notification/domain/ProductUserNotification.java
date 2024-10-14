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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@Entity
public class ProductUserNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long userId;

	@Column
	private boolean isActive = true;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
