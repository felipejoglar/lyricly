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

public class Contributors {

    @SerializedName("primaryArtist")
    @Expose
    private String primaryArtist;
    @SerializedName("composer")
    @Expose
    private String composer;
    @SerializedName("producer")
    @Expose
    private String producer;
    @SerializedName("featuredPerformer")
    @Expose
    private String featuredPerformer;
    @SerializedName("guestMusician")
    @Expose
    private String guestMusician;
    @SerializedName("engineer")
    @Expose
    private String engineer;
    @SerializedName("remixer")
    @Expose
    private String remixer;
    @SerializedName("guestVocals")
    @Expose
    private String guestVocals;
    @SerializedName("nonPrimary")
    @Expose
    private String nonPrimary;

    public String getPrimaryArtist() {
        return primaryArtist;
    }

    public void setPrimaryArtist(String primaryArtist) {
        this.primaryArtist = primaryArtist;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getFeaturedPerformer() {
        return featuredPerformer;
    }

    public void setFeaturedPerformer(String featuredPerformer) {
        this.featuredPerformer = featuredPerformer;
    }

    public String getGuestMusician() {
        return guestMusician;
    }

    public void setGuestMusician(String guestMusician) {
        this.guestMusician = guestMusician;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getRemixer() {
        return remixer;
    }

    public void setRemixer(String remixer) {
        this.remixer = remixer;
    }

    public String getGuestVocals() {
        return guestVocals;
    }

    public void setGuestVocals(String guestVocals) {
        this.guestVocals = guestVocals;
    }

    public String getNonPrimary() {
        return nonPrimary;
    }

    public void setNonPrimary(String nonPrimary) {
        this.nonPrimary = nonPrimary;
    }

}
