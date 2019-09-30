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

package com.fjoglar.lyricly.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

/**
 * Immutable model class for a Song.
 */
@Entity(tableName = "songs")
public class Song {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "source_id")
    private String sourceId;

    @ColumnInfo(name = "playback_seconds")
    private int playbackSeconds;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "artist_name")
    private String artistName;

    @ColumnInfo(name = "album_id")
    private String albumId;

    @ColumnInfo(name = "album_name")
    private String albumName;

    @ColumnInfo(name = "lyrics")
    private String lyrics;

    @ColumnInfo(name = "top")
    private boolean top;

    @ColumnInfo(name = "recent")
    private boolean recent;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @ColumnInfo(name = "top_order")
    private int topOrder;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @Ignore
    public Song() {
    }

    /**
     * @param sourceId        Song ID in Napster API DB
     * @param playbackSeconds Song length in seconds
     * @param name            Name of the song
     * @param artistName      Artist name
     * @param albumId         AlbumID in Napster API DB
     * @param albumName       Name of the album
     * @param lyrics          Lyrics for the song
     * @param top             The song is in the top list
     * @param recent          The song is in the recent list
     * @param favorite        The song is in the favorite list
     * @param topOrder        The order of the song in the top list, if applies.
     * @param createdAt       Date when the song was inserted in the DB
     */
    public Song(String sourceId,
                int playbackSeconds,
                String name,
                String artistName,
                String albumId,
                String albumName,
                String lyrics,
                boolean top,
                boolean recent,
                boolean favorite,
                int topOrder,
                Date createdAt) {
        this.sourceId = sourceId;
        this.playbackSeconds = playbackSeconds;
        this.name = name;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.lyrics = lyrics;
        this.top = top;
        this.recent = recent;
        this.favorite = favorite;
        this.topOrder = topOrder;
        this.createdAt = createdAt;
    }

    /**
     * Creates a song in the favorites list.
     *
     * @param song the song to be added.
     */
    public Song createFavoriteSong(Song song) {
        Song favoriteSong = new Song();
        favoriteSong.sourceId = song.getSourceId();
        favoriteSong.playbackSeconds = song.getPlaybackSeconds();
        favoriteSong.name = song.getName();
        favoriteSong.artistName = song.getArtistName();
        favoriteSong.albumId = song.getAlbumId();
        favoriteSong.albumName = song.getAlbumName();
        favoriteSong.lyrics = song.getLyrics();
        favoriteSong.top = false;
        favoriteSong.recent = false;
        favoriteSong.favorite = true;
        favoriteSong.topOrder = song.getTopOrder();
        favoriteSong.createdAt = new Date();
        return favoriteSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getPlaybackSeconds() {
        return playbackSeconds;
    }

    public void setPlaybackSeconds(int playbackSeconds) {
        this.playbackSeconds = playbackSeconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isRecent() {
        return recent;
    }

    public void setRecent(boolean recent) {
        this.recent = recent;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getTopOrder() {
        return topOrder;
    }

    public void setTopOrder(int topOrder) {
        this.topOrder = topOrder;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                playbackSeconds == song.playbackSeconds &&
                top == song.top &&
                recent == song.recent &&
                favorite == song.favorite &&
                topOrder == song.topOrder &&
                Objects.equals(sourceId, song.sourceId) &&
                Objects.equals(name, song.name) &&
                Objects.equals(artistName, song.artistName) &&
                Objects.equals(albumId, song.albumId) &&
                Objects.equals(albumName, song.albumName) &&
                Objects.equals(lyrics, song.lyrics) &&
                Objects.equals(createdAt, song.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                sourceId,
                playbackSeconds,
                name,
                artistName,
                albumId,
                albumName,
                lyrics,
                top,
                recent,
                favorite,
                topOrder,
                createdAt);
    }

    @NonNull
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", sourceId='" + sourceId + '\'' +
                ", playbackSeconds=" + playbackSeconds +
                ", name='" + name + '\'' +
                ", artistName='" + artistName + '\'' +
                ", albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", top=" + top +
                ", recent=" + recent +
                ", favorite=" + favorite +
                ", topOrder=" + topOrder +
                ", createdAt=" + createdAt +
                '}';
    }
}
