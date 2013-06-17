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

package com.torrosoft.triviazo.core.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Implementation of the android database.
 * 
 * @author Sergio Torró
 * @since 26/05/2013
 * @version 0.1
 */
public class DatabaseAndroid extends Database {
    protected SQLiteOpenHelper dbConnection;
    protected SQLiteDatabase stmt;

    public DatabaseAndroid(final Context context) {
        dbConnection = new AndroidDB(context, database_name, null, version);
        stmt = dbConnection.getWritableDatabase();
    }

    /** {@inheritDoc} */
    @Override
    public final void execute(final String sql) {
        stmt.execSQL(sql);
    }

    /** {@inheritDoc} */
    @Override
    public final int executeUpdate(final String sql) {
        stmt.execSQL(sql);
        final SQLiteStatement tmp = stmt.compileStatement("SELECT CHANGES()");
        return (int) tmp.simpleQueryForLong();
    }

    /** {@inheritDoc} */
    @Override
    public final Result query(final String sql) {
        final ResultAndroid result = new ResultAndroid(stmt.rawQuery(sql, null));
        return result;
    }

    /**
     * Android database helper for the creation and the update of the database.
     * 
     * @author Sergio Torró
     * @since 26/05/2013
     * @version 0.1
     */
    public class AndroidDB extends SQLiteOpenHelper {
        public AndroidDB(final Context pContext, final String pName,
                final CursorFactory pFactory, final int pVersion) {
            super(pContext, pName, pFactory, pVersion);
        }

        /** {@inheritDoc} */
        @Override
        public final void onCreate(final SQLiteDatabase pDB) {
            stmt = pDB;
            DatabaseAndroid.this.onCreate();
        }

        /** {@inheritDoc} */
        @Override
        public final void onUpgrade(final SQLiteDatabase pDB, final int oldVersion,
                final int newVersion) {
            stmt = pDB;
            DatabaseAndroid.this.onUpgrade();
        }
    }

    /**
     * Implementation of the android result.
     * 
     * @author Sergio Torró
     * @since 26/05/2013
     * @version 0.1
     */
    public class ResultAndroid implements Result {
        private final Cursor cursor;

        public ResultAndroid(final Cursor pCursor) {
            cursor = pCursor;
        }

        /** {@inheritDoc} */
        @Override
        public final boolean isEmpty() {
            return cursor.getCount() == 0;
        }

        /** {@inheritDoc} */
        @Override
        public final int getColumnIndex(final String name) {
            return cursor.getColumnIndex(name);
        }

        public final String[] getColumnNames() {
            return cursor.getColumnNames();
        }

        /** {@inheritDoc} */
        @Override
        public final float getFloat(final int columnIndex) {
            return cursor.getFloat(columnIndex);
        }

        /** {@inheritDoc} */
        @Override
        public final boolean moveToNext() {
            return cursor.moveToNext();
        }

        /** {@inheritDoc} */
        @Override
        public final String getString(final int columnIndex) {
            return cursor.getString(columnIndex);
        }

        /** {@inheritDoc} */
        @Override
        public final int getInt(final int columnIndex) {
            return cursor.getInt(columnIndex);
        }
    }
}
