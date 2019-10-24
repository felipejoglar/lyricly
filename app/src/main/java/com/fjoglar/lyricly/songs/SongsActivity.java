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

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.song.SongActivity;
import com.fjoglar.lyricly.songs.favorite.FavoriteSongsFragment;
import com.fjoglar.lyricly.songs.recent.RecentSongsFragment;
import com.fjoglar.lyricly.songs.top.TopSongsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @BindView(R.id.bnv_songs_navigation)
    BottomNavigationView mBottomNavigationSongs;
    @BindView(R.id.tv_songs_app_bar_title)
    TextView mTextViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Lyricly);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        ButterKnife.bind(this);

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
                setAppBarTitle(R.string.songs_menu_popular);
                break;
            case R.id.recent:
                navigateToFragment(mRecentSongsFragment);
                setAppBarTitle(R.string.songs_menu_recent);
                break;
            case R.id.favorite:
                navigateToFragment(mFavoriteSongsFragment);
                setAppBarTitle(R.string.songs_menu_favorite);
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
                    .add(R.id.fl_songs_container, mFavoriteSongsFragment, TAG_FRAGMENT_FAVORITE)
                    .hide(mFavoriteSongsFragment)
                    .commit();
        }
        if (!mRecentSongsFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fl_songs_container, mRecentSongsFragment, TAG_FRAGMENT_RECENT)
                    .hide(mRecentSongsFragment)
                    .commit();
        }
        if (!mFavoriteSongsFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fl_songs_container, mTopSongsFragment, TAG_FRAGMENT_TOP)
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
                    setAppBarTitle(R.string.songs_menu_popular);
                    break;
                case TAG_FRAGMENT_RECENT:
                    mActiveFragment = mRecentSongsFragment;
                    setAppBarTitle(R.string.songs_menu_recent);
                    break;
                case TAG_FRAGMENT_FAVORITE:
                    mActiveFragment = mFavoriteSongsFragment;
                    setAppBarTitle(R.string.songs_menu_favorite);
                    break;
            }
        } else {
            mActiveFragment = mTopSongsFragment;
            setAppBarTitle(R.string.songs_menu_popular);
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

    private void setAppBarTitle(int resId) {
        mTextViewTitle.setText(resId);
    }

    /**
     * Shows the song detail fragment
     */
    public void show(Song song) {
        startActivity(SongActivity.getLaunchingIntent(
                this,
                song.getId(),
                mActiveFragment == mFavoriteSongsFragment));
    }
}