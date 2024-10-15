package com.example.notification.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Integer restockRound;

	@Column
	private Integer stock;

	public void increaseRestockRound() {
		restockRound++;
	}

	public void validateStockCnt() {
		if (stock <= 0) {
			throw new RuntimeException("상품 재고가 없습니다.");
		}
	}

	public boolean isOutOfStock() {
		return stock <= 0;
	}
}
