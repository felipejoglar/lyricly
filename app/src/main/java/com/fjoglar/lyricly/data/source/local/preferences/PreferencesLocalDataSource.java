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

package com.fjoglar.lyricly.data.source.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fjoglar.lyricly.data.SongsDataSource;

/**
 * Helper class to make simple the use of SharedPreferences.
 */
public class PreferencesLocalDataSource implements SongsDataSource.PreferencesDataSource {

    private final static String LAST_UPDATED_TIME_KEY = "last_updated_time";

    private static volatile PreferencesLocalDataSource INSTANCE;

    private SharedPreferences mPreferences;

    // Prevent direct instantiation.
    private PreferencesLocalDataSource(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     */
    public static PreferencesLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PreferencesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PreferencesLocalDataSource(context);
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Used to force {@link #getInstance(Context)} to create a new instance next
     * time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public long getLastUpdatedTimeInMillis() {
        return mPreferences.getLong(LAST_UPDATED_TIME_KEY, 0);
    }

    @Override
    public void setLastUpdatedTimeInMillis() {
        mPreferences.edit().putLong(LAST_UPDATED_TIME_KEY, System.currentTimeMillis()).apply();
    }
}
