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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.fjoglar.lyricly.util.Injection

class CurrentlyPlayingSongReceiver : BroadcastReceiver() {

    private val TAG = CurrentlyPlayingSongReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras?.let { extras ->
            val songsDataSource = Injection.provideSongsRepository(context)

            val artistName = extras.get("artist")
            val trackName = extras.get("track")
            val albumName = extras.get("album")

            if (extras.containsKey("playing")) {
                songsDataSource.currentlyPlayingSongPlayBackState =
                    extras.getBoolean("playing", false)
                if (!songsDataSource.currentlyPlayingSongPlayBackState) {
                    // TODO: remove notification if shown
                    Toast.makeText(context, "STOP PLAYING", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            // For searching we will only require the track name
            if (trackName != null && songsDataSource.currentlyPlayingSongPlayBackState) {
                // TODO: search song lyrics
                Toast.makeText(context, "$artistName\n$trackName\n$albumName", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}