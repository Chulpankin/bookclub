package com.itis.bookclub.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.itis.bookclub.domain.model.PushDomainModel
import com.itis.bookclub.domain.usecase.IsUserAuthorizedUseCase
import com.itis.bookclub.domain.usecase.SavePushUseCase
import com.itis.bookclub.presentation.utils.NavigationController
import com.itis.bookclub.util.appComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookClubFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var savePushUseCase: SavePushUseCase
    @Inject
    lateinit var isUserAuthorizedUseCase: IsUserAuthorizedUseCase
    @Inject
    lateinit var navigationController: NavigationController
    @Inject
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override fun onCreate() {
        super.onCreate()

        applicationContext.appComponent.inject(this)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM", "Message received: ${message.data}")
        val data = message.data

        when (val category = data["category"].orEmpty()) {
            "alert", "" -> showNotification(data["title"].orEmpty(), data["message"].orEmpty())
            "background" -> saveToDatabase(category, data["payload"] ?: "")
            "feature" -> handleFeaturePush(data["payload"])
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("BookClubFirebaseMessagingService", "new token: $token")
    }

    private fun showNotification(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (
                checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val channelId = "push_channel"
        val channel = NotificationChannel(
            channelId,
            "Push Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        NotificationManagerCompat
            .from(this)
            .notify(System.currentTimeMillis().toInt(), notification)
    }


    private fun saveToDatabase(category: String, payload: String) {
        val push = PushDomainModel(
            category = category,
            payload = payload,
            timestamp = System.currentTimeMillis()
        )
        CoroutineScope(coroutineDispatcher).launch {
            savePushUseCase.invoke(push)
        }
    }

    private fun handleFeaturePush(payload: String?) {

        if (!isUserAuthorizedUseCase.invoke()) {
            navigationController.showAuthError()
            return
        }

        navigationController.navigateToDetails(payload.orEmpty())
    }
}