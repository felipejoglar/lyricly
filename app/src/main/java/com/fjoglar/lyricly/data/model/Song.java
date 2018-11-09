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

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

/**
 * Immutable model class for a Song.
 */
@Entity(tableName = "songs")
public class Song {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "napster_id")
    private String napsterId;

    @ColumnInfo(name = "album_index")
    private int albumIndex;

    @ColumnInfo(name = "playback_seconds")
    private int playbackSeconds;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "artist_id")
    private String artistId;

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

    @ColumnInfo(name= "top_order")
    private int topOrder;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @Ignore
    public Song() {
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
     * @param top             The song is in the top list
     * @param recent          The song is in the recent list
     * @param favorite        The song is in the favorite list
     * @param topOrder        The order of the song in the top list, if applies.
     * @param createdAt       Date when the song was inserted in the DB
     */
    public Song(int id,
                String napsterId,
                int albumIndex,
                int playbackSeconds,
                String name,
                String artistId,
                String artistName,
                String albumId,
                String albumName,
                String lyrics,
                boolean top,
                boolean recent,
                boolean favorite,
                int topOrder,
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
        this.top = top;
        this.recent = recent;
        this.favorite = favorite;
        this.topOrder = topOrder;
        this.createdAt = createdAt;
    }

    @Ignore
    public Song(String napsterId,
                int albumIndex,
                int playbackSeconds,
                String name,
                String artistId,
                String artistName,
                String albumId,
                String albumName,
                String lyrics,
                boolean top,
                boolean recent,
                boolean favorite,
                int topOrder,
                Date createdAt) {
        this.napsterId = napsterId;
        this.albumIndex = albumIndex;
        this.playbackSeconds = playbackSeconds;
        this.name = name;
        this.artistId = artistId;
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
     * @param song      the song to be added.
     * @param favorite  mark the song as favorite.
     * @param createdAt the date when it was created.
     */
    @Ignore
    public Song(Song song, boolean favorite, Date createdAt) {
        this.napsterId = song.getNapsterId();
        this.albumIndex = song.getAlbumIndex();
        this.playbackSeconds = song.getPlaybackSeconds();
        this.name = song.getName();
        this.artistId = song.getArtistId();
        this.artistName = song.getArtistName();
        this.albumId = song.getAlbumId();
        this.albumName = song.getAlbumName();
        this.lyrics = song.getLyrics();
        this.top = false;
        this.recent = false;
        this.favorite = favorite;
        this.topOrder = song.getTopOrder();
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getNapsterId() {
        return napsterId;
    }

    public int getAlbumIndex() {
        return albumIndex;
    }

    public int getPlaybackSeconds() {
        return playbackSeconds;
    }

    public String getName() {
        return name;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getLyrics() {
        return lyrics;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isRecent() {
        return recent;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getTopOrder() {
        return topOrder;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                albumIndex == song.albumIndex &&
                playbackSeconds == song.playbackSeconds &&
                top == song.top &&
                recent == song.recent &&
                favorite == song.favorite &&
                topOrder == song.topOrder &&
                Objects.equals(napsterId, song.napsterId) &&
                Objects.equals(name, song.name) &&
                Objects.equals(artistId, song.artistId) &&
                Objects.equals(artistName, song.artistName) &&
                Objects.equals(albumId, song.albumId) &&
                Objects.equals(albumName, song.albumName) &&
                Objects.equals(lyrics, song.lyrics) &&
                Objects.equals(createdAt, song.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                napsterId,
                albumIndex,
                playbackSeconds,
                name,
                artistId,
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

    @Override
    public String toString() {
        return "Song{" +
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
                ", top=" + top +
                ", recent=" + recent +
                ", favorite=" + favorite +
                ", topOrder=" + topOrder +
                ", createdAt=" + createdAt +
                '}';
    }
}
