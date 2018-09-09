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

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider;

import java.util.Date;

import io.reactivex.disposables.CompositeDisposable;

public class SongViewModel extends ViewModel {

    private SongsRepository mSongsRepository;
    private int mSongId;
    private int mSongType;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<SongResponse> response = new MutableLiveData<>();


    SongViewModel(@Nullable SongsRepository songsRepository, int songId, int songType) {
        mSongsRepository = songsRepository;
        mSongId = songId;
        mSongType = songType;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    MutableLiveData<SongResponse> response() {
        return response;
    }

    public void getSong() {
        disposables.add(mSongsRepository.getSongById(mSongId)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .doOnSubscribe(__ -> response.setValue(SongResponse.loading()))
                .subscribe(
                        song -> response.setValue(SongResponse.success(song)),
                        throwable -> response.setValue(SongResponse.error(throwable))
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
        disposables.add(mSongsRepository.saveSong(new Song(song, true, new Date()))
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(() -> Log.d("SongViewModel", "Saved"),
                        throwable -> Log.d("SongViewModel", throwable.toString())));
    }

    private void deleteFavorite(Song song) {
        disposables.add(mSongsRepository.deleteFavoriteSongById(song.getId())
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(() -> Log.d("SongViewModel", "Deleted"),
                        throwable -> Log.d("SongViewModel", throwable.toString())));

    }

    static class Factory extends ViewModelProvider.NewInstanceFactory {

        private SongsRepository mSongsRepository;
        private int mSongId;
        private int mSongType;

        Factory(SongsRepository songsDataSource, int songId, int songType) {
            mSongsRepository = songsDataSource;
            mSongId = songId;
            mSongType = songType;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SongViewModel(mSongsRepository, mSongId, mSongType);
        }
    }
}
