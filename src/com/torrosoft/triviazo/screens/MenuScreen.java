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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * This class handles the main menu and its actions.
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.1
 */
public final class MenuScreen extends AbstractScreen {
    /* MAIN MENU STYLE CONSTANTS */
    private static final float PADDING_TOP = 10F;
    private static final float PADDING_LEFT = 0F;
    private static final float PADDING_BOTTOM = 0F;
    private static final float PADDING_RIGHT = 20F;

    public MenuScreen(final TriviazoGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        // retrieve the default table actor
        final Table table = super.getTable();
        final Texture img = new Texture(Gdx.files.internal("images/menu.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(img));
        table.setBackground(drawable);

        table.pad(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT);
        table.right().top(); // Position of the menu in the screen

        table.add(" ").spaceBottom(BUTTON_SPACING);
        table.row();

        // register the button "start game"
        final TextButton startGameButton = new TextButton("Un jugador",
                getSkin());
        startGameButton.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new GameModeScreen(game));
            }
        });
        table.add(startGameButton).size(BUTTON_WIDTH, BUTTON_HEIGHT).uniform()
                .spaceBottom(BUTTON_SPACING);
        table.row();

        final TextButton multiPlayerButton = new TextButton("Multijugador",
                getSkin());
        multiPlayerButton.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                // TODO multiPlayerButton
                // game.setScreen(new GameOverScreen(game, 5, 8));
                game.setScreen(new TempusFugit(game));
            }
        });
        // table.add(multiPlayerButton).size(BUTTON_WIDTH,
        // BUTTON_HEIGHT).uniform().spaceBottom(BUTTON_SPACING);
        // table.row();

        final TextButton hallOfFameButton = new TextButton("Hall of Fame", getSkin());
        hallOfFameButton.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new HallOfFameScreen(game));
            }
        });
        table.add(hallOfFameButton).size(BUTTON_WIDTH, BUTTON_HEIGHT).uniform()
                .spaceBottom(BUTTON_SPACING);
        table.row();

        final TextButton optionsButton = new TextButton("Preferencias", getSkin());
        optionsButton.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new PreferencesScreen(game));
            }
        });
        table.add(optionsButton).uniform().fill().spaceBottom(BUTTON_SPACING);
        table.row();

        final TextButton creditsButton = new TextButton("Créditos", getSkin());
        creditsButton.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new AboutScreen(game));
            }
        });
        table.add(creditsButton).uniform().fill();
    }
}
