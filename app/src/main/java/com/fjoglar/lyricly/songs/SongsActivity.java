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
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private static final String TAG_FRAGMENT_SAVED_STATE = "tag_fragment_saved_state";
    private static final String TAG_FRAGMENT_TOP = "tag_fragment_top";
    private static final String TAG_FRAGMENT_RECENT = "tag_fragment_recent";
    private static final String TAG_FRAGMENT_FAVORITE = "tag_fragment_favorite";

    private final FragmentManager mFragmentManager = getSupportFragmentManager();

    private SongsFragment mTopSongsFragment;
    private SongsFragment mRecentSongsFragment;
    private SongsFragment mFavoriteSongsFragment;
    private SongsFragment mActiveFragment;

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

        createFragments(savedInstanceState);
        addFragments();
        selectFragment(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TAG_FRAGMENT_SAVED_STATE, mActiveFragment.getTag());

        mFragmentManager.putFragment(outState, TAG_FRAGMENT_TOP, mTopSongsFragment);
        mFragmentManager.putFragment(outState, TAG_FRAGMENT_RECENT, mRecentSongsFragment);
        mFragmentManager.putFragment(outState, TAG_FRAGMENT_FAVORITE, mFavoriteSongsFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                navigateToFragment(mTopSongsFragment);
                break;
            case R.id.recent:
                navigateToFragment(mRecentSongsFragment);
                break;
            case R.id.favorite:
                navigateToFragment(mFavoriteSongsFragment);
                break;
        }
        return true;
    }

    /**
     * Create or recover the Fragments that will be used in the Songs screen.
     *
     * @param savedInstanceState savedInstanceState Activity Bundle
     */
    private void createFragments(Bundle savedInstanceState) {
        boolean isSavedInstanceState = savedInstanceState != null;
        mTopSongsFragment = isSavedInstanceState ?
                (SongsFragment) mFragmentManager.getFragment(savedInstanceState, TAG_FRAGMENT_TOP) :
                TopSongsFragment.newInstance();
        mRecentSongsFragment = isSavedInstanceState ?
                (SongsFragment) mFragmentManager.getFragment(savedInstanceState, TAG_FRAGMENT_RECENT) :
                RecentSongsFragment.newInstance();
        mFavoriteSongsFragment = isSavedInstanceState ?
                (SongsFragment) mFragmentManager.getFragment(savedInstanceState, TAG_FRAGMENT_FAVORITE) :
                FavoriteSongsFragment.newInstance();
    }

    /**
     * Add the screen's Fragments to the FragmentManager.
     */
    private void addFragments() {
        if (!mTopSongsFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.framelayout_songs_container, mFavoriteSongsFragment, TAG_FRAGMENT_FAVORITE)
                    .hide(mFavoriteSongsFragment)
                    .commit();
        }
        if (!mRecentSongsFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.framelayout_songs_container, mRecentSongsFragment, TAG_FRAGMENT_RECENT)
                    .hide(mRecentSongsFragment)
                    .commit();
        }
        if (!mFavoriteSongsFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.framelayout_songs_container, mTopSongsFragment, TAG_FRAGMENT_TOP)
                    .hide(mTopSongsFragment)
                    .commit();
        }
    }

    /**
     * Select the active fragment when activity is created or recreated.
     *
     * @param savedInstanceState savedInstanceState Activity Bundle
     */
    private void selectFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            switch (savedInstanceState.getString(TAG_FRAGMENT_SAVED_STATE)) {
                case TAG_FRAGMENT_TOP:
                    mActiveFragment = mTopSongsFragment;
                    break;
                case TAG_FRAGMENT_RECENT:
                    mActiveFragment = mRecentSongsFragment;
                    break;
                case TAG_FRAGMENT_FAVORITE:
                    mActiveFragment = mFavoriteSongsFragment;
                    break;
            }
        } else {
            mActiveFragment = mTopSongsFragment;
        }
        mFragmentManager.beginTransaction().show(mActiveFragment).commit();
    }

    /**
     * Navigate to the selected Fragment clicked in the BottomNavigationBar.
     *
     * @param fragment Fragment to navigate to.
     */
    private void navigateToFragment(SongsFragment fragment) {
        if (fragment == mActiveFragment) {
            // Navigate to top of the list if we are currently in this fragment.
            mActiveFragment.goToTop();
            return;
        }

        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
                .hide(mActiveFragment).show(fragment).commit();

        mActiveFragment = fragment;
    }

    /**
     * Shows the song detail fragment
     */
    public void show(Song song) {
        Intent songIntent = new Intent(this, SongActivity.class);
        songIntent.putExtra(SongActivity.EXTRA_SONG_ID, song.getId());
        startActivity(songIntent);
    }
}