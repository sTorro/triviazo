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

package com.torrosoft.triviazo.core.data.wrappers;

import com.torrosoft.triviazo.core.enums.Difficulty;
import com.torrosoft.triviazo.core.enums.Discipline;
import com.torrosoft.triviazo.core.enums.Language;

/**
 * Wrapper for the questions.
 * 
 * @author Sergio Torró
 * @since 25/05/2013
 * @version 0.1
 */
public class Question {
    public static final int MAX_ANSWERS = 4;

    private String pkid;
    private String statement;
    private String[] answers = new String[MAX_ANSWERS];
    private Integer right; // < MAX_ANSWERS
    private Language lang = Language.es_ES;
    private Difficulty difficulty;
    private Discipline discipline;

    public Question(final Question question) {
        setPkid(question.getPkid());
        setStatement(question.getStatement());
        setAnswers(question.getAnswers());
        setRight(question.getRight());
        setLang(question.getLang());
        setDifficulty(question.getDifficulty());
        setDiscipline(question.getDiscipline());
    }

    public Question(final String pkid, final String statement,
            final String[] answers, final int right, final Language lang,
            final Difficulty difficulty, final Discipline discipline) {
        setPkid(pkid);
        setStatement(statement);
        setAnswers(answers);
        setRight(right);
        setLang(lang);
        setDifficulty(difficulty);
        setDiscipline(discipline);
    }

    public Question(final String pkid, final String statement,
            final Language lang, final Difficulty difficulty,
            final Discipline discipline) {
        setPkid(pkid);
        setStatement(statement);
        setLang(lang);
        setDifficulty(difficulty);
        setDiscipline(discipline);
    }

    public String[] getAnswers() {
        return answers;
    }

    public Language getLang() {
        return lang;
    }

    public int getRight() {
        return right;
    }

    public String getStatement() {
        return statement;
    }

    public void setAnswers(final String[] answers) {
        this.answers = answers;
    }

    public void setLang(final Language lang) {
        this.lang = lang;
    }

    public void setRight(int right) {
        if ((right > MAX_ANSWERS) || (right < 0)) right = 0;
        this.right = right;
    }

    public void setStatement(final String statement) {
        this.statement = statement;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(final Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(final Discipline discipline) {
        this.discipline = discipline;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(final String pkid) {
        this.pkid = pkid;
    }
}
