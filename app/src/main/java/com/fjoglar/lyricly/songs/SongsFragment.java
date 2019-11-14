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
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.core.Injection;
import com.fjoglar.lyricly.core.extensions.Dialogs;
import com.fjoglar.lyricly.core.navigation.Navigator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class SongsFragment extends Fragment {

    protected SongsViewModel mViewModel;

    private SongsAdapter mSongsAdapter;
    private GridLayoutManager mLayoutManager;

    @BindView(R.id.rv_songs)
    RecyclerView mRecyclerViewSongs;
    @BindView(R.id.pb_songs_loading)
    ProgressBar mProgressBarSongsLoading;
    @BindView(R.id.cl_songs_enable_notification_access)
    ConstraintLayout mClEnableNotificationAccess;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_songs, container, false);
        ButterKnife.bind(this, root);

        setUpRecyclerView();

        SongsViewModelFactory songsViewModelFactory =
                Injection.provideSongsViewModelFactory(getActivity());
        initViewModel(songsViewModelFactory);

        subscribeUi();

        return root;
    }

    @OnClick(R.id.btn_on_boarding_enable_notification_access)
    void onEnableNotificationClick() {
        Navigator.INSTANCE.showNotificationPermissionSettings(requireContext());
    }

    @OnClick(R.id.btn_on_boarding_enable_notification_info)
    void onNotificationMoreInfoClick() {
        Dialogs.showAlertDialogOk(
                requireContext(),
                R.string.on_boarding_notification_access_more_info_title,
                R.string.on_boarding_notification_access_more_info);
    }

    void goToTop() {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(requireActivity()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        smoothScroller.setTargetPosition(0);
        mLayoutManager.startSmoothScroll(smoothScroller);
    }

    protected void checkEnabledNotificationAccess() {
        String enabledNotificationListeners =
                Settings.Secure.getString(requireContext().getContentResolver(),
                        "enabled_notification_listeners");
        mClEnableNotificationAccess.setVisibility(
                enabledNotificationListeners.contains("CurrentlyPlayingSongNotificationListener") ?
                        View.GONE : View.VISIBLE);
    }

    protected abstract void initViewModel(SongsViewModelFactory songsViewModelFactory);

    private void subscribeUi() {
        mViewModel.getResponse().observe(this, this::showSongs);
        mViewModel.getLoadingState().observe(this, this::renderLoadingState);
    }

    private void showSongs(SongsResponse songsResponse) {
        switch (songsResponse.status) {
            case DATA:
                renderDataState(songsResponse.data);
                break;
            case ERROR:
                renderErrorState(songsResponse.error);
                break;
        }
    }

    private void renderLoadingState(boolean loading) {
        if (loading) {
            mProgressBarSongsLoading.setVisibility(View.VISIBLE);
        } else {
            mProgressBarSongsLoading.setVisibility(View.GONE);
        }
    }

    private void renderDataState(List<Song> songs) {
        mRecyclerViewSongs.setVisibility(View.VISIBLE);
        mSongsAdapter.setSongs(songs);
    }

    private void renderErrorState(Throwable throwable) {
        mProgressBarSongsLoading.setVisibility(View.GONE);
        if (throwable != null) {
            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpRecyclerView() {
        mLayoutManager = new GridLayoutManager(getActivity(),
                this.getResources().getInteger(R.integer.songs_activity_column_number));
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 2;
                else
                    return 1;
            }
        });
        mSongsAdapter = new SongsAdapter(getActivity(), mSongClickCallback);

        mRecyclerViewSongs.setLayoutManager(mLayoutManager);
        mRecyclerViewSongs.setHasFixedSize(true);
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
    }

    private final SongClickCallback mSongClickCallback = song -> {

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((SongsActivity) requireActivity()).show(song);
        }
    };
}
