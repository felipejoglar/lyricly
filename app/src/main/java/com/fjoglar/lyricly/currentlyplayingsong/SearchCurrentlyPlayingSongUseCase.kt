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

package com.fjoglar.lyricly.currentlyplayingsong

import com.fjoglar.lyricly.data.SongsDataSource
import com.fjoglar.lyricly.data.model.Song
import com.fjoglar.lyricly.data.source.mapper.SongDataMapper
import com.fjoglar.lyricly.util.usecases.SingleUseCase
import io.reactivex.Single

class SearchCurrentlyPlayingSongUseCase : SingleUseCase<String, Song> {

    override fun execute(dataSource: SongsDataSource?, parameter: String?): Single<Song> {
        return Single.create<Song> { emitter ->
            val track =
                dataSource?.searchCurrentlyPlayingSong(parameter)
            val lyrics = dataSource?.fetchSongLyrics(track?.artistName, track?.name)
            if (!lyrics.isNullOrEmpty()) {
                val song = SongDataMapper.transform(track, true, lyrics)
                song?.let {
                    dataSource.saveSong(it)
                    emitter.onSuccess(it)
                }
            } else {
                emitter.onError(Throwable("No lyrics found"))
            }
        }
    }
}