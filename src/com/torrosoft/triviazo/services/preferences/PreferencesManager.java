/*
 * This file is part of Triviazo project.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with triviazo-project.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013 Sergio Torró.
 */

package com.torrosoft.triviazo.services.preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Simple class for handles the game preferences.
 * 
 * @author Sergio Torró
 * @since 01/05/2013
 * @version 0.1
 */
public class PreferencesManager {
    private final Preferences preferences;

    /**
     * Default ctor.
     */
    public PreferencesManager() {
        preferences = Gdx.app.getPreferences(Preference.PREFS_NAME.getName());
    }

    public final boolean isSoundEnabled() {
        return preferences.getBoolean(Preference.SOUND_ENABLED.getName(), true);
    }

    public final void setSoundEnabled(final boolean soundEffectsEnabled) {
        preferences.putBoolean(Preference.SOUND_ENABLED.getName(),
                soundEffectsEnabled);
        preferences.flush();
    }

    public final boolean isMusicEnabled() {
        return preferences.getBoolean(Preference.MUSIC_ENABLED.getName(), true);
    }

    public final void setMusicEnabled(final boolean musicEnabled) {
        preferences.putBoolean(Preference.MUSIC_ENABLED.getName(), musicEnabled);
        preferences.flush();
    }

    public final float getVolume() {
        return preferences.getFloat(Preference.VOLUME.getName(), 0.5f);
    }

    public final void setVolume(final float volume) {
        preferences.putFloat(Preference.VOLUME.getName(), volume);
        preferences.flush();
    }
}
