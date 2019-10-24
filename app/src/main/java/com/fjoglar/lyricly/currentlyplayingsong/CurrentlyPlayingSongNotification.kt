/*
 * Copyright 2019 Felipe Joglar Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fjoglar.lyricly.currentlyplayingsong

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fjoglar.lyricly.R
import com.fjoglar.lyricly.data.model.Song
import com.fjoglar.lyricly.song.SongActivity
import com.fjoglar.lyricly.util.GlideHelper

const val CHANNEL_ID = "Lyricly currently playing song channel"
const val NOTIFICATION_ID = 8888

fun launchCurrentlyPlayingSongNotification(applicationContext: Context, song: Song) {
    createNotificationChannel(applicationContext)
    GlideHelper.loadBitmapFromNetwork(
        applicationContext,
        applicationContext.getString(
            R.string.album_image_url,
            song.albumId,
            GlideHelper.IMAGE_SIZE_BIG
        ),
        16
    ) {
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, buildNotification(applicationContext, song, it).build())
        }
    }
}

private fun buildNotification(
    applicationContext: Context,
    song: Song,
    largeIcon: Bitmap
): NotificationCompat.Builder =
    with(NotificationCompat.Builder(applicationContext, CHANNEL_ID)) {
        priority = NotificationCompat.PRIORITY_DEFAULT
        color = applicationContext.resources.getColor(R.color.color_primary)
        setSmallIcon(R.drawable.currently_playing_ic_notification)
        setContentTitle(song.name)
        setContentText(
            applicationContext.getString(
                R.string.currently_playing_song_notification_text,
                song.artistName
            )
        )
        setLargeIcon(largeIcon)
        setContentIntent(getPendingIntent(applicationContext, song))
        setAutoCancel(true)
    }

private fun createNotificationChannel(applicationContext: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = applicationContext.getString(R.string.currently_playing_song_channel_name)
        val descriptionText =
            applicationContext.getString(R.string.currently_playing_song_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun getPendingIntent(applicationContext: Context, song: Song): PendingIntent {
    val intent = Intent(applicationContext, SongActivity::class.java).apply {
        putExtra(SongActivity.EXTRA_SONG_ID, song.id)
        putExtra(
            SongActivity.EXTRA_IS_FAVORITE_FLOW,
            false
        )
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    return PendingIntent.getActivity(applicationContext, song.id, intent, FLAG_UPDATE_CURRENT)
}
