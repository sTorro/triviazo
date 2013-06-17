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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.core.CurrentGameConfig;

/**
 * This class describes common fields and methods of each {@link Screen} that
 * contains the game. It is only possible view one screen at a time.
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.1
 */
public class AbstractScreen implements Screen {
    protected static final float BUTTON_WIDTH = 300F;
    protected static final float BUTTON_HEIGHT = 55F;
    protected static final float BUTTON_SPACING = 5F;

    /**
     * Fixed viewport constant for width dimension.
     */
    private static final int VIRTUAL_WIDTH = Gdx.graphics.getWidth();

    /**
     * Fixed viewport height dimension.
     */
    private static final int VIRTUAL_HEIGHT = Gdx.graphics.getHeight();

    /**
     * Aspect ratio holds the ratio width/height of the device screen (physical
     * resolution).
     */
    protected static final float ASPECT_RATIO =
            (float) VIRTUAL_WIDTH / VIRTUAL_HEIGHT;

    /**
     * The main class of the game.
     */
    protected final TriviazoGame game;

    /**
     * A 2D scene graph containing hierarchies of actors. Stage handles the
     * viewport and distributes input events.
     */
    protected final Stage stage = new Stage();

    /**
     * It controls the menu shape.
     */
    private Table table;
    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;

    protected static final CurrentGameConfig gameConfig = new CurrentGameConfig();

    /**
     * The main constructor.
     * 
     * @param game
     *            The game class which calls the Screen.
     */
    public AbstractScreen(final TriviazoGame game) {
        this.game = game;
    }

    protected final String getName() {
        return getClass().getSimpleName();
    }

    protected final boolean isGameScreen() {
        return false;
    }

    public final BitmapFont getFont() {
        if (font == null) {
            font = new BitmapFont();
        }

        return font;
    }

    public final SpriteBatch getBatch() {
        if (batch == null) {
            batch = new SpriteBatch();
        }

        return batch;
    }

    protected final Skin getSkin() {
        if (skin == null) {
            final FileHandle skinFile = Gdx.files.internal("skin/uiskin.json");

            skin = new Skin(skinFile);
        }

        return skin;
    }

    protected final Table getTable() {
        if (table == null) {
            table = new Table(getSkin());
            table.setFillParent(true);

            stage.addActor(table);
        }

        return table;
    }

    /** {@inheritDoc} */
    @Override
    public final void render(final float delta) {
        // process the game logic, update the actors
        stage.act(delta);

        // draw the result, clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw the actors
        stage.draw();
    }

    /**
     * {@inheritDoc}
     * 
     * NOTE: If the window it's resized, the viewport will adjusted.
     */
    @Override
    public void resize(final int width, final int height) {
        stage.setViewport(width, height, true);
    }

    /** {@inheritDoc} */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /** {@inheritDoc} */
    @Override
    public final void hide() {
        dispose();
    }

    /** {@inheritDoc} */
    @Override
    public void pause() {
        // TODO pause() if needed
    }

    /** {@inheritDoc} */
    @Override
    public void resume() {
        // TODO resume() if needed
    }

    /** {@inheritDoc} */
    @Override
    public final void dispose() {
        if (font != null) {
            font.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (skin != null) {
            skin.dispose();
            // if (stage != null) stage.dispose();
        }
    }
}
