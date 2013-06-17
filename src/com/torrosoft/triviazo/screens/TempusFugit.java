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

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.services.music.TriviazoMusic;

/**
 * Game Screen for Tempus Fugit game mode.
 * 
 * @author Sergio Torró
 * @since 05/06/2013
 * @version 0.1
 */
public class TempusFugit extends GameScreen {
    private static final int SECONDS_PER_QUESTION = 15;
    private static final int MIN_SECONDS = 5;
    private int seconds = SECONDS_PER_QUESTION;
    private int score;
    private final Timer timer;
    private final float delaySeconds = 0F; // Delay before start
    private float intervalSeconds = 0.9F;

    public TempusFugit(final TriviazoGame game) {
        super(game);
        game.getMusicManager().play(TriviazoMusic.TENSION_IS_RISING);
        timer = new Timer();
        timer.scheduleTask(task, delaySeconds, intervalSeconds);
        timer.start();
    }

    @Override
    public final void setScore(final Integer sco) {
        score = (correct * QUESTION_VALUE) - (incorrect * QUESTION_VALUE);
        lblCorrect.setText("");
        lblIncorrect.setText("Puntuación: " + score);
    }

    @Override
    public final void gameOver() {
        timer.stop();
        game.setScreen(new GameOverScreen(game, score));
    }

    private final Task task = new Task() {
        @Override
        public void run() {
            seconds -= 1;
            lblTimer.setText("Tiempo: " + seconds);
            if (seconds <= 0) {
                timer.stop();
                cancel();
                gameOver();
            }
        }
    };

    @Override
    public final void checkQuestions(final int resp) {
        if (questions.get(index).getRight() == resp) correct += 1;
        else incorrect += 1;
        seconds = SECONDS_PER_QUESTION - (correct + incorrect) - 1;
        if (seconds < MIN_SECONDS) seconds = MIN_SECONDS;
        if (intervalSeconds > 0.1F) intervalSeconds -= 0.1F;
    }
}
