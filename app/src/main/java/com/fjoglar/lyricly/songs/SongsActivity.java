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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.songs.top.TopSongsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsActivity extends AppCompatActivity
        implements TopSongsFragment.OnItemClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

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
        getSupportActionBar().setTitle(R.string.songs_menu_popular);
        mBottomNavigationSongs.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            TopSongsFragment fragment = TopSongsFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_songs_container, fragment).commit();
        }

        // TODO: implement a better fetching logic
        startService(new Intent(this, UpdateTopSongsIntentService.class));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                mToolbar.setTitle(R.string.songs_menu_popular);
                break;
            case R.id.recent:
                mToolbar.setTitle(R.string.songs_menu_recent);
                break;
            case R.id.favorite:
                mToolbar.setTitle(R.string.songs_menu_favorite);
                break;
            default:
                mToolbar.setTitle(R.string.songs_menu_popular);
                return false;
        }
        return true;
    }

    @Override
    public void onItemClicked(Song song) {
        // TODO: navigate to song detail screen.
        Toast.makeText(getApplicationContext(),
                song.getName() + " by " + song.getArtistName(),
                Toast.LENGTH_SHORT).show();
    }
}
