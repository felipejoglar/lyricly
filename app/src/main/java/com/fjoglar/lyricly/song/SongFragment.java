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

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.SongsLocalDataSource;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteDataSource;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongFragment extends Fragment {

    private static final String ARGUMENT_SONG_ID = "song_id";
    private static final String ARGUMENT_SONG_TYPE = "song_type";

    private static final String IMAGE_SIZE_BIG = "500x500";
    private static final String IMAGE_SIZE_MEDIUM = "200x200";
    private static final String IMAGE_SIZE_SMALL = "70x70";

    private int mSongId;
    private int mSongType;
    private Song mSong;

    @BindView(R.id.imageview_song_cover)
    ImageView mImageViewSongCover;
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

    public static SongFragment newInstance(int songId, int songType) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_SONG_ID, songId);
        arguments.putInt(ARGUMENT_SONG_TYPE, songType);

        SongFragment songFragment = new SongFragment();
        songFragment.setArguments(arguments);
        return songFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARGUMENT_SONG_ID) &&
                getArguments().containsKey(ARGUMENT_SONG_TYPE)) {
            mSongId = getArguments().getInt(ARGUMENT_SONG_ID);
            mSongType = getArguments().getInt(ARGUMENT_SONG_TYPE);
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

    private void initViewModel() {
        SongsRepository repository =
                SongsRepository.getInstance(
                        SongsLocalDataSource.getInstance(
                                SongDatabase.getInstance(getActivity().getApplicationContext())),
                        SongsRemoteDataSource.getInstance()
                );

        SongViewModel.Factory factory = new SongViewModel.Factory(repository, mSongId, mSongType);

        final SongViewModel songViewModel =
                ViewModelProviders.of(this, factory).get(SongViewModel.class);

        subscribeUi(songViewModel);
    }

    private void subscribeUi(SongViewModel viewModel) {
        switch (mSongType) {
            case SongActivity.SONG_TYPE_TOP:
                viewModel.getTopSong().observe(this, song -> {
                    visualizeSong(song);
                    viewModel.getTopSong().removeObserver(this::showSong);
                });
                break;
            case SongActivity.SONG_TYPE_RECENT:
                viewModel.getRecentSong().observe(this, song -> {
                    visualizeSong(song);
                    viewModel.getTopSong().removeObserver(this::showSong);
                });
                break;
            case SongActivity.SONG_TYPE_FAVORITE:
                viewModel.getFavoriteSong().observe(this, song -> {
                    visualizeSong(song);
                    viewModel.getTopSong().removeObserver(this::showSong);
                });
                break;
            default:
                viewModel.getTopSong().observe(this, song -> {
                    visualizeSong(song);
                    viewModel.getTopSong().removeObserver(this::showSong);
                });
                break;
        }

    }

    private void visualizeSong(Song song) {
        if (song != null) {
            setIsLoading(false);
            showSong(song);
        } else {
            setIsLoading(true);
        }
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
    }

    private void setIsLoading(boolean isLoading) {
        mProgressBarSongLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
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
