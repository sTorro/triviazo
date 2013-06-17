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

package com.torrosoft.triviazo.screens;

import com.torrosoft.triviazo.TriviazoGame;

/**
 * Game Screen for Intellectus Machina game mode.
 * 
 * @author Sergio Torró
 * @since 25/05/2013
 * @version 0.2
 */
public class IntellectusMachina extends GameScreen {

    public IntellectusMachina(final TriviazoGame game) {
        super(game);
    }

    @Override
    public final void setScore(final Integer sco) {
        lblCorrect.setText("Correctas: " + correct);
        lblIncorrect.setText("Incorrectas: " + incorrect);
    }

    @Override
    public final void gameOver() {
        game.setScreen(new GameOverScreen(game, correct, incorrect));
    }

    @Override
    public final void checkQuestions(final int resp) {
        if (questions.get(index).getRight() == resp) correct += 1;
        else incorrect += 1;
    }
}
