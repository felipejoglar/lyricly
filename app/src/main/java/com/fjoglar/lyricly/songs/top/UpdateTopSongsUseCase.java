/*
 * Copyright 2018 Felipe Joglar Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fjoglar.lyricly.songs.top;

import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.mapper.SongDataMapper;
import com.fjoglar.lyricly.data.source.remote.entity.Track;
import com.fjoglar.lyricly.util.usecases.CompletableUseCase;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;

public class UpdateTopSongsUseCase implements CompletableUseCase<Void> {

    private long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    @Override
    public Completable execute(SongsDataSource songsDataSource, Void parameter) {
        return Completable.fromAction(() -> {

            if (System.currentTimeMillis() - songsDataSource.getLastUpdatedTimeInMillis() < DAY_IN_MILLIS) {
                return;
            }

            List<Track> tracks = songsDataSource.fetchTopSongs(200);
            if (tracks.isEmpty()) {
                return;
            }

            songsDataSource.setLastUpdatedTimeInMillis();

            for (Track track : tracks) {
                Song song = songsDataSource.getTopSongByNapsterId(track.getId());

                if (song == null) {
                    String lyrics = songsDataSource.fetchSongLyrics(track.getArtistName(), track.getName());
                    if (lyrics != null && !lyrics.isEmpty()) {
                        songsDataSource.saveSong(SongDataMapper.transform(track, tracks.indexOf(track), lyrics));
                    }
                } else {
                    songsDataSource.updateTopSongOrder(song.getId(), tracks.indexOf(track), new Date());
                }

                Song favoriteSong = songsDataSource.getFavoriteSongByNapsterId(track.getId());
                if (favoriteSong != null) {
                    songsDataSource.updateFavoriteSongByNapsterId(track.getId());
                }
            }

            songsDataSource.deleteOldTopSongs(new Date(songsDataSource.getLastUpdatedTimeInMillis()));
        });
    }
}

