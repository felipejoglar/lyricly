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

package com.fjoglar.lyricly.songs.favorite;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.SongsDataSource;
import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.data.source.local.SongsLocalDataSource;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteDataSource;
import com.fjoglar.lyricly.songs.SongClickCallback;
import com.fjoglar.lyricly.songs.SongsActivity;
import com.fjoglar.lyricly.songs.SongsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteSongsFragment extends Fragment {

    private SongsAdapter mSongsAdapter;

    @BindView(R.id.recyclerview_songs)
    RecyclerView mRecyclerViewSongs;
    @BindView(R.id.progressbar_songs_loading)
    ProgressBar mProgressBarSongsLoading;

    public FavoriteSongsFragment() {
        // Required empty public constructor
    }

    public static FavoriteSongsFragment newInstance() {
        return new FavoriteSongsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_songs, container, false);
        ButterKnife.bind(this, root);

        setUpRecyclerView();
        initViewModel();

        return root;
    }

    private void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                this.getResources().getInteger(R.integer.songs_activity_column_number));
        mSongsAdapter = new SongsAdapter(getActivity(), mSongClickCallback);

        mRecyclerViewSongs.setLayoutManager(layoutManager);
        mRecyclerViewSongs.setHasFixedSize(true);
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
    }

    private void initViewModel() {
        SongsRepository repository =
                SongsRepository.getInstance(
                        SongsLocalDataSource.getInstance(
                                SongDatabase.getInstance(getActivity().getApplicationContext())),
                        SongsRemoteDataSource.getInstance()
                );
        FavoriteSongsViewModel.Factory factory = new FavoriteSongsViewModel.Factory(repository);

        FavoriteSongsViewModel favoriteSongsViewModel =
                ViewModelProviders.of(this, factory).get(FavoriteSongsViewModel.class);

        subscribeUi(favoriteSongsViewModel);
    }

    private void subscribeUi(FavoriteSongsViewModel viewModel) {
        viewModel.getFavoriteSongs().observe(this, favoriteSongEntities -> {
            if (favoriteSongEntities != null) {
                setIsLoading(false);
                showSongs(favoriteSongEntities);
            } else {
                setIsLoading(true);
            }
        });
    }

    private void showSongs(List<? extends Song> songs) {
        mSongsAdapter.setSongs(songs);
    }

    private void setIsLoading(boolean isLoading) {
        mProgressBarSongsLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private final SongClickCallback mSongClickCallback = song -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((SongsActivity) getActivity()).show(song);
        }
    };
}
