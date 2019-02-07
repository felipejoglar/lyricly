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

import com.fjoglar.lyricly.data.SongsRepository;
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
    public Completable execute(SongsRepository repository, Void parameter) {
        return Completable.fromAction(() -> {

            if (System.currentTimeMillis() - repository.getLastUpdatedTimeInMillis() < DAY_IN_MILLIS) {
                return;
            }

            List<Track> tracks = repository.fetchTopSongs(200);
            if (tracks.isEmpty()) {
                return;
            }

            repository.setLastUpdatedTimeInMillis();

            for (Track track : tracks) {
                Song song = repository.getTopSongByNapsterId(track.getId());

                if (song == null) {
                    String lyrics = repository.fetchSongLyrics(track.getArtistName(), track.getName());
                    if (lyrics != null) {
                        repository.saveSong(SongDataMapper.transform(track, tracks.indexOf(track), lyrics));
                    }
                } else {
                    repository.updateTopSongOrder(song.getId(), tracks.indexOf(track), new Date());
                }

                Song favoriteSong = repository.getFavoriteSongByNapsterId(track.getId());
                if (favoriteSong != null) {
                    repository.updateFavoriteSongByNapsterId(track.getId());
                }

                // TODO: check if song already is in the recent list?
            }

            repository.deleteOldTopSongs(new Date(repository.getLastUpdatedTimeInMillis()));
        });
    }
}

