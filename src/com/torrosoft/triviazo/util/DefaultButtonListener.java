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

package com.torrosoft.triviazo.util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Abstract class for the default button listener. All the buttons must
 * implement this listener.
 * 
 * @author Sergio Torró
 * @since 04/05/2013
 * @version 0.1
 */
public abstract class DefaultButtonListener extends InputListener {
    /**
     * This method have to return true for a correct working.
     */
    @Override
    public final boolean touchDown(final InputEvent event, final float x,
            final float y,
            final int pointer, final int button) {
        return true;
    }
}
