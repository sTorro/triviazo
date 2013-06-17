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

/**
 * Enumeration for control the available sound files.
 * 
 * @author Sergio Torró
 * @since 09/05/2013
 * @version 0.1
 */
public enum TriviazoSound {
    CLICK("sounds/click.wav"),
    TICK("sounds/tick.wav"),
    CHIMP("sounds/chimp.wav");

    private final String fileName;

    private TriviazoSound(final String pFileName) {
        fileName = pFileName;
    }

    public String getFileName() {
        return fileName;
    }
}
