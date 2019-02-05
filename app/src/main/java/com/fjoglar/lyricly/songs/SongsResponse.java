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

import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.model.Status;

import java.util.List;

import androidx.annotation.Nullable;

import static com.fjoglar.lyricly.data.model.Status.DATA;
import static com.fjoglar.lyricly.data.model.Status.ERROR;
import static com.fjoglar.lyricly.data.model.Status.LOADING;
import static com.fjoglar.lyricly.data.model.Status.SUCCESS;

/**
 * SongsResponse holder provided to the UI
 */
public class SongsResponse {

    public final Status status;

    @Nullable
    public final List<Song> data;

    @Nullable
    public final Throwable error;

    private SongsResponse(Status status, @Nullable List<Song> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static SongsResponse loading() {
        return new SongsResponse(LOADING, null, null);
    }

    public static SongsResponse success() {
        return new SongsResponse(SUCCESS, null, null);
    }

    public static SongsResponse load(List<Song> data) {
        return new SongsResponse(DATA, data, null);
    }

    public static SongsResponse error(Throwable error) {
        return new SongsResponse(ERROR, null, error);
    }
}