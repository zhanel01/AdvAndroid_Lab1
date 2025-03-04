package com.example.lab1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat

class Mp3ForegroundService : Service() {

    companion object {
        const val ACTION_START = "action_start"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_STOP = "action_stop"
        private const val CHANNEL_ID = "MusicServiceChannel"
        private const val NOTIFICATION_ID = 1
    }

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        val assetFileDescriptor = assets.openFd("Adil - Улыбайся это Сунна.mp3")
        mediaPlayer?.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )
        mediaPlayer?.prepare()
        mediaPlayer?.isLooping = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            ACTION_START -> {
                if (mediaPlayer != null && !isPlaying) {
                    mediaPlayer?.start()
                    isPlaying = true
                    startForegroundService()
                }
            }
            ACTION_PAUSE -> {
                if (isPlaying) {
                    mediaPlayer?.pause()
                    isPlaying = false
                    startForegroundService()
                }
            }
            ACTION_STOP -> {
                stopForegroundService()
            }
        }
        return START_NOT_STICKY
    }

    private fun startForegroundService() {
        createNotificationChannel()
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(): Notification {
        val mainIntent = Intent(this, MainActivity::class.java)
        val mainPendingIntent = PendingIntent.getActivity(
            this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val playIntent = Intent(this, Mp3ForegroundService::class.java).apply {
            action = ACTION_START
        }
        val playPendingIntent = PendingIntent.getService(
            this, 1, playIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pauseIntent = Intent(this, Mp3ForegroundService::class.java).apply {
            action = ACTION_PAUSE
        }
        val pausePendingIntent = PendingIntent.getService(
            this, 2, pauseIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = Intent(this, Mp3ForegroundService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 3, stopIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Mp3 Service")
            .setContentText(if (isPlaying) "Playing" else "Paused")
            .setSmallIcon(R.drawable.music_icon)
            .setContentIntent(mainPendingIntent)
            .addAction(
                NotificationCompat.Action(
                    0,
                    if (isPlaying) "Pause" else "Play",
                    if (isPlaying) pausePendingIntent else playPendingIntent
                )
            )
            .addAction(
                NotificationCompat.Action(
                    0,
                    "Stop",
                    stopPendingIntent
                )
            )
            .setOngoing(isPlaying)
            .build()
    }

    private fun stopForegroundService() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Mp3 Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }
}