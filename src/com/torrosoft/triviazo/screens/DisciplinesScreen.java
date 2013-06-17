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
import com.torrosoft.triviazo.core.enums.Discipline;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * This class handles the user interface of the discipline selection.
 * 
 * @author Sergio Torró
 * @since 18/05/2013
 * @version 0.1
 */
public class DisciplinesScreen extends AbstractScreen {
    private static final int MINIMUM_SELECTED = 3;
    /**
     * The main skin used by all the UI components.
     */
    private final Skin mainSkin;

    /**
     * The main table of the super class.
     */
    private Table mainTable;

    private ImageButton btnHistory;
    private ImageButton btnGeography;
    private ImageButton btnPolitics;
    private ImageButton btnTechnology;
    private ImageButton btnPhysics;
    private ImageButton btnChemistry;
    private ImageButton btnLiterature;
    private ImageButton btnMusic;
    private ImageButton btnArt;

    private int selectedCount = 0;

    public DisciplinesScreen(final TriviazoGame game) {
        super(game);
        mainSkin = super.getSkin();
    }

    /**
     * The method for show the UI. It handles the main {@link Table}.
     */
    @Override
    public void show() {
        super.show();
        mainTable = super.getTable();

        final Texture texture = new Texture(
                Gdx.files.internal("images/disciplines.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(
                texture));
        mainTable.setBackground(drawable);

        // Create UI
        createButtonsTable();
        createDisciplinesTable();

        if (gameConfig.getDisciplines().get(Discipline.HISTORY)) {
            btnHistory.setChecked(true);
            selectedCount += 1;
        } else btnHistory.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.GEOGRAPHY)) {
            btnGeography.setChecked(true);
            selectedCount += 1;
        } else btnGeography.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.POLITICS)) {
            btnPolitics.setChecked(true);
            selectedCount += 1;
        } else btnPolitics.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.TECNOLOGY)) {
            btnTechnology.setChecked(true);
            selectedCount += 1;
        } else btnTechnology.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.CHEMISTRY)) {
            btnChemistry.setChecked(true);
            selectedCount += 1;
        } else btnChemistry.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.PHYSICS)) {
            btnPhysics.setChecked(true);
            selectedCount += 1;
        } else btnPhysics.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.LITERATURE)) {
            btnLiterature.setChecked(true);
            selectedCount += 1;
        } else btnLiterature.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.MUSIC)) {
            btnMusic.setChecked(true);
            selectedCount += 1;
        } else btnMusic.setChecked(false);

        if (gameConfig.getDisciplines().get(Discipline.ART)) {
            btnArt.setChecked(true);
            selectedCount += 1;
        } else btnArt.setChecked(false);
    }

    /**
     * This method creates the buttons of the bottom and its listeners.
     */
    private void createButtonsTable() {
        final Table tableButtonNext = new Table(mainSkin);
        final Table tableButtonBack = new Table(mainSkin);
        tableButtonNext.setFillParent(true);
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
                setMapValues();
                game.setScreen(new LastConfigScreen(game));
            }
        });

        btnBack.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                setMapValues();
                game.setScreen(new GameModeScreen(game));
            }
        });

        tableButtonNext.add(btnNext).size(BUTTON_WIDTH, BUTTON_HEIGHT);
        tableButtonBack.add(btnBack).size(BUTTON_WIDTH, BUTTON_HEIGHT);

        tableButtonNext.bottom().right().pad(0, 0, 10, 10);
        tableButtonBack.bottom().left().pad(0, 10, 10, 0);
        stage.addActor(tableButtonNext);
        stage.addActor(tableButtonBack);
    }

    private void setMapValues() {
        gameConfig.getDisciplines().put(Discipline.HISTORY, btnHistory.isChecked());
        gameConfig.getDisciplines().put(Discipline.GEOGRAPHY,
                btnGeography.isChecked());
        gameConfig.getDisciplines()
                .put(Discipline.POLITICS, btnPolitics.isChecked());
        gameConfig.getDisciplines().put(Discipline.TECNOLOGY,
                btnTechnology.isChecked());
        gameConfig.getDisciplines().put(Discipline.PHYSICS, btnPhysics.isChecked());
        gameConfig.getDisciplines().put(Discipline.CHEMISTRY,
                btnChemistry.isChecked());
        gameConfig.getDisciplines().put(Discipline.LITERATURE,
                btnLiterature.isChecked());
        gameConfig.getDisciplines().put(Discipline.MUSIC, btnMusic.isChecked());
        gameConfig.getDisciplines().put(Discipline.ART, btnArt.isChecked());
    }

    private void createDisciplinesTable() {
        final Table tableDisciplines = new Table(mainSkin);
        tableDisciplines.setFillParent(true);

        // History
        Texture texUp = new Texture(
                Gdx.files.internal("images/buttons/his_btn_up.png"));
        Texture texDown = new Texture(
                Gdx.files.internal("images/buttons/his_btn_down.png"));
        Drawable drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        Drawable drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle(
                drawUp,
                drawDown, drawDown, drawUp, drawDown, drawDown);
        btnHistory = new ImageButton(btnStyle);

        // Geography
        texUp = new Texture(Gdx.files.internal("images/buttons/geo_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/geo_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnGeography = new ImageButton(btnStyle);

        // Politics
        texUp = new Texture(Gdx.files.internal("images/buttons/pol_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/pol_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnPolitics = new ImageButton(btnStyle);

        // Technology
        texUp = new Texture(Gdx.files.internal("images/buttons/tec_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/tec_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnTechnology = new ImageButton(btnStyle);

        // Physics
        texUp = new Texture(Gdx.files.internal("images/buttons/fis_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/fis_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnPhysics = new ImageButton(btnStyle);

        // Chemistry
        texUp = new Texture(Gdx.files.internal("images/buttons/qui_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/qui_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnChemistry = new ImageButton(btnStyle);

        // Literature
        texUp = new Texture(Gdx.files.internal("images/buttons/lit_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/lit_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnLiterature = new ImageButton(btnStyle);

        // Music
        texUp = new Texture(Gdx.files.internal("images/buttons/mus_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/mus_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnMusic = new ImageButton(btnStyle);

        // Art
        texUp = new Texture(Gdx.files.internal("images/buttons/art_btn_up.png"));
        texDown = new Texture(Gdx.files.internal("images/buttons/art_btn_down.png"));
        drawUp = new TextureRegionDrawable(new TextureRegion(texUp));
        drawDown = new TextureRegionDrawable(new TextureRegion(texDown));
        btnStyle = new ImageButton.ImageButtonStyle(drawUp, drawDown, drawDown,
                drawUp, drawDown,
                drawDown);
        btnArt = new ImageButton(btnStyle);

        /* MINIMUM 3 */
        btnHistory.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnHistory.isChecked()) selectedCount += 1;
                    else btnHistory.setChecked(true);
                } else {
                    if (btnHistory.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnGeography.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnGeography.isChecked()) selectedCount += 1;
                    else btnGeography.setChecked(true);
                } else {
                    if (btnGeography.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnPolitics.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnPolitics.isChecked()) selectedCount += 1;
                    else btnPolitics.setChecked(true);
                } else {
                    if (btnPolitics.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnTechnology.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnTechnology.isChecked()) selectedCount += 1;
                    else btnTechnology.setChecked(true);
                } else {
                    if (btnTechnology.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnPhysics.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnPhysics.isChecked()) selectedCount += 1;
                    else btnPhysics.setChecked(true);
                } else {
                    if (btnPhysics.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnChemistry.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnChemistry.isChecked()) selectedCount += 1;
                    else btnChemistry.setChecked(true);
                } else {
                    if (btnChemistry.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnLiterature.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnLiterature.isChecked()) selectedCount += 1;
                    else btnLiterature.setChecked(true);
                } else {
                    if (btnLiterature.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnMusic.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnMusic.isChecked()) selectedCount += 1;
                    else btnMusic.setChecked(true);
                } else {
                    if (btnMusic.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });
        btnArt.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.TICK);
                if (selectedCount <= MINIMUM_SELECTED) {
                    if (btnArt.isChecked()) selectedCount += 1;
                    else btnArt.setChecked(true);
                } else {
                    if (btnArt.isChecked()) selectedCount += 1;
                    else selectedCount -= 1;
                }
            }
        });

        // Creating the table
        tableDisciplines.add(btnHistory);
        tableDisciplines.add("     ");
        tableDisciplines.add(btnGeography);
        tableDisciplines.add("     ");
        tableDisciplines.add(btnPolitics);
        tableDisciplines.row();
        tableDisciplines.add(" ");
        tableDisciplines.row();
        tableDisciplines.add(btnTechnology);
        tableDisciplines.add("     ");
        tableDisciplines.add(btnPhysics);
        tableDisciplines.add("     ");
        tableDisciplines.add(btnChemistry);
        tableDisciplines.row();
        tableDisciplines.add(" ");
        tableDisciplines.row();
        tableDisciplines.add(btnLiterature);
        tableDisciplines.add("     ");
        tableDisciplines.add(btnMusic);
        tableDisciplines.add("     ");
        tableDisciplines.add(btnArt);
        tableDisciplines.row();

        stage.addActor(tableDisciplines);
    }
}
