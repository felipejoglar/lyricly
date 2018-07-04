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

import java.util.List;

public class Track {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("index")
    @Expose
    private int index;
    @SerializedName("disc")
    @Expose
    private int disc;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("playbackSeconds")
    @Expose
    private int playbackSeconds;
    @SerializedName("isExplicit")
    @Expose
    private boolean isExplicit;
    @SerializedName("isStreamable")
    @Expose
    private boolean isStreamable;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isrc")
    @Expose
    private String isrc;
    @SerializedName("shortcut")
    @Expose
    private String shortcut;
    @SerializedName("blurbs")
    @Expose
    private List<Object> blurbs = null;
    @SerializedName("artistId")
    @Expose
    private String artistId;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("formats")
    @Expose
    private List<Format> formats = null;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("contributors")
    @Expose
    private Contributors contributors;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("previewURL")
    @Expose
    private String previewURL;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getPlaybackSeconds() {
        return playbackSeconds;
    }

    public void setPlaybackSeconds(int playbackSeconds) {
        this.playbackSeconds = playbackSeconds;
    }

    public boolean isIsExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(boolean isExplicit) {
        this.isExplicit = isExplicit;
    }

    public boolean isIsStreamable() {
        return isStreamable;
    }

    public void setIsStreamable(boolean isStreamable) {
        this.isStreamable = isStreamable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public List<Object> getBlurbs() {
        return blurbs;
    }

    public void setBlurbs(List<Object> blurbs) {
        this.blurbs = blurbs;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Contributors getContributors() {
        return contributors;
    }

    public void setContributors(Contributors contributors) {
        this.contributors = contributors;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

}
