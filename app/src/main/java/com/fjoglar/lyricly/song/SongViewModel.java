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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.AppExecutors;

import java.util.Date;

public class SongViewModel extends ViewModel {

    private SongsRepository mSongsRepository;
    private int mSongId;
    private int mSongType;

    private LiveData<Song> mSong;

    SongViewModel(@Nullable SongsRepository songsRepository, int songId, int songType) {
        mSongsRepository = songsRepository;
        mSongId = songId;
        mSongType = songType;
    }

    public LiveData<Song> getSong() {
        if (mSong == null) {
            mSong = mSongsRepository.getSongById(mSongId);
        }
        return mSong;
    }

    public void onFavoriteClicked (Song song) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (mSongType == SongActivity.SONG_TYPE_FAVORITE) {
                mSongsRepository.deleteFavoriteSongById(song.getId());
            } else {
                mSongsRepository.saveSong(new Song(song, true, new Date()));
            }
        });
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
