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

package com.fjoglar.lyricly.song;

import androidx.annotation.Nullable;

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.model.Status;

import static com.fjoglar.lyricly.data.model.Status.DATA;
import static com.fjoglar.lyricly.data.model.Status.ERROR;

/**
 * SongsResponse holder provided to the UI
 */
class SongResponse {

    final Status status;

    @Nullable
    public final Song data;

    @Nullable
    public final Throwable error;

    private SongResponse(Status status, @Nullable Song data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    static SongResponse success(Song data) {
        return new SongResponse(DATA, data, null);
    }

    static SongResponse error(Throwable error) {
        return new SongResponse(ERROR, null, error);
    }
}