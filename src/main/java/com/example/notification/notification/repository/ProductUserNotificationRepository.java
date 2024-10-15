package com.example.notification.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.notification.notification.domain.ProductUserNotification;

@Repository
public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {

	@Query("select pun from ProductUserNotification pun "
		+ "where pun.product.id =:productId "
		+ "and pun.isActive =:true "
		+ "order by pun.createdAt")
	List<ProductUserNotification> findAllByProductIdAndIsActiveByCreatedAt(Long productId);
}
