package com.icia.friend.api;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.icia.friend.MainActivity;
import com.icia.friend.OrderActivity;
import com.icia.friend.R;
import com.icia.friend.login.RegisterActivity;
import com.icia.friend.session.Session;
import com.icia.friend.vo.UserVO;

// 메시지 수신 및 알림
public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCMMessagingService";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "onNewToken : " + token);
        sendRegistrationToServer(token);
    }

    // 앱 실행 시 서버에 디바이스 토큰 값 넘겨주는 동작
    private void sendRegistrationToServer(String token) {
        // 디바이스 토큰이 생성되거나 재생성되면 동작
        System.out.println("FCMMessagingService - sendRegistrationToServer : " + token);

//        // 만약 세션에 저장되어있는 토큰 값이 같지 않으면
//        if(Session.getToken(this) != token) {
//            // 토큰 값 세션에 저장
//            Session.setToken(this, token);
//        }

        // 토큰 값 세션에 저장
        Session.setToken(this, token);
        String Token = Session.getToken(this);
        System.out.println("FCMMessagingService - sendRegistrationToServer :: getToken : " + Token);
    }

    // 받은 메시지에서 title, body 추출
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {
            } else {
                handleNow();
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    // 받은 title과 body로 디바이스 알림 전송
    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_notification_logo)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }

}
