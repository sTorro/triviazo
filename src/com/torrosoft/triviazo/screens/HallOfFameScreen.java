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
import com.torrosoft.triviazo.core.enums.GameMode;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * This class handles the interface for the score and the records.
 * 
 * @author Sergio Torró
 * @since 04/06/2013
 * @version 0.1
 */
public final class HallOfFameScreen extends AbstractScreen {
    public HallOfFameScreen(final TriviazoGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        final Table table = super.getTable();
        final Texture img = new Texture(
                Gdx.files.internal("images/hall_of_fame.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(img));
        table.setBackground(drawable);

        createButtonsTable();
        createScoreBoard();
    }

    private void createScoreBoard() {
        // TODO text size...
        final Table tableScore = new Table(getSkin());
        tableScore.setFillParent(true);

        for (final GameMode gm : GameMode.values()) {
            final String str = gm.getName() + " --> "
                    + game.getDatabase().getScoreForGameMode(gm);
            final Label lblGameMode = new Label(str, getSkin());
            tableScore.add(lblGameMode);
            tableScore.row();
        }

        stage.addActor(tableScore);
    }

    private void createButtonsTable() {
        final Table tableButtonBack = new Table(getSkin());
        tableButtonBack.setFillParent(true);
        final TextButton btnBack = new TextButton("Volver al menú", getSkin());

        btnBack.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new MenuScreen(game));
            }
        });

        tableButtonBack.add(btnBack).size(BUTTON_WIDTH, BUTTON_HEIGHT);
        tableButtonBack.bottom().right().pad(0, 0, 10, 10);
        stage.addActor(tableButtonBack);
    }
}
