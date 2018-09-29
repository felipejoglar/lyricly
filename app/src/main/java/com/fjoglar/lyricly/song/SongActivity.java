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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.fjoglar.lyricly.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongActivity extends AppCompatActivity {

    public static final String EXTRA_SONG_ID = "song_id";

    private int mSongId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setTitle();

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(EXTRA_SONG_ID)) {
            closeOnError();
            return;
        }

        mSongId = getIntent().getIntExtra(EXTRA_SONG_ID, 0);

        SongFragment songFragment = (SongFragment) getSupportFragmentManager()
                .findFragmentById(R.id.framelayout_song_container);

        if (songFragment == null) {
            songFragment = SongFragment.newInstance(mSongId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_song_container, songFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(getApplicationContext(), R.string.song_error_message, Toast.LENGTH_SHORT).show();
    }
}
