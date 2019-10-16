/*
 * Copyright 2019 Felipe Joglar Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
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

public class NapsterApiSearchResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;

    @SerializedName("search")
    @Expose
    private Search search;

    public Meta getMeta() {
        return meta;
    }

    public Search getSearch() {
        return search;
    }

    public class Meta {

        @SerializedName("totalCount")
        @Expose
        private int totalCount;
        @SerializedName("returnedCount")
        @Expose
        private int returnedCount;

        public int getTotalCount() {
            return totalCount;
        }

        public int getReturnedCount() {
            return returnedCount;
        }
    }

    public class Search {

        @SerializedName("query")
        @Expose
        private String query;

        @SerializedName("data")
        @Expose
        private Data data;

        @SerializedName("order")
        @Expose
        private List<String> order = null;

        public String getQuery() {
            return query;
        }

        public Data getData() {
            return data;
        }

        public List<String> getOrder() {
            return order;
        }
    }

    public class Data {

        @SerializedName("tracks")
        @Expose
        private List<Track> tracks = null;

        public List<Track> getTracks() {
            return tracks;
        }
    }
}
