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

package com.fjoglar.lyricly.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.source.local.SongsLocalDataSource;
import com.fjoglar.lyricly.data.source.local.db.SongDatabase;
import com.fjoglar.lyricly.data.source.local.entity.FavoriteSongEntity;
import com.fjoglar.lyricly.data.source.remote.SongsRemoteDataSource;

import java.util.List;

public class FavoriteSongsWidget extends AppWidgetProvider {

    // TODO: improve the widget to make it a listview with access to the song details.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SongsRepository repository =
                SongsRepository.getInstance(
                        SongsLocalDataSource.getInstance(
                                SongDatabase.getSyncInstance(context)),
                        SongsRemoteDataSource.getInstance()
                );

        CharSequence widgetText = formatSongList(repository.getWidgetSongs());
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_songs_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    
    public String formatSongList(List<FavoriteSongEntity> songs) {
        StringBuilder sb = new StringBuilder();
        for (FavoriteSongEntity song : songs) {
            sb.append(song.getName()).append(" by ").append(song.getArtistName()).append("\n");
        }
        return sb.toString();
    }
}

