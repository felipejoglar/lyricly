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

package com.fjoglar.lyricly.data.source.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.fjoglar.lyricly.data.model.Song;

import java.util.Date;
import java.util.Objects;

/**
 * Model class for a user recently played song.
 */
@Entity(tableName = "recent_songs")
public class RecentlyPlayedSongEntity implements Song {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "napster_id")
    private String napsterId;

    @ColumnInfo(name = "album_index")
    private int albumIndex;

    @ColumnInfo(name = "playback_seconds")
    private int playbackSeconds;

    private String name;

    @ColumnInfo(name = "artist_id")
    private String artistId;

    @ColumnInfo(name = "artist_name")
    private String artistName;

    @ColumnInfo(name = "album_id")
    private String albumId;

    @ColumnInfo(name = "album_name")
    private String albumName;

    private String lyrics;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    public RecentlyPlayedSongEntity() {
    }

    /**
     * @param id              Song ID
     * @param napsterId       Song ID in Napster API DB
     * @param albumIndex      Song position on the album
     * @param playbackSeconds Song length in seconds
     * @param name            Name of the song
     * @param artistId        Artist ID in Napster API DB
     * @param artistName      Artist name
     * @param albumId         AlbumID in Napster API DB
     * @param albumName       Name of the album
     * @param lyrics          Lyrics for the song
     * @param createdAt       Date when the song was inserted in the DB
     */
    public RecentlyPlayedSongEntity(int id,
                                    String napsterId,
                                    int albumIndex,
                                    int playbackSeconds,
                                    String name,
                                    String artistId,
                                    String artistName,
                                    String albumId,
                                    String albumName,
                                    String lyrics,
                                    Date createdAt) {
        this.id = id;
        this.napsterId = napsterId;
        this.albumIndex = albumIndex;
        this.playbackSeconds = playbackSeconds;
        this.name = name;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.lyrics = lyrics;
        this.createdAt = createdAt;
    }

    public RecentlyPlayedSongEntity(String napsterId,
                                    int albumIndex,
                                    int playbackSeconds,
                                    String name,
                                    String artistId,
                                    String artistName,
                                    String albumId,
                                    String albumName,
                                    String lyrics) {
        this.napsterId = napsterId;
        this.albumIndex = albumIndex;
        this.playbackSeconds = playbackSeconds;
        this.name = name;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNapsterId() {
        return napsterId;
    }

    public void setNapsterId(String napsterId) {
        this.napsterId = napsterId;
    }

    @Override
    public int getAlbumIndex() {
        return albumIndex;
    }

    public void setAlbumIndex(int albumIndex) {
        this.albumIndex = albumIndex;
    }

    @Override
    public int getPlaybackSeconds() {
        return playbackSeconds;
    }

    public void setPlaybackSeconds(int playbackSeconds) {
        this.playbackSeconds = playbackSeconds;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    @Override
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
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
        RecentlyPlayedSongEntity that = (RecentlyPlayedSongEntity) o;
        return id == that.id &&
                albumIndex == that.albumIndex &&
                playbackSeconds == that.playbackSeconds &&
                Objects.equals(napsterId, that.napsterId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(artistId, that.artistId) &&
                Objects.equals(artistName, that.artistName) &&
                Objects.equals(albumId, that.albumId) &&
                Objects.equals(albumName, that.albumName) &&
                Objects.equals(lyrics, that.lyrics) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, napsterId, albumIndex, playbackSeconds, name, artistId, artistName, albumId, albumName, lyrics, createdAt);
    }

    @Override
    public String toString() {
        return "TopSongEntity{" +
                "id=" + id +
                ", napsterId='" + napsterId + '\'' +
                ", albumIndex=" + albumIndex +
                ", playbackSeconds=" + playbackSeconds +
                ", name='" + name + '\'' +
                ", artistId='" + artistId + '\'' +
                ", artistName='" + artistName + '\'' +
                ", albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                ", lyrics='" + lyrics + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
