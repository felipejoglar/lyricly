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

package com.fjoglar.lyricly.songs.favorite;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.songs.SongsResponse;
import com.fjoglar.lyricly.songs.SongsViewModel;
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class FavoriteSongsViewModel extends ViewModel implements SongsViewModel {

    private SongsDataSource mSongsDataSource;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final MutableLiveData<SongsResponse> mResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mLoadingState = new MutableLiveData<>();

    public FavoriteSongsViewModel(@Nullable SongsDataSource songsDataSource) {
        mSongsDataSource = songsDataSource;
        mLoadingState.setValue(false);
        getFavoriteSongs();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    @Override
    public LiveData<SongsResponse> getResponse() {
        return mResponse;
    }

    @Override
    public LiveData<Boolean> getLoadingState() {
        return mLoadingState;
    }

    private void getFavoriteSongs() {
        mDisposables.add(new GetFavoriteSongsUseCase().execute(mSongsDataSource, null)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(
                        songs -> mResponse.setValue(SongsResponse.load(songs)),
                        error -> mResponse.setValue(SongsResponse.error(error))
                )
        );
    }
}
