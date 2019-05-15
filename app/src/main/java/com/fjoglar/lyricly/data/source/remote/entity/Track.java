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

package com.fjoglar.lyricly.data.source.remote.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("playbackSeconds")
    @Expose
    private int playbackSeconds;
    @SerializedName("isExplicit")
    @Expose
    private boolean isExplicit;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("albumId")
    @Expose
    private String albumId;

    public String getId() {
        return id;
    }

    public int getPlaybackSeconds() {
        return playbackSeconds;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumId() {
        return albumId;
    }
}
