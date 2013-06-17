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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.services.music.TriviazoMusic;
import com.torrosoft.triviazo.services.music.TriviazoSound;

/**
 * This class handles the splash screen that contains two images and some
 * actions like a fade in, a delay...
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.2
 */
public final class SplashScreen extends AbstractScreen {
    private Image splashImage;

    public SplashScreen(final TriviazoGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        game.getSoundManager().play(TriviazoSound.CHIMP);
        final Texture img = new Texture(Gdx.files.internal("images/splash1.png"));
        final Drawable splashDrawable = new TextureRegionDrawable(new TextureRegion(
                img));

        // here we create the splash image actor; its size is set when the
        // resize() method gets called
        splashImage = new Image(splashDrawable, Scaling.stretch);
        splashImage.setFillParent(true);

        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        splashImage.getColor().a = 0f;

        // configure the fade-in/out effect on the splash image
        splashImage.addAction(sequence(fadeIn(0.75F), delay(2F), fadeOut(0.75F),
                new Action() {
                    @Override
                    public boolean act(final float delta) {
                        // Now I change the splash image and repeat the process
                        game.getMusicManager().play(TriviazoMusic.MAIN);
                        final Texture img = new Texture(Gdx.files
                                .internal("images/splash2.png"));
                        splashImage.setDrawable(new TextureRegionDrawable(
                                new TextureRegion(img)));
                        splashImage.addAction(sequence(fadeIn(0.75F), delay(2F),
                                fadeOut(0.75F),
                                new Action() {
                                    @Override
                                    public boolean act(final float delta) {
                                        // Finally go to the menu screen
                                        game.setScreen(new MenuScreen(game));
                                        return false;
                                    }
                                }));

                        return true;
                    }
                }));

        // and finally we add the actor to the stage
        stage.addActor(splashImage);
    }
}
