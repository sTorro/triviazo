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
import com.torrosoft.triviazo.core.enums.Difficulty;
import com.torrosoft.triviazo.core.enums.GameMode;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * The last configurations of the game happens in this class.
 * 
 * @author Sergio Torró
 * @since 20/05/2013
 * @version 0.3
 */
public final class LastConfigScreen extends AbstractScreen {
    /**
     * The main skin used by all the UI components.
     */
    private final Skin mainSkin;

    /**
     * The main table of the super class.
     */
    private Table mainTable;

    private ImageButton btnEasyIA;
    private ImageButton btnNormalIA;
    private ImageButton btnHardIA;
    private ImageButton btnInsaneIA;

    private ImageButton btnEasyQuestions;
    private ImageButton btnNormalQuestions;
    private ImageButton btnHardQuestions;
    private ImageButton btnAllQuestions;

    private ImageButton btnTen;
    private ImageButton btnFifteen;
    private ImageButton btnTwenty;
    private ImageButton btnThirty;

    public LastConfigScreen(final TriviazoGame game) {
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
                Gdx.files.internal("images/last_config.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(
                texture));
        mainTable.setBackground(drawable);

        createButtonsTable();
        createOptionsTable();

        if (gameConfig.getDifAI() == Difficulty.EASY) {
            btnEasyIA.setChecked(true);
        } else if (gameConfig.getDifAI() == Difficulty.NORMAL) {
            btnNormalIA.setChecked(true);
        } else if (gameConfig.getDifAI() == Difficulty.HARD) {
            btnHardIA.setChecked(true);
        } else if (gameConfig.getDifAI() == Difficulty.INSANE) {
            btnInsaneIA.setChecked(true);
        }

        if (gameConfig.getDifQuestions() == Difficulty.EASY) {
            btnEasyQuestions.setChecked(true);
        } else if (gameConfig.getDifQuestions() == Difficulty.NORMAL) {
            btnNormalQuestions.setChecked(true);
        } else if (gameConfig.getDifQuestions() == Difficulty.HARD) {
            btnHardQuestions.setChecked(true);
        } else if (gameConfig.getDifQuestions() == Difficulty.ALL) {
            btnAllQuestions.setChecked(true);
        }

        if (gameConfig.getNumQuestions() == 10) {
            btnTen.setChecked(true);
        } else if (gameConfig.getNumQuestions() == 15) {
            btnFifteen.setChecked(true);
        } else if (gameConfig.getNumQuestions() == 20) {
            btnTwenty.setChecked(true);
        } else if (gameConfig.getNumQuestions() == 30) {
            btnThirty.setChecked(true);
        }
    }

    private void createOptionsTable() {
        final Table tableOptions = new Table(mainSkin);
        tableOptions.setFillParent(true);

        // Image buttons instantiation
        final Texture easyUp = new Texture(
                Gdx.files.internal("images/buttons/easy_btn_up.png"));
        final Texture easyDown = new Texture(
                Gdx.files.internal("images/buttons/easy_btn_down.png"));
        final Texture normalUp = new Texture(
                Gdx.files.internal("images/buttons/normal_btn_up.png"));
        final Texture normalDown = new Texture(
                Gdx.files.internal("images/buttons/normal_btn_down.png"));
        final Texture hardUp = new Texture(
                Gdx.files.internal("images/buttons/hard_btn_up.png"));
        final Texture hardDown = new Texture(
                Gdx.files.internal("images/buttons/hard_btn_down.png"));
        final Texture tenUp = new Texture(
                Gdx.files.internal("images/buttons/ten_btn_up.png"));
        final Texture tenDown = new Texture(
                Gdx.files.internal("images/buttons/ten_btn_down.png"));
        final Texture fifteenUp = new Texture(
                Gdx.files.internal("images/buttons/fifteen_btn_up.png"));
        final Texture fifteenDown = new Texture(
                Gdx.files.internal("images/buttons/fifteen_btn_down.png"));
        final Texture twentyUp = new Texture(
                Gdx.files.internal("images/buttons/twenty_btn_up.png"));
        final Texture twentyDown = new Texture(
                Gdx.files.internal("images/buttons/twenty_btn_down.png"));
        final Texture thirtyUp = new Texture(
                Gdx.files.internal("images/buttons/thirty_btn_up.png"));
        final Texture thirtyDown = new Texture(
                Gdx.files.internal("images/buttons/thirty_btn_down.png"));
        final Texture allUp = new Texture(
                Gdx.files.internal("images/buttons/all_btn_up.png"));
        final Texture allDown = new Texture(
                Gdx.files.internal("images/buttons/all_btn_down.png"));
        final Texture insaneUp = new Texture(
                Gdx.files.internal("images/buttons/insane_btn_up.png"));
        final Texture insaneDown = new Texture(
                Gdx.files.internal("images/buttons/insane_btn_down.png"));

        // Drawable objects
        final Drawable easyDrawableUp = new TextureRegionDrawable(new TextureRegion(
                easyUp));
        final Drawable easyDrawableDown = new TextureRegionDrawable(
                new TextureRegion(easyDown));
        final Drawable normalDrawableUp = new TextureRegionDrawable(
                new TextureRegion(normalUp));
        final Drawable normalDrawableDown = new TextureRegionDrawable(
                new TextureRegion(normalDown));
        final Drawable hardDrawableUp = new TextureRegionDrawable(new TextureRegion(
                hardUp));
        final Drawable hardDrawableDown = new TextureRegionDrawable(
                new TextureRegion(hardDown));
        final Drawable tenDrawableUp = new TextureRegionDrawable(new TextureRegion(
                tenUp));
        final Drawable tenDrawableDown = new TextureRegionDrawable(
                new TextureRegion(tenDown));
        final Drawable fifteenDrawableUp = new TextureRegionDrawable(
                new TextureRegion(fifteenUp));
        final Drawable fifteenDrawableDown = new TextureRegionDrawable(
                new TextureRegion(fifteenDown));
        final Drawable twentyDrawableUp = new TextureRegionDrawable(
                new TextureRegion(twentyUp));
        final Drawable twentyDrawableDown = new TextureRegionDrawable(
                new TextureRegion(twentyDown));
        final Drawable allDrawableUp = new TextureRegionDrawable(
                new TextureRegion(allUp));
        final Drawable allDrawableDown = new TextureRegionDrawable(
                new TextureRegion(allDown));
        final Drawable insaneDrawableUp = new TextureRegionDrawable(
                new TextureRegion(insaneUp));
        final Drawable insaneDrawableDown = new TextureRegionDrawable(
                new TextureRegion(insaneDown));
        final Drawable thirtyDrawableUp = new TextureRegionDrawable(
                new TextureRegion(thirtyUp));
        final Drawable thirtyDrawableDown = new TextureRegionDrawable(
                new TextureRegion(thirtyDown));

        // Styles for the buttons
        final ImageButton.ImageButtonStyle easyStyle = new ImageButton.ImageButtonStyle(
                easyDrawableUp, easyDrawableDown, easyDrawableDown, easyDrawableUp,
                easyDrawableDown, easyDrawableDown);
        final ImageButton.ImageButtonStyle normalStyle = new ImageButton.ImageButtonStyle(
                normalDrawableUp, normalDrawableDown, normalDrawableDown,
                normalDrawableUp, normalDrawableDown, normalDrawableDown);
        final ImageButton.ImageButtonStyle hardStyle = new ImageButton.ImageButtonStyle(
                hardDrawableUp, hardDrawableDown, hardDrawableDown, hardDrawableUp,
                hardDrawableDown, hardDrawableDown);
        final ImageButton.ImageButtonStyle tenStyle = new ImageButton.ImageButtonStyle(
                tenDrawableUp, tenDrawableDown, tenDrawableDown, tenDrawableUp,
                tenDrawableDown, tenDrawableDown);
        final ImageButton.ImageButtonStyle fifteenStyle = new ImageButton.ImageButtonStyle(
                fifteenDrawableUp, fifteenDrawableDown, fifteenDrawableDown,
                fifteenDrawableUp, fifteenDrawableDown, fifteenDrawableDown);
        final ImageButton.ImageButtonStyle twentyStyle = new ImageButton.ImageButtonStyle(
                twentyDrawableUp, twentyDrawableDown, twentyDrawableDown,
                twentyDrawableUp, twentyDrawableDown, twentyDrawableDown);
        final ImageButton.ImageButtonStyle allStyle = new ImageButton.ImageButtonStyle(
                allDrawableUp, allDrawableDown, allDrawableDown, allDrawableUp,
                allDrawableDown, allDrawableDown);
        final ImageButton.ImageButtonStyle insaneStyle = new ImageButton.ImageButtonStyle(
                insaneDrawableUp, insaneDrawableDown, insaneDrawableDown,
                insaneDrawableUp, insaneDrawableDown, insaneDrawableDown);
        final ImageButton.ImageButtonStyle thirtyStyle = new ImageButton.ImageButtonStyle(
                thirtyDrawableUp, thirtyDrawableDown, thirtyDrawableDown,
                thirtyDrawableUp, thirtyDrawableDown, thirtyDrawableDown);

        btnEasyIA = new ImageButton(easyStyle);
        btnNormalIA = new ImageButton(normalStyle);
        btnHardIA = new ImageButton(hardStyle);
        btnInsaneIA = new ImageButton(insaneStyle);

        btnEasyIA.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnNormalIA.isChecked() && !btnHardIA.isChecked()
                        && !btnInsaneIA.isChecked()) {
                    btnEasyIA.setChecked(true);
                } else {
                    btnNormalIA.setChecked(false);
                    btnHardIA.setChecked(false);
                    btnInsaneIA.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifAI(Difficulty.EASY);
                }
            }
        });
        btnNormalIA.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnEasyIA.isChecked() && !btnHardIA.isChecked()
                        && !btnInsaneIA.isChecked()) {
                    btnNormalIA.setChecked(true);
                } else {
                    btnEasyIA.setChecked(false);
                    btnHardIA.setChecked(false);
                    btnInsaneIA.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifAI(Difficulty.NORMAL);
                }
            }
        });
        btnHardIA.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnEasyIA.isChecked() && !btnNormalIA.isChecked()
                        && !btnInsaneIA.isChecked()) {
                    btnHardIA.setChecked(true);
                } else {
                    btnEasyIA.setChecked(false);
                    btnNormalIA.setChecked(false);
                    btnInsaneIA.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifAI(Difficulty.HARD);
                }
            }
        });
        btnInsaneIA.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnEasyIA.isChecked() && !btnNormalIA.isChecked()
                        && !btnHardIA.isChecked()) {
                    btnInsaneIA.setChecked(true);
                } else {
                    btnEasyIA.setChecked(false);
                    btnNormalIA.setChecked(false);
                    btnHardIA.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifAI(Difficulty.INSANE);
                }
            }
        });

        btnEasyQuestions = new ImageButton(easyStyle);
        btnNormalQuestions = new ImageButton(normalStyle);
        btnHardQuestions = new ImageButton(hardStyle);
        btnAllQuestions = new ImageButton(allStyle);

        btnEasyQuestions.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnNormalQuestions.isChecked() && !btnHardQuestions.isChecked()
                        && !btnAllQuestions.isChecked()) {
                    btnEasyQuestions.setChecked(true);
                } else {
                    btnNormalQuestions.setChecked(false);
                    btnHardQuestions.setChecked(false);
                    btnAllQuestions.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifQuestions(Difficulty.EASY);
                }
            }
        });
        btnNormalQuestions.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnEasyQuestions.isChecked() && !btnHardQuestions.isChecked()
                        && !btnAllQuestions.isChecked()) {
                    btnNormalQuestions.setChecked(true);
                } else {
                    btnEasyQuestions.setChecked(false);
                    btnHardQuestions.setChecked(false);
                    btnAllQuestions.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifQuestions(Difficulty.NORMAL);
                }
            }
        });
        btnHardQuestions.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnEasyQuestions.isChecked() && !btnNormalQuestions.isChecked()
                        && !btnAllQuestions.isChecked()) {
                    btnHardQuestions.setChecked(true);
                } else {
                    btnEasyQuestions.setChecked(false);
                    btnNormalQuestions.setChecked(false);
                    btnAllQuestions.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifQuestions(Difficulty.HARD);
                }
            }
        });
        btnAllQuestions.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnEasyQuestions.isChecked() && !btnNormalQuestions.isChecked()
                        && !btnHardQuestions.isChecked()) {
                    btnAllQuestions.setChecked(true);
                } else {
                    btnEasyQuestions.setChecked(false);
                    btnNormalQuestions.setChecked(false);
                    btnHardQuestions.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setDifQuestions(Difficulty.ALL);
                }
            }
        });

        btnTen = new ImageButton(tenStyle);
        btnFifteen = new ImageButton(fifteenStyle);
        btnTwenty = new ImageButton(twentyStyle);
        btnThirty = new ImageButton(thirtyStyle);

        btnTen.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnFifteen.isChecked() && !btnTwenty.isChecked()
                        && !btnThirty.isChecked()) {
                    btnTen.setChecked(true);
                } else {
                    btnFifteen.setChecked(false);
                    btnTwenty.setChecked(false);
                    btnThirty.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setNumQuestions(10);
                }
            }
        });
        btnFifteen.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y,
                    final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnTen.isChecked() && !btnTwenty.isChecked()
                        && !btnThirty.isChecked()) {
                    btnFifteen.setChecked(true);
                } else {
                    btnTen.setChecked(false);
                    btnTwenty.setChecked(false);
                    btnThirty.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setNumQuestions(15);
                }
            }
        });
        btnTwenty.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnTen.isChecked() && !btnFifteen.isChecked()
                        && !btnThirty.isChecked()) {
                    btnTwenty.setChecked(true);
                } else {
                    btnTen.setChecked(false);
                    btnFifteen.setChecked(false);
                    btnThirty.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setNumQuestions(20);
                }
            }
        });
        btnThirty.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                if (!btnTen.isChecked() && !btnFifteen.isChecked()
                        && !btnTwenty.isChecked()) {
                    btnThirty.setChecked(true);
                } else {
                    btnTen.setChecked(false);
                    btnFifteen.setChecked(false);
                    btnTwenty.setChecked(false);
                    game.getSoundManager().play(TriviazoSound.TICK);
                    gameConfig.setNumQuestions(30);
                }
            }
        });

        // tableOptions.add("Dificultad de la Inteligencia Artificial").colspan(4);
        // tableOptions.row();
        // tableOptions.add(btnEasyIA);
        // tableOptions.add(btnNormalIA);
        // tableOptions.add(btnHardIA);
        // tableOptions.add(btnInsaneIA);
        // tableOptions.row();
        // tableOptions.add(" ");
        // tableOptions.row();
        // tableOptions.add(" ");
        // tableOptions.row();
        if (!(gameConfig.getGameMode() == GameMode.VINDICETIS_EX_SIMIUS)) {
            tableOptions.add("Dificultad de las preguntas").colspan(4);
            tableOptions.row();
            tableOptions.add(btnEasyQuestions);
            tableOptions.add(btnNormalQuestions);
            tableOptions.add(btnHardQuestions);
            tableOptions.add(btnAllQuestions);
        } else gameConfig.setDifQuestions(Difficulty.ALL);

        if (gameConfig.getGameMode() == GameMode.INTELLECTUS_MACHINA) {
            tableOptions.row();
            tableOptions.add(" ");
            tableOptions.row();
            tableOptions.add(" ");
            tableOptions.row();
        }

        if (!(gameConfig.getGameMode() == GameMode.TEMPUS_FUGIT)) {
            tableOptions.add("Número de preguntas").colspan(4);
            tableOptions.row();
            tableOptions.add(btnTen);
            tableOptions.add(btnFifteen);
            tableOptions.add(btnTwenty);
            tableOptions.add(btnThirty);
        } else gameConfig.setNumQuestions(30);

        tableOptions.center();
        stage.addActor(tableOptions);
    }

    /**
     * This method creates the buttons of the bottom and its listeners.
     */
    private void createButtonsTable() {
        final Table tableButtonNext = new Table(mainSkin);
        final Table tableButtonBack = new Table(mainSkin);

        tableButtonNext.setFillParent(true);
        tableButtonBack.setFillParent(true);

        final TextButton btnNext = new TextButton("Comenzar partida   >>", getSkin());
        final TextButton btnBack = new TextButton("<<   Atrás", getSkin());

        btnNext.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                if (gameConfig.getGameMode() == GameMode.INTELLECTUS_MACHINA) {
                    game.setScreen(new IntellectusMachina(game));
                } else if (gameConfig.getGameMode() == GameMode.TEMPUS_FUGIT) {
                    game.setScreen(new TempusFugit(game));
                } else if (gameConfig.getGameMode() == GameMode.VINDICETIS_EX_SIMIUS) {
                    game.setScreen(new VindicetisExSimius(game));
                }

            }
        });

        btnBack.addListener(new DefaultButtonListener() {
            @Override
            public void touchUp(final InputEvent event, final float x,
                    final float y, final int pointer, final int button) {
                super.touchUp(event, x, y, pointer, button);
                game.getSoundManager().play(TriviazoSound.CLICK);
                game.setScreen(new DisciplinesScreen(game));
            }
        });

        tableButtonNext.add(btnNext).size(BUTTON_WIDTH, BUTTON_HEIGHT);
        tableButtonBack.add(btnBack).size(BUTTON_WIDTH, BUTTON_HEIGHT);

        tableButtonNext.bottom().right().pad(0, 0, 10, 10);
        tableButtonBack.bottom().left().pad(0, 10, 10, 0);
        stage.addActor(tableButtonNext);
        stage.addActor(tableButtonBack);
    }
}
