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
 * The possible languages of the questions.
 * 
 * @author Sergio Torró
 * @since 25/05/2013
 * @version 0.1
 */
public enum Language {
    es_ES("Spanish"),
    en_GB("English"),
    fr_FR("French"),
    de_DE("German");

    private String name;

    private Language(final String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public static Language getLanguageByName(final String name) {
        Language res = null;
        for (final Language lang : Language.values()) {
            if (lang.getName().equals(name)) {
                res = lang;
                break;
            }
        }
        return res;
    }
}
