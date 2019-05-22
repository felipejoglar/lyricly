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

package com.fjoglar.lyricly.songs;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.fjoglar.lyricly.data.model.Song;

import java.util.List;

public class SongsDiffCallback extends DiffUtil.Callback {

    private final List<? extends Song> mOldSongs;
    private final List<? extends Song> mNewSongs;

    public SongsDiffCallback(List<? extends Song> oldSongs, List<? extends Song> newSongs) {
        this.mOldSongs = oldSongs;
        this.mNewSongs = newSongs;
    }

    @Override
    public int getOldListSize() {
        return mOldSongs.size();
    }

    @Override
    public int getNewListSize() {
        return mNewSongs.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldSongs.get(oldItemPosition).getSourceId()
                .equals(mNewSongs.get(newItemPosition).getSourceId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Song oldSong = mOldSongs.get(oldItemPosition);
        final Song newSong = mNewSongs.get(newItemPosition);

        // TODO: implement equals for our songs items!!
        return oldSong.equals(newSong);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if we're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
