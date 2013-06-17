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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.services.music.TriviazoMusic;
import com.torrosoft.triviazo.services.music.TriviazoSound;

/**
 * This class handles the screen for the game configuration.
 * 
 * @author Sergio Torró
 * @since 09/05/2013
 * @version 0.3
 */
public final class PreferencesScreen extends AbstractScreen {
    private Label volumeValue;

    public PreferencesScreen(final TriviazoGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        final Table table = super.getTable();
        final Texture img = new Texture(
                Gdx.files.internal("images/preferencias.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(img));

        table.setBackground(drawable);
        table.defaults().spaceBottom(30F);
        table.columnDefaults(0).padRight(20F);

        final Texture chCheckedTex = new Texture(
                Gdx.files.internal("images/buttons/chChecked.png"));
        final Texture chUncheckedTex = new Texture(
                Gdx.files.internal("images/buttons/chUnchecked.png"));
        final Drawable chCheckedDraw = new TextureRegionDrawable(new TextureRegion(
                chCheckedTex));
        final Drawable chUncheckedDraw = new TextureRegionDrawable(
                new TextureRegion(chUncheckedTex));
        final ImageButton.ImageButtonStyle checkStyle = new ImageButton.ImageButtonStyle(
                chUncheckedDraw, chCheckedDraw, chCheckedDraw, chUncheckedDraw,
                chCheckedDraw,
                chCheckedDraw);

        final ImageButton chSoundEffects = new ImageButton(checkStyle);
        chSoundEffects.setChecked(game.getPreferencesManager().isSoundEnabled());
        chSoundEffects.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                final boolean enabled = chSoundEffects.isChecked();
                game.getPreferencesManager().setSoundEnabled(enabled);
                game.getSoundManager().setEnabled(enabled);
                game.getSoundManager().play(TriviazoSound.CLICK);
            }
        });
        table.row();
        table.add("Efectos de sonido");
        table.add(chSoundEffects).colspan(2).left();

        final ImageButton chMusic = new ImageButton(checkStyle);
        chMusic.setChecked(game.getPreferencesManager().isMusicEnabled());
        chMusic.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                final boolean enabled = chMusic.isChecked();
                game.getPreferencesManager().setMusicEnabled(enabled);
                game.getMusicManager().setEnabled(enabled);
                game.getSoundManager().play(TriviazoSound.CLICK);
                // if the music is now enabled, start playing the menu music
                game.getMusicManager().play(TriviazoMusic.MAIN);
            }
        });
        table.row();
        table.add("Música");
        table.add(chMusic).colspan(2).left();

        // range is [0.0,1.0]; step is 0.1f
        // TODO bigger slider
        final Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, getSkin());
        volumeSlider.setValue(game.getPreferencesManager().getVolume());
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                final float value = ((Slider) actor).getValue();
                game.getPreferencesManager().setVolume(value);
                game.getMusicManager().setVolume(value);
                game.getSoundManager().setVolume(value);
                updateVolumeLabel();
            }
        });

        // create the volume label
        volumeValue = new Label("", getSkin());
        updateVolumeLabel();
        // add the volume row
        table.row();
        table.add("Volumen");
        table.add(volumeSlider);
        table.add(volumeValue).width(40);

        // register the back button
        final TextButton backButton = new TextButton("Volver al menú", getSkin());

        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new MenuScreen(game));
            }

            @Override
            public boolean touchDown(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                return true;
            }

        });
        table.row();
        table.add(" ");
        table.row();
        table.add(backButton).size(250, 60).colspan(3);
        table.padTop(100F);
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel() {
        final int volume = (int) (game.getPreferencesManager().getVolume() * 100);
        volumeValue.setText(volume + "%");
    }
}
