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

package com.fjoglar.lyricly.song;

import android.util.Log;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.SingleLiveEvent;
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class SongViewModel extends ViewModel {

    private SongsRepository mSongsRepository;
    private int mSongId;

    private SingleLiveEvent<Void> mAddedToFavorites = new SingleLiveEvent<>();
    private SingleLiveEvent<Void> mDeletedFromFavorites = new SingleLiveEvent<>();

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final MutableLiveData<SongResponse> mResponse = new MutableLiveData<>();


    public SongViewModel(@Nullable SongsRepository songsRepository, int songId) {
        mSongsRepository = songsRepository;
        mSongId = songId;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    MutableLiveData<SongResponse> response() {
        return mResponse;
    }

    SingleLiveEvent<Void> addedToFavorites() {
        return mAddedToFavorites;
    }

    SingleLiveEvent<Void> deletedFromFavorites() {
        return mDeletedFromFavorites;
    }

    public void getSong() {
        mDisposables.add(new GetSongByIdUseCase().execute(mSongsRepository, mSongId)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .doOnSubscribe(__ -> mResponse.setValue(SongResponse.loading()))
                .subscribe(
                        song -> mResponse.setValue(SongResponse.success(song)),
                        throwable -> mResponse.setValue(SongResponse.error(throwable))
                )
        );
    }

    public void onFavoriteClicked(Song song) {
        if (song.isFavorite()) {
            deleteFavorite(song);
        } else {
            saveFavorite(song);
        }
    }

    private void saveFavorite(Song song) {
        mDisposables.add(new AddSongToFavoriteUseCase().execute(mSongsRepository, song)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(() -> mAddedToFavorites.call(),
                        throwable -> Log.d("SongViewModel", throwable.toString())));
    }

    private void deleteFavorite(Song song) {
        mDisposables.add(new DeleteSongFromFavoriteUseCase().execute(mSongsRepository, song)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(() -> mDeletedFromFavorites.call(),
                        throwable -> Log.d("SongViewModel", throwable.toString())));
    }
}
