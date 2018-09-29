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


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.Injection;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SongFragment extends Fragment {

    private static final String ARGUMENT_SONG_ID = "song_id";

    private static final String IMAGE_SIZE_BIG = "500x500";
    private static final String IMAGE_SIZE_MEDIUM = "200x200";
    private static final String IMAGE_SIZE_SMALL = "70x70";

    private int mSongId;
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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public SongFragment() {
    }

    public static SongFragment newInstance(int songId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_SONG_ID, songId);

        SongFragment songFragment = new SongFragment();
        songFragment.setArguments(arguments);
        return songFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARGUMENT_SONG_ID)) {
            mSongId = getArguments().getInt(ARGUMENT_SONG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_song, container, false);
        ButterKnife.bind(this, root);

        setHasOptionsMenu(true);

        initViewModel();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.song_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareSong();
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
        viewModel.response().observe(this, song -> viewModel.response().observe(this, this::showSong));

    }

    private void showSong(SongResponse songResponse) {
        switch (songResponse.status) {
            case LOADING:
                renderLoadingState();
                break;
            case SUCCESS:
                renderDataState(songResponse.data);
                break;
            case ERROR:
                renderErrorState(songResponse.error);
                break;
        }
    }

    private void renderLoadingState() {
        mProgressBarSongLoading.setVisibility(View.VISIBLE);
    }

    private void renderDataState(Song song) {
        mProgressBarSongLoading.setVisibility(View.GONE);
        showSong(song);
    }

    private void renderErrorState(Throwable throwable) {
        mProgressBarSongLoading.setVisibility(View.GONE);
        Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_LONG).show();
    }

    private void showSong(Song song) {
        mSong = song;

        String albumId = mSong.getAlbumId();
        String imageUrl = getString(R.string.album_image_url, albumId, IMAGE_SIZE_BIG);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.colorSecondaryLight)
                .error(R.color.colorPrimaryLight)
                .into(mImageViewSongCover);
        mTextViewSongTitle.setText(mSong.getName());
        mTextViewSongArtist.setText(mSong.getArtistName());
        mTextViewSongLyrics.setText(mSong.getLyrics());

        mFabSongFavorite.setImageResource(
                mSong.isFavorite() ? R.drawable.songs_ic_favorite_24dp :
                        R.drawable.song_ic_favorite_border_24dp);
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
}
