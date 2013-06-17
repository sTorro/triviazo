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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.core.enums.GameMode;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * This class handles the game mode selection with its user interface.
 * 
 * @author Sergio Torró
 * @since 18/05/2013
 * @version 0.5
 */
public final class GameModeScreen extends AbstractScreen {
    /**
     * The main skin used by all the UI components.
     */
    private final Skin mainSkin = super.getSkin();

    /**
     * The main table of the super class.
     */
    private Table mainTable;

    /**
     * Another table to handle the game mode buttons.
     */
    private Table tableGameModes;
    private ImageButton btnMachina;
    private ImageButton btnSimius;
    private ImageButton btnTempus;

    /**
     * Default ctor.
     * 
     * @param game
     *            The parent game object.
     */
    public GameModeScreen(final TriviazoGame game) {
        super(game);
    }

    /**
     * The method for show the UI. It handles the main {@link Table}.
     */
    @Override
    public void show() {
        super.show();
        mainTable = super.getTable();

        // Create UI
        createGameModesTable();
        createButtonsTable();

        // Default game mode background
        // if (gameConfig.getGameMode())
        if (gameConfig.getGameMode() == GameMode.VINDICETIS_EX_SIMIUS) {
            btnSimius
                    .setChecked(true);
        } else if (gameConfig.getGameMode() == GameMode.TEMPUS_FUGIT) {
            btnTempus
                    .setChecked(true);
        } else {
            btnMachina.setChecked(true);
        }

        changeBackground(gameConfig.getGameMode());
    }

    /**
     * This method creates the buttons of the bottom and its listeners.
     */
    private void createButtonsTable() {
        final Table tableButtonNext = new Table(mainSkin);
        final Table tableButtonBack = new Table(mainSkin);
        if (TriviazoGame.FULL_DEBUG_MODE) {
            tableButtonNext.debug();
        }
        tableButtonNext.setFillParent(true);
        if (TriviazoGame.FULL_DEBUG_MODE) {
            tableButtonBack.debug();
        }
        tableButtonBack.setFillParent(true);

        final TextButton btnNext = new TextButton("Siguiente   >>", getSkin());
        final TextButton btnBack = new TextButton("<<   Atrás", getSkin());

        btnNext.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new DisciplinesScreen(game));
            }
        });

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

        tableButtonNext.add(btnNext).size(BUTTON_WIDTH, BUTTON_HEIGHT);
        tableButtonBack.add(btnBack).size(BUTTON_WIDTH, BUTTON_HEIGHT);

        // pad(top, left, bottom, right)
        tableButtonNext.bottom().right().pad(0, 0, 10, 10);
        tableButtonBack.bottom().left().pad(0, 10, 10, 0);
        stage.addActor(tableButtonNext);
        stage.addActor(tableButtonBack);
    }

    /**
     * This method creates the buttons for select the game mode and its
     * listeners.
     */
    private void createGameModesTable() {
        tableGameModes = new Table(mainSkin);
        if (TriviazoGame.FULL_DEBUG_MODE) {
            tableGameModes.debug();
        }
        tableGameModes.setFillParent(true);

        // Image buttons instantiation
        final Texture machinaUp = new Texture(
                Gdx.files.internal("images/buttons/machina_btn_up.png"));
        final Texture machinaDown = new Texture(
                Gdx.files.internal("images/buttons/machina_btn_down.png"));
        final Texture simiusUp = new Texture(
                Gdx.files.internal("images/buttons/simius_btn_up.png"));
        final Texture simiusDown = new Texture(
                Gdx.files.internal("images/buttons/simius_btn_down.png"));
        final Texture tempusUp = new Texture(
                Gdx.files.internal("images/buttons/tempus_btn_up.png"));
        final Texture tempusDown = new Texture(
                Gdx.files.internal("images/buttons/tempus_btn_down.png"));

        // Drawable objects
        final Drawable machinaDrawableUp = new TextureRegionDrawable(
                new TextureRegion(machinaUp));
        final Drawable machinaDrawableDown = new TextureRegionDrawable(
                new TextureRegion(
                        machinaDown));
        final Drawable simiusDrawableUp = new TextureRegionDrawable(
                new TextureRegion(simiusUp));
        final Drawable simiusDrawableDown = new TextureRegionDrawable(
                new TextureRegion(simiusDown));
        final Drawable tempusDrawableUp = new TextureRegionDrawable(
                new TextureRegion(tempusUp));
        final Drawable tempusDrawableDown = new TextureRegionDrawable(
                new TextureRegion(tempusDown));

        // Styles for the buttons
        final ImageButton.ImageButtonStyle machinaStyle = new ImageButton.ImageButtonStyle(
                machinaDrawableUp, machinaDrawableDown, machinaDrawableDown,
                machinaDrawableUp,
                machinaDrawableDown, machinaDrawableDown);
        final ImageButton.ImageButtonStyle simiusStyle = new ImageButton.ImageButtonStyle(
                simiusDrawableUp, simiusDrawableDown, simiusDrawableDown,
                simiusDrawableUp,
                simiusDrawableDown, simiusDrawableDown);
        final ImageButton.ImageButtonStyle tempusStyle = new ImageButton.ImageButtonStyle(
                tempusDrawableUp, tempusDrawableDown, tempusDrawableDown,
                tempusDrawableUp,
                tempusDrawableDown, tempusDrawableDown);

        btnMachina = new ImageButton(machinaStyle);
        btnSimius = new ImageButton(simiusStyle);
        btnTempus = new ImageButton(tempusStyle);

        // Listeners
        btnMachina.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnSimius.isChecked() && !btnTempus.isChecked()) {
                    btnMachina.setChecked(true);
                } else {
                    game.getSoundManager().play(TriviazoSound.TICK);
                    btnSimius.setChecked(false);
                    btnTempus.setChecked(false);
                    changeBackground(GameMode.INTELLECTUS_MACHINA);
                }
            }
        });
        btnSimius.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnMachina.isChecked() && !btnTempus.isChecked()) {
                    btnSimius.setChecked(true);
                } else {
                    btnMachina.setChecked(false);
                    btnTempus.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    changeBackground(GameMode.VINDICETIS_EX_SIMIUS);
                }
            }
        });
        btnTempus.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnMachina.isChecked() && !btnSimius.isChecked()) {
                    btnTempus.setChecked(true);
                } else {
                    btnMachina.setChecked(false);
                    btnSimius.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    changeBackground(GameMode.TEMPUS_FUGIT);
                }
            }
        });

        // Creating the table
        tableGameModes.add(btnMachina);
        tableGameModes.row();
        tableGameModes.add(" ");
        tableGameModes.row();
        tableGameModes.add(btnTempus);
        tableGameModes.row();
        tableGameModes.add(" ");
        tableGameModes.row();
        tableGameModes.add(btnSimius);

        stage.addActor(tableGameModes);
    }

    /**
     * It changes the background image relative to the game mode selected.
     * 
     * @param gameMode
     *            The game mode.
     */
    private void changeBackground(final GameMode gameMode) {
        final Texture texture = new Texture(
                Gdx.files.internal(gameMode.getTextImg()));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(
                texture));
        mainTable.setBackground(drawable);
        gameConfig.setGameMode(gameMode);
    }

    /**
     * Adjusting the UI relative to screen width.
     */
    @Override
    public void resize(final int width, final int height) {
        super.resize(width, height);

        float leftPadding = width / 10;
        if (leftPadding < 100) {
            leftPadding /= (ASPECT_RATIO * 1.5F); // 3
        }
        tableGameModes.center().left().pad(0, leftPadding, 20, 0);
    }
}
