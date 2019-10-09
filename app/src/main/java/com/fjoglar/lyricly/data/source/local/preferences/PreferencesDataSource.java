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

package com.fjoglar.lyricly.data.source.local.preferences;

public interface PreferencesDataSource {

    String LAST_UPDATED_TIME_KEY = "last_updated_time";
    String CURRENTLY_PLAYING_SONG_STATE_KEY = "currently_playing_song_state";

    /**
     * Gets the last time the top songs were updated.
     *
     * @return the time of the last update in millis.
     */
    long getLastUpdatedTimeInMillis();

    /**
     * Sets the last updated time the top songs where fetched.
     */
    void setLastUpdatedTimeInMillis();

    /**
     * @return the state of the currently playing song, true if playing
     */
    boolean getCurrentlyPlayingSongPlayBackState();

    /**
     *
     */
    void setCurrentlyPlayingSongPlayBackState(boolean currentState);
}
