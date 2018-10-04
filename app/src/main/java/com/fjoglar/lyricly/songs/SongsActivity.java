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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.song.SongActivity;
import com.fjoglar.lyricly.songs.favorite.FavoriteSongsFragment;
import com.fjoglar.lyricly.songs.recent.RecentSongsFragment;
import com.fjoglar.lyricly.songs.top.TopSongsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int mBottomNavigationSelectedItem;

    private Fragment mTopSongsFragment;
    private Fragment mRecentSongsFragment;
    private Fragment mFavoriteSongsFragment;
    private Fragment mActiveFragment;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation_songs)
    BottomNavigationView mBottomNavigationSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mBottomNavigationSongs.setOnNavigationItemSelectedListener(this);

        mActiveFragment = getSupportFragmentManager()
                .findFragmentById(R.id.framelayout_song_container);

        if (mActiveFragment == null) {
            activateFragments();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                loadFragment(mTopSongsFragment);
                break;
            case R.id.recent:
                loadFragment(mRecentSongsFragment);
                break;
            case R.id.favorite:
                loadFragment(mFavoriteSongsFragment);
                break;
        }
        return true;
    }

    /**
     * Shows the song detail fragment
     */
    public void show(Song song) {
        Intent songIntent = new Intent(this, SongActivity.class);
        songIntent.putExtra(SongActivity.EXTRA_SONG_ID, song.getId());
        startActivity(songIntent);
    }

    private void activateFragments() {
        mTopSongsFragment = TopSongsFragment.newInstance();
        mRecentSongsFragment = RecentSongsFragment.newInstance();
        mFavoriteSongsFragment = FavoriteSongsFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout_songs_container, mFavoriteSongsFragment, "2")
                .hide(mFavoriteSongsFragment)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout_songs_container, mRecentSongsFragment, "1")
                .hide(mRecentSongsFragment)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout_songs_container, mTopSongsFragment, "0")
                .commit();

        mActiveFragment = mTopSongsFragment;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .hide(mActiveFragment).show(fragment).commit();
        mActiveFragment = fragment;
    }
}