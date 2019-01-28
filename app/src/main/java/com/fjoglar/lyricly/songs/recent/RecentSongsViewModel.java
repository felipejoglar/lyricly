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

package com.fjoglar.lyricly.songs.recent;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.model.Status;
import com.fjoglar.lyricly.songs.SongsViewModel;
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class RecentSongsViewModel extends ViewModel implements SongsViewModel {

    private SongsRepository mSongsRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<List<Song>> songs = new MutableLiveData<>();
    private MutableLiveData<Status> status = new MutableLiveData<>();

    public RecentSongsViewModel(SongsRepository songsRepository) {
        mSongsRepository = songsRepository;
        getRecentSongs();
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    @Override
    public MutableLiveData<List<Song>> getSongs() {
        return songs;
    }

    @Override
    public MutableLiveData<Status> getStatus() {
        return status;
    }

    private void getRecentSongs() {
        disposables.add(new GetRecentSongsUseCase().execute(mSongsRepository, null)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .subscribe(
                        this::setSongs,
                        this::logError
                )
        );
    }

    private void setSongs(List<Song> result) {
        songs.setValue(result);
    }

    private void logError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
