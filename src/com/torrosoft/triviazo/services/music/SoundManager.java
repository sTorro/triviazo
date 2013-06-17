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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.torrosoft.triviazo.util.LRUCache;
import com.torrosoft.triviazo.util.LRUCache.CacheEntryRemovedListener;

/**
 * This class handles the sounds like clicks. It should cache the loaded sounds
 * in order to improve performance by avoiding excessive IO reads. An
 * implementation of LRU (Least Recently Used) cache would do the job. See
 * {@link LRUCache} class.
 * 
 * @author Sergio Torró
 * @since 01/05/2013
 * @version 0.1
 */
public class SoundManager implements
        CacheEntryRemovedListener<TriviazoSound, Sound>, Disposable {
    /**
     * The volume of the sound.
     */
    private float volume = 1F;

    /**
     * If the sound is enabled or not.
     */
    private boolean enabled = true;

    /**
     * The sound cache.
     */
    private final LRUCache<TriviazoSound, Sound> soundCache;

    /**
     * Default ctor. that creates the sound manager.
     */
    public SoundManager() {
        soundCache = new LRUCache<TriviazoSound, Sound>(10);
        soundCache.setEntryRemovedListener(this);
    }

    /**
     * Plays the specified sound.
     */
    public final void play(final TriviazoSound sound) {
        if (enabled) {
            // Try and get the sound from the cache
            Sound soundToPlay = soundCache.get(sound);

            if (soundToPlay == null) {
                // If the cache doesn't have the sound, reads it from disk.
                final FileHandle soundFile = Gdx.files.internal(sound.getFileName());
                soundToPlay = Gdx.audio.newSound(soundFile);
                soundCache.add(sound, soundToPlay);
            }

            soundToPlay.play(volume);
        }
    }

    @Override
    public final void dispose() {
        for (final Sound sound : soundCache.retrieveAll()) {
            sound.stop();
            sound.dispose();
        }
    }

    @Override
    public final void notifyEntryRemoved(final TriviazoSound key, final Sound value) {
        value.dispose();
    }

    public final float getVolume() {
        return volume;
    }

    /**
     * Must be inside the range [0,1].
     * 
     * @param volume
     */
    public final void setVolume(final float pVolume) {
        // check and set the new volume
        if ((pVolume < 0) || (pVolume > 1f)) { throw new IllegalArgumentException(
                "The volume must be inside the range: [0,1]"); }

        volume = pVolume;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(final boolean pEnabled) {
        enabled = pEnabled;
    }
}
