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

package com.fjoglar.lyricly.songs.top;

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
import android.widget.Toast;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.songs.SongClickCallback;
import com.fjoglar.lyricly.songs.SongsActivity;
import com.fjoglar.lyricly.songs.SongsAdapter;
import com.fjoglar.lyricly.songs.SongsResponse;
import com.fjoglar.lyricly.util.Injection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopSongsFragment extends Fragment {

    private SongsAdapter mSongsAdapter;

    @BindView(R.id.recyclerview_songs)
    RecyclerView mRecyclerViewSongs;
    @BindView(R.id.progressbar_songs_loading)
    ProgressBar mProgressBarSongsLoading;

    public TopSongsFragment() {
        // Required empty public constructor
    }

    public static TopSongsFragment newInstance() {
        return new TopSongsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        SongsRepository repository = Injection.provideSongsRepository(getActivity());
        TopSongsViewModel.Factory factory = new TopSongsViewModel.Factory(repository);

        TopSongsViewModel topSongsViewModel =
                ViewModelProviders.of(this, factory).get(TopSongsViewModel.class);

        subscribeUi(topSongsViewModel);
        topSongsViewModel.updateTopSongs();
        topSongsViewModel.getTopSongs();
    }

    private void subscribeUi(TopSongsViewModel viewModel) {
        viewModel.response().observe(this, this::showSongs);
    }

    private void showSongs(SongsResponse songsResponse) {
        switch (songsResponse.status) {
            case LOADING:
                renderLoadingState();
                break;
            case SUCCESS:
                renderDataState(songsResponse.data);
                break;
            case ERROR:
                renderErrorState(songsResponse.error);
                break;
        }
    }

    private void renderLoadingState() {
        mRecyclerViewSongs.setVisibility(View.GONE);
        mProgressBarSongsLoading.setVisibility(View.VISIBLE);
    }

    private void renderDataState(List<Song> songs) {
        mProgressBarSongsLoading.setVisibility(View.GONE);
        mRecyclerViewSongs.setVisibility(View.VISIBLE);
        mSongsAdapter.setSongs(songs);
    }

    private void renderErrorState(Throwable throwable) {
        mProgressBarSongsLoading.setVisibility(View.GONE);
        mRecyclerViewSongs.setVisibility(View.GONE);
        Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    private final SongClickCallback mSongClickCallback = song -> {

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((SongsActivity) getActivity()).show(song);
        }
    };
}
