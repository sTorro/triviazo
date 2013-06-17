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

package com.torrosoft.triviazo.services.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Service for the background music of the game (streaming). It uses native
 * components and must be disposable manually (the JVM garbage collector only
 * works with Java components, not native).
 * <p>
 * Only one music may be playing at a given time.
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.1
 */
public class MusicManager implements Disposable {
    /**
     * The volume to be set on the music.
     */
    private float volume = 1f;

    /**
     * Whether the music is enabled.
     */
    private boolean enabled = true;

    /**
     * Holds the music currently being played, if any.
     */
    private TriviazoMusic musicBeingPlayed;

    /**
     * Creates the music manager.
     */
    public MusicManager() {}

    /**
     * Plays the given music (starts the streaming).
     * <p>
     * If there is already a music being played it is stopped automatically.
     */
    public final void play(final TriviazoMusic music) {
        // check if the music is enabled
        if (enabled) {
            // check if the given music is already being played
            if (musicBeingPlayed != music) {
                // stop any music being played
                stop();

                // start streaming the new music
                final FileHandle musicFile = Gdx.files.internal(music.getFileName());
                final Music musicResource = Gdx.audio.newMusic(musicFile);

                musicResource.setVolume(volume);
                musicResource.setLooping(true);
                musicResource.play();

                // set the music being played
                musicBeingPlayed = music;
                musicBeingPlayed.setMusicResource(musicResource);
            }
        }
    }

    /**
     * Stops and disposes the current music being played, if any.
     */
    public final void stop() {
        if (musicBeingPlayed != null) {
            final Music musicResource = musicBeingPlayed.getMusicResource();

            musicResource.stop();
            musicResource.dispose();
            musicBeingPlayed = null;
        }
    }

    /**
     * Sets the music volume which must be inside the range [0,1].
     */
    public final void setVolume(final float pVolume) {
        // check and set the new volume
        if ((pVolume < 0) || (pVolume > 1F)) { throw new IllegalArgumentException(
                "The volume must be inside the range: [0, 1]"); }

        volume = pVolume;

        // if there is a music being played, change its volume
        if (musicBeingPlayed != null) {
            musicBeingPlayed.getMusicResource().setVolume(pVolume);
        }
    }

    /**
     * Enables or disabled the music.
     */
    public final void setEnabled(final boolean pEnabled) {
        enabled = pEnabled;

        // if the music is being deactivated, stop any music being played
        if (!pEnabled) stop();
    }

    /**
     * Disposes the music manager.
     */
    @Override
    public final void dispose() {
        stop();
    }
}
