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

package com.fjoglar.lyricly.songs.top;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.usecases.FlowableUseCase;

import java.util.List;

import io.reactivex.Flowable;

public class GetTopSongsUseCase implements FlowableUseCase<Void, List<Song>> {

    @Override
    public Flowable<List<Song>> execute(SongsRepository repository, Void parameter) {
        return repository.getTopSongs();
    }
}
