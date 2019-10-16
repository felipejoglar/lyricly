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

package com.fjoglar.lyricly.data.source.remote;

import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.List;

public interface SongsRemoteDataSource {

    /**
     * Gets a list of songs from the remote data source.
     *
     * @param limit number of songs to be fetched.
     * @return the list of top songs from the data source.
     */
    List<Track> fetchTopSongs(int limit);

    /**
     * Search a song in the remote data source.
     *
     * @param query search query.
     * @return a single track.
     */
    Track searchCurrentlyPlayingSong(String query);

    /**
     * Gets the lyrics of a song from the remote data source.
     *
     * @param artist the artist of the song
     * @param title  the title of the song
     * @return the user from the data source.
     */
    String fetchSongLyrics(String artist, String title);
}
