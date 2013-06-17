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

/**
 * Enumeration that controls the different preferences of the game.
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.1
 */
public enum Preference {
    PREFS_NAME("triviazo"),
    VOLUME("volume"),
    MUSIC_ENABLED("music.enabled"),
    SOUND_ENABLED("sound.enable");

    private String name;

    private Preference(final String pName) {
        name = pName;
    }

    public String getName() {
        return name;
    }
}
