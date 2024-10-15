package com.example.notification.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.notification.notification.domain.ProductNotificationHistory;
import com.example.notification.notification.domain.ProductUserNotification;
import com.example.notification.notification.domain.ProductUserNotificationHistory;
import com.example.notification.notification.repository.ProductNotificationHistoryRepository;
import com.example.notification.notification.repository.ProductUserNotificationHistoryRepository;
import com.example.notification.notification.repository.ProductUserNotificationRepository;
import com.example.notification.product.domain.Product;
import com.example.notification.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class RestockNotificationService {

	private static final int NOTIFICATION_CNT = 500;

	private final ProductRepository productRepository;
	private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
	private final ProductUserNotificationRepository productUserNotificationRepository;
	private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;

	public void notifyRestock(Long productId) {
		// 상품 생성 및 재입고 회차 증가
		Product product = findProductById(productId);
		product.increaseRestockRound();
		int restockRound = product.getRestockRound();

		// 재입고 알림 상태 초기화
		ProductNotificationHistory notificationHistory = createAndSaveNotificationHistory(restockRound, product);

		// 매진 유무 확인
		product.validateStockCnt();

		List<ProductUserNotification> notificationList = productUserNotificationRepository.findAllByProductIdAndIsActiveByCreatedAt(
			productId);

		// 알림 전송 및 예외 처리
		Long lastSentUserId = sendNotifications(notificationList, restockRound, product, notificationHistory);

		// 알림 발송 완료 처리
		notificationHistory.updateLastSendUserAndStatus(lastSentUserId);
		productNotificationHistoryRepository.save(notificationHistory);
	}

	private Product findProductById(Long productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
	}

	private ProductNotificationHistory createAndSaveNotificationHistory(int restockRound, Product product) {
		ProductNotificationHistory notificationHistory = new ProductNotificationHistory(restockRound, product);
		productNotificationHistoryRepository.save(notificationHistory);
		return notificationHistory;
	}

	private Long sendNotifications(List<ProductUserNotification> notificationList, int restockRound,
		Product product, ProductNotificationHistory notificationHistory) {
		Long lastSentUserId = null;

		int totalNotificationCnt = notificationList.size();
		int cnt = (int)Math.ceil((double)totalNotificationCnt / NOTIFICATION_CNT);

		//  500개씩 끊어서 1사이클 알림
		for (int i = 0; i < cnt; i++) {
			List<ProductUserNotification> totalNotificationListByOneTime = notificationList.subList(
				i * NOTIFICATION_CNT, Math.min((i + 1) * NOTIFICATION_CNT, totalNotificationCnt)
			);

			lastSentUserId = processNotificationByOneTime(totalNotificationListByOneTime, restockRound, product,
				notificationHistory, lastSentUserId);

			// 요구사항에 따른 1초 대기
			// todo ?? 500개 알림이 1초를 넘을 경우는?
			pauseBetweenTime();
		}

		updateNotificationHistory(notificationHistory, lastSentUserId);
		return lastSentUserId;
	}

	private static void pauseBetweenTime() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private Long processNotificationByOneTime(List<ProductUserNotification> totalNotificationListByOneTime,
		int restockRound, Product product, ProductNotificationHistory notificationHistory, Long lastSentUserId) {

		for (ProductUserNotification notification : totalNotificationListByOneTime) {
			try {
				sendNotificationToUser(notification, restockRound, product);
				lastSentUserId = notification.getUserId();

				if (product.isOutOfStock()) {
					updateNotificationHistory(notificationHistory, lastSentUserId);
					return lastSentUserId;
				}
			} catch (Exception e) {
				handleNotificationException(notificationHistory, lastSentUserId, e);
			}
		}
		return lastSentUserId;
	}

	private void updateNotificationHistory(ProductNotificationHistory notificationHistory, Long lastSentUserId) {
		notificationHistory.updateLastSendUserAndStatus(lastSentUserId);
		productNotificationHistoryRepository.save(notificationHistory);
	}

	private void handleNotificationException(ProductNotificationHistory notificationHistory, Long lastSentUserId, Exception e) {
		updateNotificationHistory(notificationHistory, lastSentUserId);
		throw new RuntimeException("알림 전송 중 오류 발생", e);
	}


	private void sendNotificationToUser(ProductUserNotification notification, int restockRound, Product product) {
		// todo: 알림 전송 로직

		ProductUserNotificationHistory productUserNotificationHistory = new ProductUserNotificationHistory(
			notification.getUserId(), restockRound, product);

		productUserNotificationHistoryRepository.save(productUserNotificationHistory);
	}
}
