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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.services.music.TriviazoMusic;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * The game over screen. It has two different constructors for different game
 * mode.
 * 
 * @author Sergio Torró
 * @since 03/06/2013
 * @version 0.1
 */
public class GameOverScreen extends AbstractScreen {
    /**
     * The main table of the super class.
     */
    private Table mainTable;
    private final Integer correct;
    private final Integer incorrect;
    private Integer score;

    public GameOverScreen(final TriviazoGame game, final int correct,
            final int incorrect) {
        super(game);
        game.getMusicManager().play(TriviazoMusic.MAIN);
        this.correct = correct;
        this.incorrect = incorrect;
        score = null;
    }

    public GameOverScreen(final TriviazoGame game, final int score) {
        super(game);
        game.getMusicManager().play(TriviazoMusic.MAIN);
        this.score = score;
        correct = null;
        incorrect = null;
    }

    @Override
    public final void show() {
        super.show();
        game.getSoundManager().play(TriviazoSound.CHIMP);
        mainTable = super.getTable();

        final Texture texture = new Texture(
                Gdx.files.internal("images/game_over.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(
                texture));
        mainTable.setBackground(drawable);

        createButton();
        if (score == null) {
            createMessage();
        } else {
            createMessageForScore();
        }
        createFinalScore();
    }

    private void createMessage() {
        String message;
        final Table subTable = new Table(getSkin());
        subTable.setFillParent(true);

        if (incorrect == 0) {
            message = "Estas hecho un campeón, has acertado todas las preguntas!! Ook! Oook! Ooook!!";
        } else if (correct == 0) {
            message = "Conozco a chimpancés con más cultura e intelecto que tú, querido jugador.";
        } else if (correct == incorrect) {
            message = "Has fallado las mismas que has acertado, seguro que lo has hecho a posta.";
        } else if (correct > incorrect) {
            message = "Vas bien, pero es mejorable la cosa...";
        } else { // correct < incorrect
            message = "Has fallado más que acertado, eres una mente privilegiada.";
        }

        final Label lblMessage = new Label(message, getSkin());
        subTable.add(lblMessage);
        subTable.bottom().padBottom(100F);
        stage.addActor(subTable);
    }

    private void createMessageForScore() {
        String message;
        final Table subTable = new Table(getSkin());
        subTable.setFillParent(true);

        if (score == 0) {
            message = "Cero pelotero, eres una mente privilegiada.";
        } else if (score < 0) {
            message = "Conozco a chimpancés con más cultura e intelecto que tú, querido jugador.";
        } else if (score > 500) {
            message = "Estas hecho un campeón, mozo recio! Ook! Oook! Ooook!!";
        } else { // score > 0
            message = "No está mal la cosa, pero es mejorable.";
        }

        final Label lblMessage = new Label(message, getSkin());
        subTable.add(lblMessage);
        subTable.bottom().padBottom(100F);
        stage.addActor(subTable);
    }

    private void createFinalScore() {
        if (score == null) {
            score = (correct - incorrect) * 100;
            if (score < 0) {
                score = 0;
            }
        }

        game.getDatabase().addScoreForGameMode(gameConfig.getGameMode(), score);
        final Table scoreTable = new Table(getSkin());
        scoreTable.setFillParent(true);
        final Label lblScore = new Label("Puntuación final: " + score, getSkin());
        scoreTable.add(lblScore);
        scoreTable.bottom().left().pad(0, 10, 10, 0);
        stage.addActor(scoreTable);
    }

    private void createButton() {
        final Table tableButton = new Table(getSkin());
        tableButton.setFillParent(true);

        final TextButton btnGoToMainMenu = new TextButton(
                "Volver al menú principal", getSkin());

        btnGoToMainMenu.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new MenuScreen(game));
            }
        });

        tableButton.add(btnGoToMainMenu).size(BUTTON_WIDTH, BUTTON_HEIGHT);

        tableButton.bottom().right().pad(0, 0, 10, 10);
        stage.addActor(tableButton);
    }
}
