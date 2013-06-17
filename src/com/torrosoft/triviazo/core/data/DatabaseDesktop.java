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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementation of the desktop database.
 * 
 * @author Sergio Torró
 * @since 26/05/2013
 * @version 0.1
 */
public class DatabaseDesktop extends Database {
    protected Connection dbConnection;
    protected Statement stmt;
    protected boolean noDatabase = false;

    public DatabaseDesktop() {
        loadDatabase();
        if (isNewDatabase()) {
            onCreate();
            upgradeVersion();
        } else if (isVersionDifferent()) {
            onUpgrade();
            upgradeVersion();
        }

    }

    /** {@inheritDoc} */
    @Override
    public final void execute(final String sql) {
        try {
            stmt.execute(sql);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public final int executeUpdate(final String sql) {
        try {
            return stmt.executeUpdate(sql);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public final Result query(final String sql) {
        try {
            return new ResultDesktop(stmt.executeQuery(sql));
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * It loads the database for the desktop version.
     */
    private void loadDatabase() {
        final File file = new File(database_name + ".sqlite");
        if (!file.exists()) noDatabase = true;

        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:"
                    + database_name + ".sqlite");
            stmt = dbConnection.createStatement();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private void upgradeVersion() {
        execute("PRAGMA user_version=" + version);
    }

    private boolean isNewDatabase() {
        return noDatabase;
    }

    private boolean isVersionDifferent() {
        final Result q = query("PRAGMA user_version");
        if (!q.isEmpty()) return (q.getFloat(1) != version);
        else return true;
    }

    /**
     * Result for the Desktop database.
     * 
     * @author Sergio Torró
     * @since 26/05/2013
     * @version 0.1
     */
    public class ResultDesktop implements Result {
        private final ResultSet res;
        boolean calledIsEmpty = false;

        public ResultDesktop(final ResultSet res) {
            this.res = res;
        }

        /** {@inheritDoc} */
        @Override
        public final boolean isEmpty() {
            try {
                if (res.getRow() == 0) {
                    calledIsEmpty = true;
                    return !res.next();
                }
                return res.getRow() == 0;
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        /** {@inheritDoc} */
        @Override
        public final boolean moveToNext() {
            try {
                if (calledIsEmpty) {
                    calledIsEmpty = false;
                    return true;
                } else return res.next();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        /** {@inheritDoc} */
        @Override
        public final int getColumnIndex(final String name) {
            try {
                return res.findColumn(name);
            } catch (final SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }

        /** {@inheritDoc} */
        @Override
        public final float getFloat(final int columnIndex) {
            try {
                return res.getFloat(columnIndex);
            } catch (final SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }

        /** {@inheritDoc} */
        @Override
        public final String getString(final int columnIndex) {
            try {
                return res.getString(columnIndex);
            } catch (final SQLException e) {
                e.printStackTrace();
            }

            return "";
        }

        /** {@inheritDoc} */
        @Override
        public final int getInt(final int columnIndex) {
            try {
                return res.getInt(columnIndex);
            } catch (final SQLException e) {
                e.printStackTrace();
            }

            return 0;
        }
    }
}
