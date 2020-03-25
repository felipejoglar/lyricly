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
package com.fjoglar.lyricly.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Immutable model class for a Song.
 */
@Entity(tableName = "songs")
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "source_id") val sourceId: String,
    @ColumnInfo(name = "playback_seconds") val playbackSeconds: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "album_name") val albumName: String,
    @ColumnInfo(name = "lyrics") val lyrics: String,
    @ColumnInfo(name = "is_explicit") val isExplicit: Boolean,
    @ColumnInfo(name = "album_cover_url") val albumCoverUrl: String,
    @ColumnInfo(name = "thumbnail_album_cover_url") val thumbnailAlbumCoverUrl: String,
    @ColumnInfo(name = "is_top") val isTop: Boolean,
    @ColumnInfo(name = "top_order") val topOrder: Int,
    @ColumnInfo(name = "is_recent") val isRecent: Boolean,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "updated_at") val updatedAt: Date
)