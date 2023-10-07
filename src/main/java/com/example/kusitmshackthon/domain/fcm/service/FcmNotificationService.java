package com.example.kusitmshackthon.domain.fcm.service;

import com.example.kusitmshackthon.domain.fcm.dto.request.FcmNotificationRequestDto;
import com.example.kusitmshackthon.domain.fcm.entity.FcmToken;
import com.example.kusitmshackthon.exception.badrequest.FcmInvalidRequestException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmNotificationService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationByToken(FcmNotificationRequestDto fcmNotificationRequestDto, Long meetingId) {
        for (FcmToken fcmToken : fcmNotificationRequestDto.getTargetTokenList()) {
            Notification notification = createNotification(fcmNotificationRequestDto.getTitle(), fcmNotificationRequestDto.getBody());
            Message message = createMessage(notification, fcmToken, meetingId);
            sendNotification(message);
        }
    }

    private Notification createNotification(String title, String body) {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                // .setImage(fcmNotificationRequestDto.getImg())
                .build();
    }

    private Message createMessage(Notification notification, FcmToken fcmToken, Long meetingId) {
        return Message.builder()
                .setToken(fcmToken.getFcmToken())
                .putData("meetingId", meetingId.toString())
                .setNotification(notification)
                .build();
    }

    private void sendNotification(Message message) {
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send Notification", e);
            throw new FcmInvalidRequestException();
        }
    }
}
