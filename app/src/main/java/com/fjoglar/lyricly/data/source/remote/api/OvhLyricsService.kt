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
package com.fjoglar.lyricly.data.source.remote.api

import com.fjoglar.lyricly.BuildConfig
import com.fjoglar.lyricly.data.source.remote.entity.OvhLyricsApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface OvhLyricsService {

    @GET("{artist}/{title}")
    fun getSongLyrics(
        @Path("artist") artist: String,
        @Path("title") title: String
    ): Call<OvhLyricsApiResponse>

    companion object {

        private const val BASE_URL = "https://api.lyrics.ovh/v1/"
        private const val TIMEOUT = 5

        @JvmStatic
        fun retrofit(): Retrofit {
            val httpClient = with(OkHttpClient().newBuilder()) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
                })
                readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient)
                .build()
        }
    }
}