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

package com.torrosoft.triviazo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.torrosoft.triviazo.core.data.Database;
import com.torrosoft.triviazo.screens.MenuScreen;
import com.torrosoft.triviazo.screens.SplashScreen;
import com.torrosoft.triviazo.services.music.MusicManager;
import com.torrosoft.triviazo.services.music.SoundManager;
import com.torrosoft.triviazo.services.preferences.PreferencesManager;

/**
 * The game's main class, called as application events are fired. It centralizes
 * the entire game logic.
 * 
 * @author Sergio Torró
 * @since 01/05/2013
 * @version 0.1
 */
public class TriviazoGame extends Game {
    public static final String VERSION = " - v0.7 ALPHA - ";
    public static final boolean DEBUG_MODE = false;
    public static final boolean FULL_DEBUG_MODE = false;

    /* SERVICES */
    private MusicManager musicManager;
    private SoundManager soundManager;
    private PreferencesManager preferencesManager;

    /**
     * The game database.
     */
    private Database database;

    /**
     * Default constructor.
     */
    public TriviazoGame(final Database pDatabase) {
        setDatabase(pDatabase);
    }

    /**
     * Fired once when the application is created.
     */
    @Override
    public final void create() {
        preferencesManager = new PreferencesManager();

        musicManager = new MusicManager();
        musicManager.setVolume(preferencesManager.getVolume());
        musicManager.setEnabled(preferencesManager.isMusicEnabled());

        soundManager = new SoundManager();
        soundManager.setVolume(preferencesManager.getVolume());
        soundManager.setEnabled(preferencesManager.isSoundEnabled());

        // Mouse hidden
        Gdx.input.setCursorCatched(false);
    }

    /**
     * Fired every time the game screen is re-sized and the game is not in the
     * paused state. It is also fired once just after the create event.
     */
    @Override
    public final void resize(final int width, final int height) {
        super.resize(width, height);
        if (getScreen() == null) {
            if (DEBUG_MODE) {
                setScreen(new MenuScreen(this));
            } else setScreen(new SplashScreen(this)); // new SplashScreen(this)
        }
    }

    /**
     * Fired when the application is being destroyed. It is preceded by the
     * pause event.
     */
    @Override
    public final void dispose() {
        super.dispose();
        // It's necessary dispose some native methods.
        musicManager.dispose();
        soundManager.dispose();
    }

    /**
     * Fired just before the application is destroyed. On Android it happens
     * when the home button is pressed or an incoming call is happening. On
     * desktop it's fired just before disposal when exiting the application.
     * This a good time to save the game state on Android as it is not
     * guaranteed to be resumed.
     */
    @Override
    public final void pause() {
        super.pause();
    }

    /**
     * Fired ONLY on Android, when the application receives the focus.
     */
    @Override
    public final void resume() {
        super.resume();
    }

    /**
     * Fired by the game loop from the application every time the rendering
     * happens. The game update should take place before the actual rendering.
     */
    @Override
    public final void render() {
        super.render();
    }

    /**
     * Switch between the different screens.
     */
    @Override
    public final void setScreen(final Screen screen) {
        super.setScreen(screen);
    }

    public final PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    public final void setPreferencesManager(
            final PreferencesManager pPreferencesManager) {
        preferencesManager = pPreferencesManager;
    }

    public final SoundManager getSoundManager() {
        return soundManager;
    }

    public final void setSoundManager(final SoundManager pSoundManager) {
        soundManager = pSoundManager;
    }

    public final MusicManager getMusicManager() {
        return musicManager;
    }

    public final void setMusicManager(final MusicManager pMusicManager) {
        musicManager = pMusicManager;
    }

    public final Database getDatabase() {
        return database;
    }

    public final void setDatabase(final Database pDatabase) {
        database = pDatabase;
    }
}
