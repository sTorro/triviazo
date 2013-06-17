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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.torrosoft.triviazo.TriviazoGame;
import com.torrosoft.triviazo.core.data.wrappers.Question;
import com.torrosoft.triviazo.core.enums.Difficulty;
import com.torrosoft.triviazo.core.enums.Discipline;
import com.torrosoft.triviazo.core.enums.GameMode;
import com.torrosoft.triviazo.core.enums.Language;
import com.torrosoft.triviazo.services.music.TriviazoMusic;
import com.torrosoft.triviazo.services.music.TriviazoSound;
import com.torrosoft.triviazo.util.DefaultButtonListener;

/**
 * Abstract class witch defines the default behavior of the game screen.
 * 
 * @author Sergio Torró
 * @since 04/06/2013
 * @version 0.3
 */
public abstract class GameScreen extends AbstractScreen {
    private static final int BUTTON_SIZE_MOD = 60;
    protected static final int QUESTION_VALUE = 50;
    /**
     * The main skin used by all the UI components.
     */
    protected final Skin mainSkin;

    /**
     * The questions of the current game.
     */
    protected final List<Question> questions = new ArrayList<Question>();
    /**
     * The current index of the list.
     */
    protected int index = 0;
    protected int correct = 0;
    protected int incorrect = 0;

    /**
     * The main table of the super class.
     */
    private Table mainTable;

    private Label lblQuestion;
    private TextButton btnAns1;
    private TextButton btnAns2;
    private TextButton btnAns3;
    private TextButton btnAns4;
    protected Label lblCorrect;
    protected Label lblIncorrect;
    protected Label lblTimer;

    protected int maxQuestions;

    public GameScreen(final TriviazoGame game) {
        super(game);
        game.getMusicManager().play(TriviazoMusic.IN_GAME);
        mainSkin = super.getSkin();
    }

    /**
     * The method for show the UI. It handles the main {@link Table}.
     */
    @Override
    public final void show() {
        super.show();
        mainTable = super.getTable();

        final Texture texture = new Texture(
                Gdx.files.internal("images/default.png"));
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(
                texture));
        mainTable.setBackground(drawable);

        createUI();
        createScoreboard();
        createTimerTable();
        loadQuestions();

        nextQuestion(); // Start game
    }

    private void createScoreboard() {
        final Table tableScore = new Table(mainSkin);
        tableScore.setFillParent(true);
        lblCorrect = new Label("", mainSkin);
        lblIncorrect = new Label("", mainSkin);
        setScore(null);
        tableScore.add(lblCorrect);
        tableScore.row();
        tableScore.add(lblIncorrect);
        tableScore.bottom().left().pad(0, 10, 10, 0);
        stage.addActor(tableScore);
    }

    private void createTimerTable() {
        final Table timerTable = new Table(mainSkin);
        timerTable.setFillParent(true);
        lblTimer = new Label("", mainSkin);
        timerTable.add(lblTimer);
        timerTable.row();
        timerTable.top().right().pad(0, 0, 10, 10);
        stage.addActor(timerTable);
        if (gameConfig.getGameMode() == GameMode.INTELLECTUS_MACHINA)
            lblTimer.setVisible(false);
    }

    private void createUI() {
        final Table tableUI = new Table(mainSkin);
        tableUI.setFillParent(true);
        lblQuestion = new Label("", getSkin());
        btnAns1 = new TextButton("", getSkin());
        btnAns1.setName("0");
        btnAns2 = new TextButton("", getSkin());
        btnAns2.setName("1");
        btnAns3 = new TextButton("", getSkin());
        btnAns3.setName("2");
        btnAns4 = new TextButton("", getSkin());
        btnAns4.setName("3");

        btnAns1.addListener(btnListener);
        btnAns2.addListener(btnListener);
        btnAns3.addListener(btnListener);
        btnAns4.addListener(btnListener);

        tableUI.add(lblQuestion).center().colspan(2);
        tableUI.row();
        tableUI.add("\n\n").colspan(2);
        tableUI.row();
        tableUI.add(btnAns1).size(BUTTON_WIDTH + BUTTON_SIZE_MOD,
                BUTTON_HEIGHT + BUTTON_SIZE_MOD);
        tableUI.add(btnAns2).size(BUTTON_WIDTH + BUTTON_SIZE_MOD,
                BUTTON_HEIGHT + BUTTON_SIZE_MOD);
        tableUI.row();
        tableUI.add(btnAns3).size(BUTTON_WIDTH + BUTTON_SIZE_MOD,
                BUTTON_HEIGHT + BUTTON_SIZE_MOD);
        tableUI.add(btnAns4).size(BUTTON_WIDTH + BUTTON_SIZE_MOD,
                BUTTON_HEIGHT + BUTTON_SIZE_MOD);
        tableUI.row();
        tableUI.add("\n\n").colspan(2);
        tableUI.row();
        tableUI.center();
        stage.addActor(tableUI);
    }

    /**
     * It loads all the questions and keeps a certain number of them in
     * "questions" List (mixed). Never keeps repeated questions.
     */
    private void loadQuestions() {
        final List<Discipline> selectedDis = new ArrayList<Discipline>();
        final Iterator<Entry<Discipline, Boolean>> it = gameConfig.getDisciplines()
                .entrySet().iterator();

        // Get the selected disciplines
        while (it.hasNext()) {
            final Map.Entry<Discipline, Boolean> entry = it.next();
            if (entry.getValue()) selectedDis.add(entry.getKey());
        }

        final Difficulty difQuestions = (gameConfig.getDifQuestions() == Difficulty.ALL) ? null
                : gameConfig.getDifQuestions();

        final List<Question> queList = game.getDatabase().getQuestions(
                Language.es_ES,
                difQuestions, selectedDis.get(0));
        for (int i = 1; i < selectedDis.size(); i++) {
            queList.addAll(game.getDatabase().getQuestions(Language.es_ES,
                    difQuestions, selectedDis.get(i)));
        }

        // Mix questions
        final Random rnd = new Random(System.currentTimeMillis());
        final int max = queList.size();
        int i = 0;

        maxQuestions = (gameConfig.getGameMode() != GameMode.TEMPUS_FUGIT) ?
                gameConfig.getNumQuestions() : max;

        do {
            Question toAdd = queList.get(rnd.nextInt(max));
            if (!hasQuestion(toAdd)) {
                questions.add(toAdd);
                i += 1;
            } else {
                rnd.setSeed(System.currentTimeMillis() * rnd.nextInt());
                toAdd = queList.get(rnd.nextInt(max));
            }
        } while (i < maxQuestions);
    }

    /**
     * Checks if the question it's already in the list.
     * 
     * @param question
     *            The question to check.
     * @return True if it exists.
     */
    private boolean hasQuestion(final Question question) {
        boolean res = false;
        for (final Question que : questions) {
            if (que.getPkid().equals(question.getPkid())) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Loads the question of the list pointed by index.
     */
    protected final void nextQuestion() {
        if (index < maxQuestions) {
            final Question current = questions.get(index);

            lblQuestion.setText(formatStr(current.getStatement(), 90));
            final String[] answers = current.getAnswers();
            btnAns1.setText(answers[0].length() > 40 ? formatStr(answers[0], 40)
                    : answers[0]);
            btnAns2.setText(answers[1].length() > 40 ? formatStr(answers[1], 40)
                    : answers[1]);
            btnAns3.setText(answers[2].length() > 40 ? formatStr(answers[2], 40)
                    : answers[2]);
            btnAns4.setText(answers[3].length() > 40 ? formatStr(answers[3], 40)
                    : answers[3]);
        } else {
            gameOver();
        }
    }

    /**
     * This algorithm takes care of formatting a string to make it pretty on the
     * screen.
     * 
     * @param str
     *            The string to format.
     * @param nChars
     *            The maximum number of characters per line.
     * @return The formatted string.
     */
    private String formatStr(final String str, final int nChars) {
        final StringBuilder sb = new StringBuilder(str);
        final int ite = sb.length() / nChars;

        if (ite > 0) {
            for (int i = 0; i < ite; i++) {
                int pos = (i + 1) * nChars;
                while (!sb.substring(pos, pos + 1).equals(" ")) {
                    --pos;
                }
                sb.insert(pos, '\n');
                sb.delete(pos + 1, pos + 2); // Deletes the space
            }
        } else return str;

        return sb.toString();
    }

    /**
     * The button listener for the answers.
     */
    private final DefaultButtonListener btnListener = new DefaultButtonListener() {
        @Override
        public void touchUp(final InputEvent event, final float x,
                final float y, final int pointer, final int button) {
            super.touchUp(event, x, y, pointer, button);
            game.getSoundManager().play(TriviazoSound.CLICK);
            final int resp = Integer.parseInt(event.getListenerActor().getName());
            checkQuestions(resp);
            setScore(null);
            index += 1;
            nextQuestion();
        }
    };

    /**
     * Checks the current question with the passed integer.
     * 
     * @param resp
     *            The passed integer.
     */
    public abstract void checkQuestions(final int resp);

    /**
     * It sets the score of the game. If the parameter is null the score must be
     * recalculated.
     * 
     * @param sco
     *            The new score if applicable.
     */
    public abstract void setScore(final Integer sco);

    /**
     * The game finish when this method is called.
     */
    public abstract void gameOver();
}
