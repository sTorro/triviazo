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

package com.torrosoft.triviazo.core;

import java.util.HashMap;
import java.util.Map;

import com.torrosoft.triviazo.core.enums.Difficulty;
import com.torrosoft.triviazo.core.enums.Discipline;
import com.torrosoft.triviazo.core.enums.GameMode;

/**
 * This class handles the game configuration. Parameters like the game mode or
 * that will be the questions about are present in this class.
 * 
 * @author Sergio Torró
 * @since 20/05/2013
 * @version 0.1
 */
public class CurrentGameConfig {
    private static final int DEFAULT_NUMBER_OF_QUESTIONS = 10;
    protected static final int DISCIPLINES_NUMBER = 9;
    private GameMode gameMode;
    private final Map<Discipline, Boolean> disciplines =
            new HashMap<Discipline, Boolean>(DISCIPLINES_NUMBER);

    /**
     * Total number of questions.
     */
    private int numQuestions = DEFAULT_NUMBER_OF_QUESTIONS;

    /**
     * Questions difficulty.
     */
    private Difficulty difQuestions = Difficulty.ALL;

    /**
     * Artificial Intelligence difficulty.
     */
    private Difficulty difAI = Difficulty.NORMAL;

    /**
     * Default constructor. It creates the map and adds the main disciplines
     * activated by default.
     */
    public CurrentGameConfig() {
        getDisciplines().put(Discipline.HISTORY, true);
        getDisciplines().put(Discipline.GEOGRAPHY, true);
        getDisciplines().put(Discipline.POLITICS, true);
        getDisciplines().put(Discipline.TECNOLOGY, true);
        getDisciplines().put(Discipline.PHYSICS, true);
        getDisciplines().put(Discipline.CHEMISTRY, true);
        getDisciplines().put(Discipline.LITERATURE, true);
        getDisciplines().put(Discipline.MUSIC, true);
        getDisciplines().put(Discipline.ART, true);

        gameMode = GameMode.INTELLECTUS_MACHINA;
    }

    public final GameMode getGameMode() {
        return gameMode;
    }

    public final void setGameMode(final GameMode pGameMode) {
        gameMode = pGameMode;
    }

    public final Map<Discipline, Boolean> getDisciplines() {
        return disciplines;
    }

    public final int getNumQuestions() {
        return numQuestions;
    }

    public final void setNumQuestions(final int pNumQuestions) {
        numQuestions = pNumQuestions;
    }

    public final Difficulty getDifQuestions() {
        return difQuestions;
    }

    public final void setDifQuestions(final Difficulty pDifQuestions) {
        difQuestions = pDifQuestions;
    }

    public final Difficulty getDifAI() {
        return difAI;
    }

    public final void setDifAI(final Difficulty pDifAI) {
        difAI = pDifAI;
    }
}
