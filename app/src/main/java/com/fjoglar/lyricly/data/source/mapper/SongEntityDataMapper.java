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

package com.fjoglar.lyricly.data.source.mapper;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;
import com.fjoglar.lyricly.data.source.remote.entity.OvhLyricsApiResponse;
import com.fjoglar.lyricly.data.source.remote.entity.Track;

import java.util.Date;

/**
 * Mapper class used to transform {@link Track} and {@link OvhLyricsApiResponse} to {@link Song}.
 */
public class SongEntityDataMapper {

    public SongEntityDataMapper() {
    }

    public static TopSongEntity transform(Track track, String lyrics) {
        TopSongEntity song = null;

        if (track != null && lyrics != null) {
            song = new TopSongEntity(track.getId(),
                    track.getIndex(),
                    track.getPlaybackSeconds(),
                    track.getName(),
                    track.getArtistId(),
                    track.getArtistName(),
                    track.getAlbumId(),
                    track.getAlbumName(),
                    lyrics,
                    new Date());
        }

        return song;
    }
}
