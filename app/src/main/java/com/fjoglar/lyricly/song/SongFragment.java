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


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.GlideHelper;
import com.fjoglar.lyricly.util.Injection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SongFragment extends Fragment {

    private static final String ARGUMENT_SONG_ID = "song_id";
    private static final String ARGUMENT_IS_FAVORITE_FLOW = "is_favorite_flow";

    private int mSongId;
    private boolean mIsFavoriteFlow;
    private Song mSong;
    private SongViewModel mSongViewModel;

    @BindView(R.id.imageview_song_cover)
    ImageView mImageViewSongCover;
    @BindView(R.id.floatingactionbutton_song_favorite)
    FloatingActionButton mFabSongFavorite;
    @BindView(R.id.textview_song_title)
    TextView mTextViewSongTitle;
    @BindView(R.id.textview_song_artist)
    TextView mTextViewSongArtist;
    @BindView(R.id.textview_song_lyrics)
    TextView mTextViewSongLyrics;
    @BindView(R.id.progressbar_song_loading)
    ProgressBar mProgressBarSongLoading;
    Menu mMenu;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public SongFragment() {
    }

    public static SongFragment newInstance(int songId, boolean isFavoriteFlow) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_SONG_ID, songId);
        arguments.putBoolean(ARGUMENT_IS_FAVORITE_FLOW, isFavoriteFlow);

        SongFragment songFragment = new SongFragment();
        songFragment.setArguments(arguments);
        return songFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARGUMENT_SONG_ID)) {
                mSongId = getArguments().getInt(ARGUMENT_SONG_ID);
            }
            if (getArguments().containsKey(ARGUMENT_IS_FAVORITE_FLOW)) {
                mIsFavoriteFlow = getArguments().getBoolean(ARGUMENT_IS_FAVORITE_FLOW);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_song_anim, container, false);
        ButterKnife.bind(this, root);

        setHasOptionsMenu(true);

        initViewModel();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.song_menu, menu);
        mMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
        setFavoriteIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareSong();
                return true;
            case R.id.favorite:
                favoriteClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.floatingactionbutton_song_favorite)
    void favoriteClicked() {
        mSongViewModel.onFavoriteClicked(mSong);
    }

    private void initViewModel() {
        SongViewModelFactory songViewModelFactory =
                Injection.provideSongViewModelFactory(getActivity(), mSongId);
        mSongViewModel =
                ViewModelProviders.of(this, songViewModelFactory)
                        .get(SongViewModel.class);

        subscribeUi(mSongViewModel);
        mSongViewModel.getSong();
    }

    private void subscribeUi(SongViewModel viewModel) {
        viewModel.response().observe(this, this::showSong);
        viewModel.addedToFavorites().observe(this, response -> showAddedToFavoritesMessage());
        viewModel.deletedFromFavorites().observe(this, response -> showDeletedFromFavoritesMessage());
    }

    private void showSong(SongResponse songResponse) {
        switch (songResponse.status) {
            case DATA:
                renderDataState(songResponse.data);
                break;
            case ERROR:
                renderErrorState(songResponse.error);
                break;
        }
    }

    private void showAddedToFavoritesMessage() {
        getSnackBar(getString(R.string.song_favorite_added)).show();
    }

    private void showDeletedFromFavoritesMessage() {
        getSnackBar(getString(R.string.song_favorite_deleted))
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (mIsFavoriteFlow) {
                            finishActivity();
                        }
                    }
                })
                .show();
    }

    private void renderDataState(Song song) {
        showSong(song);
    }

    private void renderErrorState(Throwable throwable) {
        getSnackBar(throwable.getMessage())
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        finishActivity();
                    }
                })
                .show();
    }

    private void showSong(Song song) {
        mSong = song;

        String albumId = mSong.getAlbumId();
        String imageUrl = getString(R.string.album_image_url, albumId, GlideHelper.IMAGE_SIZE_BIG);
        String thumbUrl = getString(R.string.album_image_url, albumId, GlideHelper.IMAGE_SIZE_SMALL);

        GlideHelper.loadWithThumb(mImageViewSongCover, imageUrl, thumbUrl);
        mTextViewSongTitle.setText(mSong.getName());
        mTextViewSongArtist.setText(mSong.getArtistName());
        mTextViewSongLyrics.setText(mSong.getLyrics());
        setFavoriteIcon();
    }

    private void setFavoriteIcon() {
        if(mMenu == null || mSong == null){
            return;
        }
        mMenu.getItem(1).setIcon(mSong.isFavorite() ? R.drawable.songs_ic_favorite_24dp :
                R.drawable.song_ic_favorite_border_24dp);
    }

    private Snackbar getSnackBar(String message) {
        return Snackbar.make(mFabSongFavorite, message, Snackbar.LENGTH_SHORT);
    }

    private void shareSong() {
        String mimeType = "text/plain";
        String title = getString(R.string.share_dialog_title);
        String message = getString(R.string.share_message, mSong.getArtistName(), mSong.getLyrics());

        ShareCompat.IntentBuilder
                .from(getActivity())
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(message)
                .startChooser();
    }

    private void finishActivity() {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            getActivity().finish();
        }
    }
}
