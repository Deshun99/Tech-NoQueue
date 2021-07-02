package com.technoqueue.notification

data class PushNotification(
    val data: NotificationData,
    val to: String
)