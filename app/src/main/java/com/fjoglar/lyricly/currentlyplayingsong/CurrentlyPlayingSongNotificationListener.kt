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

import android.media.MediaMetadata.*
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.fjoglar.lyricly.util.Injection
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class CurrentlyPlayingSongNotificationListener : NotificationListenerService() {

    private val TAG = CurrentlyPlayingSongNotificationListener::class.java.simpleName

    private object PackageNames {
        const val SPOTIFY = "com.spotify.music"
        const val SPOTIFY_LITE = "com.spotify.lite"
        const val POWER_AMP = "com.maxmpz.audioplayer"
        const val GOOGLE_PLAY_MUSIC = "com.google.android.music"
    }

    private object NotificationCode {
        const val SPOTIFY_CODE = 1
        const val POWER_AMP_CODE = 2
        const val GOOGLE_PLAY_MUSIC_CODE = 3
        const val OTHER_NOTIFICATIONS_CODE = -1
    }

    private val disposables = CompositeDisposable()

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn?.let { matchNotificationCode(it) } != NotificationCode.OTHER_NOTIFICATIONS_CODE) {
            val mediaController = MediaController(
                applicationContext,
                sbn?.notification?.extras?.get("android.mediaSession") as MediaSession.Token
            )

            if (mediaController.playbackState?.state == PlaybackState.STATE_PLAYING) {
                val metadata = mediaController.metadata
                val track = metadata?.getString(METADATA_KEY_TITLE)
                val artist = metadata?.getString(METADATA_KEY_ARTIST)
                val album = metadata?.getString(METADATA_KEY_ALBUM)

                val songsDataSource = Injection.provideSongsRepository(applicationContext)
                val searchCurrentlyPlayingSongUseCase =
                    Injection.provideSearchCurrentlyPlayingSongUseCase()

                disposables.add(
                    searchCurrentlyPlayingSongUseCase
                        .execute(songsDataSource, "$artist $track $album")
                        .subscribeOn(SchedulerProvider.getInstance().io())
                        .observeOn(SchedulerProvider.getInstance().ui())
                        .subscribe({ song ->
                            Log.d(TAG, "Song fetched: ${song?.name} by ${song?.artistName}")
                        },
                            {
                                disposables.add(searchCurrentlyPlayingSongUseCase
                                    .execute(songsDataSource, "$artist $track")
                                    .subscribeOn(SchedulerProvider.getInstance().io())
                                    .observeOn(SchedulerProvider.getInstance().ui())
                                    .subscribe({ song ->
                                        Log.d(
                                            TAG,
                                            "Song fetched: ${song?.name} by ${song?.artistName}"
                                        )
                                    },
                                        {
                                            Log.e(TAG, it.message)
                                        })
                                )
                            })
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun matchNotificationCode(sbn: StatusBarNotification): Int {
        val packageName = sbn.packageName

        return if (packageName == PackageNames.SPOTIFY || packageName == PackageNames.SPOTIFY_LITE) {
            NotificationCode.SPOTIFY_CODE
        } else if (packageName == PackageNames.POWER_AMP) {
            NotificationCode.POWER_AMP_CODE
        } else if (packageName == PackageNames.GOOGLE_PLAY_MUSIC) {
            NotificationCode.GOOGLE_PLAY_MUSIC_CODE
        } else {
            NotificationCode.OTHER_NOTIFICATIONS_CODE
        }
    }
}