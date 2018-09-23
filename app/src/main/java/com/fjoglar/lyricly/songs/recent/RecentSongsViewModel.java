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
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.songs.SongsResponse;
import com.fjoglar.lyricly.util.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class RecentSongsViewModel extends ViewModel {

    private SongsRepository mSongsRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<SongsResponse> response = new MutableLiveData<>();

    RecentSongsViewModel(@Nullable SongsRepository songsRepository) {
        if (mSongsRepository != null) {
            // ViewModel is created per Activity
            return;
        }
        if (songsRepository != null) {
            mSongsRepository = songsRepository;
        }
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    MutableLiveData<SongsResponse> response() {
        return response;
    }

    public void getRecentSongs() {
        loadSongs();
    }

    private void loadSongs() {
        disposables.add(new GetRecentSongsUseCase().execute(mSongsRepository, null)
                .subscribeOn(SchedulerProvider.getInstance().io())
                .observeOn(SchedulerProvider.getInstance().ui())
                .doOnSubscribe(__ -> response.setValue(SongsResponse.loading()))
                .subscribe(
                        songs -> response.setValue(SongsResponse.success(songs)),
                        throwable -> response.setValue(SongsResponse.error(throwable))
                )
        );
    }

    static class Factory extends ViewModelProvider.NewInstanceFactory {

        private SongsRepository mSongsRepository;

        Factory(SongsRepository songsRepository) {
            mSongsRepository = songsRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RecentSongsViewModel(mSongsRepository);
        }
    }
}
