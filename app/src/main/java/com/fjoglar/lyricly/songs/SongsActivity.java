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

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsActivity extends AppCompatActivity implements SongsAdapter.SongClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private SongsAdapter mSongsAdapter;
    private LiveData<List<? extends Song>> mSongs;

    @BindView(R.id.recyclerview_songs)
    RecyclerView mRecyclerViewSongs;
    @BindView(R.id.progressbar_songs_loading)
    ProgressBar mProgressBarSongsLoading;
    @BindView(R.id.bottom_navigation_songs)
    BottomNavigationView mBottomNavigationSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        ButterKnife.bind(this);

        LiveData<List<TopSongEntity>> songs = SongDatabase.getInstance(getApplicationContext())
                .topSongDao().getAll();

        songs.observe(this, this::showSongs);

        setUpRecyclerView();

        mBottomNavigationSongs.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:

                break;
            case R.id.recent:

                break;
            case R.id.favorite:

                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onSongClick(Song song) {
        // TODO: navigate to song detail screen.
        Toast.makeText(getApplicationContext(),
                song.getName() + " by " + song.getArtistName(),
                Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                this.getResources().getInteger(R.integer.songs_activity_column_number));
        mSongsAdapter = new SongsAdapter(this, this);

        mRecyclerViewSongs.setLayoutManager(layoutManager);
        mRecyclerViewSongs.setHasFixedSize(true);
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
    }

    private void showSongs(List<? extends Song> songs) {
        mSongsAdapter.setSongs(songs);
    }

    private void setIsLoading(boolean isLoading) {
        mProgressBarSongsLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
