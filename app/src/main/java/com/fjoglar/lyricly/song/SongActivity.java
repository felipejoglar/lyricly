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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fjoglar.lyricly.R;

public class SongActivity extends AppCompatActivity {

    public static final String EXTRA_SONG_ID = "song_id";
    public static final String EXTRA_IS_FAVORITE_FLOW = "is_favorite_flow";

    private int mSongId;
    private boolean mIsFavoriteFlow;

    public static Intent getLaunchingIntent(Context context, int songId, boolean isFavoriteFlow) {
        Intent songIntent = new Intent(context, SongActivity.class);
        songIntent.putExtra(SongActivity.EXTRA_SONG_ID, songId);
        songIntent.putExtra(SongActivity.EXTRA_IS_FAVORITE_FLOW, isFavoriteFlow);
        return songIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        getIntentExtras();
        loadSongFragment();
    }

    private void getIntentExtras() {
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(EXTRA_SONG_ID) || !intent.hasExtra(EXTRA_IS_FAVORITE_FLOW)) {
            closeOnError();
        }
        mSongId = getIntent().getIntExtra(EXTRA_SONG_ID, 0);
        mIsFavoriteFlow = getIntent().getBooleanExtra(EXTRA_IS_FAVORITE_FLOW, false);
    }

    private void loadSongFragment() {
        SongFragment songFragment = (SongFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fl_song_container);

        if (songFragment == null) {
            songFragment = SongFragment.newInstance(mSongId, mIsFavoriteFlow);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_song_container, songFragment)
                    .commit();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(getApplicationContext(), R.string.song_error_message, Toast.LENGTH_SHORT).show();
    }
}
