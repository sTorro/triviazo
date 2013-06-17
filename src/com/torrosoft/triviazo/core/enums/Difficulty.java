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

package com.torrosoft.triviazo.core.enums;

/**
 * The game and IA difficulty.
 * 
 * @author Sergio Torró
 * @since 20/05/2013
 * @version 0.3
 */
public enum Difficulty {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard"),
    INSANE("Insane"), // Only used for IA
    ALL("All"); // Only used for questions

    private String name;

    private Difficulty(final String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public static Difficulty getDifficultyByName(final String name) {
        Difficulty res = null;
        for (final Difficulty d : Difficulty.values()) {
            if (d.getName().equals(name)) {
                res = d;
                break;
            }
        }

        return res;
    }
}
