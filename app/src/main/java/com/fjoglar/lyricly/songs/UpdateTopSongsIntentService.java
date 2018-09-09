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

package com.fjoglar.lyricly.songs;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.mapper.SongDataMapper;
import com.fjoglar.lyricly.data.source.remote.entity.Track;
import com.fjoglar.lyricly.util.Injection;
import com.google.firebase.perf.metrics.AddTrace;

import java.util.List;

public class UpdateTopSongsIntentService extends IntentService {

    public UpdateTopSongsIntentService() {
        super("UpdateTopSongsIntentService");
    }

    @Override
    @AddTrace(name = "updateTopSongsTrace")
    protected void onHandleIntent(@Nullable Intent intent) {
        SongsRepository repository = Injection.provideSongsRepository(getApplicationContext());

        // We have set the limit to 50 eventually.
        // TODO: in the future implement an infinite scroll with paging.
        List<Track> tracks = repository.fetchTopSongs(50);

        if (!tracks.isEmpty()) {
            repository.deleteTopSongs();
        }

        for (Track track : tracks) {
            String lyrics = repository.fetchSongLyrics(track.getArtistName(), track.getName());
            if (lyrics != null) {
                SongDatabase.getInstance(this).songDao()
                        .insert(SongDataMapper.transform(track, lyrics));
            }
        }
    }
}

