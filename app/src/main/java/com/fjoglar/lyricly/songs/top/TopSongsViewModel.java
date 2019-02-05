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
import com.fjoglar.lyricly.songs.SongsResponse;
import com.fjoglar.lyricly.songs.SongsViewModel;
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class TopSongsViewModel extends ViewModel implements SongsViewModel {

    private SongsRepository mSongsRepository;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final MutableLiveData<SongsResponse> mResponse = new MutableLiveData<>();


    public TopSongsViewModel(SongsRepository songsRepository) {
        mSongsRepository = songsRepository;
        updateTopSongs();
        getTopSongs();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    @Override
    public MutableLiveData<SongsResponse> getResponse() {
        return mResponse;
    }

    private void updateTopSongs() {
        mDisposables.add(new UpdateTopSongsUseCase().execute(mSongsRepository, null)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .doOnSubscribe(__ -> mResponse.setValue(SongsResponse.loading()))
                .subscribe(
                        () -> mResponse.setValue(SongsResponse.success()),
                        error -> mResponse.setValue(SongsResponse.error(error))
                )
        );
    }

    private void getTopSongs() {
        mDisposables.add(new GetTopSongsUseCase().execute(mSongsRepository, null)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(
                        songs -> mResponse.setValue(SongsResponse.load(songs)),
                        error -> mResponse.setValue(SongsResponse.error(error))
                )
        );
    }
}
