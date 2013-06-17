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

import com.badlogic.gdx.audio.Music;

/**
 * Enumeration for control the available musics files.
 * 
 * @author Sergio Torró
 * @since 01/05/2013
 * @version 0.1
 */
public enum TriviazoMusic {
    /**
     * Creative Commons License: http://ccmixter.org/files/flatwound/14476
     */
    MAIN("music/game_music_main.mp3"),
    /**
     * Creative Commons License: http://ccmixter.org/files/duncan_beattie/7116
     */
    IN_GAME("music/game_music_ii.mp3"),
    /**
     * Creative Commons License: http://freemusicarchive.org/music/The_Insider/
     */
    TENSION_IS_RISING("music/tension_is_rising.mp3");

    private String fileName;
    private Music musicResource;

    private TriviazoMusic(final String pFileName) {
        fileName = pFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public Music getMusicResource() {
        return musicResource;
    }

    public void setMusicResource(final Music musicBeingPlayed) {
        musicResource = musicBeingPlayed;
    }
}
