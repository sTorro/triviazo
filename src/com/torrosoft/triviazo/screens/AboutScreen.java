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
 * Shows the credits and licenses.
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.1
 */
public final class AboutScreen extends AbstractScreen {
    public AboutScreen(final TriviazoGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        game.getSoundManager().play(TriviazoSound.CHIMP);

        final Table table = super.getTable();
        final Texture img = new Texture(Gdx.files.internal("images/about.png"));
        final Drawable splashDrawable = new TextureRegionDrawable(new TextureRegion(
                img));
        table.setBackground(splashDrawable);
        table.row();
        table.pad(0F, 10F, 10F, 0F); // Bottom and left padding
        table.bottom().left();

        table.add(TriviazoGame.VERSION).spaceBottom(10);
        table.row();
        final TextButton returnButton = new TextButton("Volver al menú", getSkin());
        returnButton.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new MenuScreen(game));
            }
        });
        table.add(returnButton)
                .size(AbstractScreen.BUTTON_WIDTH, AbstractScreen.BUTTON_HEIGHT)
                .uniform()
                .spaceBottom(AbstractScreen.BUTTON_SPACING);
        table.row();
    }
}
