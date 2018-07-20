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

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.fjoglar.lyricly.songs.SongsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopSongsFragment extends Fragment implements SongsAdapter.SongClickListener {

    private OnItemClickListener mListener;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpRecyclerView();
        initViewModel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            mListener = (OnItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSongClick(Song song) {
        mListener.onItemClicked(song);
    }

    private void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                this.getResources().getInteger(R.integer.songs_activity_column_number));
        mSongsAdapter = new SongsAdapter(getActivity(), this);

        mRecyclerViewSongs.setLayoutManager(layoutManager);
        mRecyclerViewSongs.setHasFixedSize(true);
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
    }

    private void initViewModel() {
        SongsDataSource repository =
                SongsRepository.getInstance(
                        SongsLocalDataSource.getInstance(
                                SongDatabase.getInstance(getActivity().getApplicationContext())),
                        SongsRemoteDataSource.getInstance()
                );
        TopSongsViewModel.Factory factory = new TopSongsViewModel.Factory(repository);

        TopSongsViewModel topSongsViewModel =
                ViewModelProviders.of(this, factory).get(TopSongsViewModel.class);

        subscribeUi(topSongsViewModel);
    }

    private void subscribeUi(TopSongsViewModel viewModel) {
        viewModel.getTopSongs().observe(this, topSongEntities -> {
            if (topSongEntities != null) {
                setIsLoading(false);
                showSongs(topSongEntities);
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

    public interface OnItemClickListener {
        void onItemClicked(Song song);
    }
}