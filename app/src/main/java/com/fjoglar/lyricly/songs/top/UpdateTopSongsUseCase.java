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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;

public class UpdateTopSongsUseCase implements CompletableUseCase<Void> {

    @Override
    public Completable execute(SongsRepository repository, Void parameter) {
        return Completable.fromAction(() -> {
            List<Song> songs = new ArrayList<>();

            // TODO: in the future implement an infinite scroll with paging.
            List<Track> tracks = repository.fetchTopSongs(100);

            if (!tracks.isEmpty()) {
                repository.deleteTopSongs();
            }

            for (Track track : tracks) {
                String lyrics = repository.fetchSongLyrics(track.getArtistName(), track.getName());
                if (lyrics != null) {
                    songs.add(SongDataMapper.transform(track, lyrics));
                }

                // Save the songs in small chunks to better UX
                if (songs.size() % 15 == 0) {
                    repository.saveSongs(songs);
                    songs.clear();
                }
            }

            repository.saveSongs(songs);
        });
    }
}

