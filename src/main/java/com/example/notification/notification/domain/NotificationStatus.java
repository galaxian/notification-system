package com.example.notification.notification.domain;

import lombok.Getter;

@Getter
public enum NotificationStatus {
	IN_PROGRESS,
	COMPLETED,
	CANCELED_BY_SOLD_OUT,
	CANCELED_BY_ERROR
}
