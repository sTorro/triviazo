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
 * The different game modes.
 * 
 * @author Sergio Torró
 * @since 19/05/2013
 * @version 0.2
 */
public enum GameMode {
    INTELLECTUS_MACHINA("Intellectus machina", "images/machina_back.png", "machina"),
    TEMPUS_FUGIT("Tempus fugit", "images/tempus_back.png", "tempus"),
    VINDICETIS_EX_SIMIUS("Vindicetis ex simius", "images/simius_back.png", "simius");

    private String name;
    private String textImg;
    private String dbName;

    private GameMode(final String name, final String textImg, final String dbName) {
        setName(name);
        setTextImg(textImg);
        setDbName(dbName);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTextImg() {
        return textImg;
    }

    public void setTextImg(final String textImg) {
        this.textImg = textImg;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(final String dbName) {
        this.dbName = dbName;
    }
}
