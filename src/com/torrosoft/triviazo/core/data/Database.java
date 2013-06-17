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

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.torrosoft.triviazo.core.data.wrappers.Question;
import com.torrosoft.triviazo.core.enums.Difficulty;
import com.torrosoft.triviazo.core.enums.Discipline;
import com.torrosoft.triviazo.core.enums.GameMode;
import com.torrosoft.triviazo.core.enums.Language;

/**
 * This abstract class defines the database architecture. It's necessary
 * implement the database features for each platform.
 * 
 * @see DatabaseAndroid
 * @see DatabaseDesktop
 * @author Sergio Torró
 * @since 26/05/2013
 * @version 0.1
 */
public abstract class Database {
    protected static String database_name = "triviazo";
    protected static Database instance = null;
    protected static int version = 1;

    /**
     * Runs a SQL query like "create".
     * 
     * @param sql
     *            SQL code to execute.
     */
    public abstract void execute(String sql);

    /**
     * Identical to execute but returns the number of rows affected (useful for
     * updates).
     * 
     * @param sql
     *            SQL code to execute.
     * @return The number of rows affected.
     */
    public abstract int executeUpdate(String sql);

    /**
     * Runs a query and returns an Object with all the results of the query.
     * Result Interface is defined below.
     * 
     * @param sql
     *            SQL code to execute.
     * @return The query result.
     */
    public abstract Result query(String sql);

    /**
     * This method creates the database for the questions used in the game.
     */
    public final void onCreate() {
        // hard-coded rules !
        // TODO read the database from a SQL file would be cool ... maybe?
        createDatabase();
        createInserts();
    }

    /**
     * Method to upgrade the database with new versions...
     */
    public final void onUpgrade() {
        onCreate();
        Gdx.app.log("onUpgrade",
                "onUpgrade called because I changed DataBase version on code!");
    }

    private void createDatabase() {
        execute("DROP TABLE IF EXISTS 'discipline';");
        execute("DROP TABLE IF EXISTS 'difficulty';");
        execute("DROP TABLE IF EXISTS 'language';");
        execute("DROP TABLE IF EXISTS 'questions';");
        execute("DROP TABLE IF EXISTS 'answers';");
        execute("DROP TABLE IF EXISTS 'ans_que';");
        execute("CREATE TABLE 'discipline' ('pkid' INTEGER PRIMARY KEY  NOT NULL , "
                +
                "'name' VARCHAR NOT NULL);");
        execute("INSERT INTO 'discipline' ('name') VALUES ('History');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Geography');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Politics');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Technology');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Physics');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Chemistry');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Literature');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Music');");
        execute("INSERT INTO 'discipline' ('name') VALUES ('Art');");
        execute("CREATE TABLE 'difficulty' ('pkid' INTEGER PRIMARY KEY  NOT NULL , "
                + "'name' VARCHAR NOT NULL);");
        execute("INSERT INTO 'difficulty' ('name') VALUES ('Easy');");
        execute("INSERT INTO 'difficulty' ('name') VALUES ('Normal');");
        execute("INSERT INTO 'difficulty' ('name') VALUES ('Hard');");
        execute("CREATE TABLE 'language' ('pkid' INTEGER PRIMARY KEY  NOT NULL , "
                + "'name' VARCHAR NOT NULL);");
        execute("INSERT INTO 'language' ('name') VALUES ('Spanish');");
        execute("INSERT INTO 'language' ('name') VALUES ('English');");
        execute("INSERT INTO 'language' ('name') VALUES ('French');");
        execute("INSERT INTO 'language' ('name') VALUES ('German');");
        execute("CREATE TABLE 'questions' ('pkid' INTEGER PRIMARY KEY  NOT NULL , " +
                "'statement' VARCHAR NOT NULL, " +
                "'language' INTEGER NOT NULL, " +
                "'difficulty' INTEGER NOT NULL, " +
                "'discipline' INTEGER NOT NULL, " +
                "FOREIGN KEY(language) REFERENCES language(pkid), " +
                "FOREIGN KEY(difficulty) REFERENCES difficulty(pkid), " +
                "FOREIGN KEY(discipline) REFERENCES discipline(pkid));");
        execute("CREATE TABLE 'answers' ('pkid' INTEGER PRIMARY KEY  NOT NULL , "
                + "'text' VARCHAR NOT NULL) ;");
        execute("CREATE TABLE 'ans_que' ('pkid' INTEGER PRIMARY KEY  NOT NULL , "
                + "'answer' INTEGER NOT NULL, " +
                "'question' INTEGER NOT NULL, " +
                "'right' INTEGER NOT NULL, " +
                "FOREIGN KEY(question) REFERENCES questions(pkid), " +
                "FOREIGN KEY(answer) REFERENCES answers(pkid));");
        execute("CREATE TABLE 'scoreboard' ('pkid' INTEGER PRIMARY KEY  NOT NULL , "
                + "'game_mode' VARCHAR NOT NULL, 'value' INTEGER NOT NULL);");
        execute("INSERT INTO 'scoreboard' ('game_mode', 'value') VALUES ('machina', 0);");
        execute("INSERT INTO 'scoreboard' ('game_mode', 'value') VALUES ('tempus', 0);");
        execute("INSERT INTO 'scoreboard' ('game_mode', 'value') VALUES ('simius', 0);");
    }

    private void createInserts() {
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién fue el primer presidente de los Estados Unidos de América?', 1, 2, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Thomas Jefferson');");
        execute("INSERT INTO 'answers' ('text') VALUES ('George Washington');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Franklin D. Roosevelt');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Abraham Lincoln');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (1, 1, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (2, 1, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (3, 1, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (4, 1, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién fue el primer presidente de la Segunda República Española?', 1, 1, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Niceto Alcalá-Zamora');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Manuel Azaña');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Emilio Mola');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Francisco Largo Caballero');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (5, 2, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (6, 2, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (7, 2, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (8, 2, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cómo se denomina la forma de gobierno ejercido por tres personas, normalmente aliados entre sí?', 1, 3, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Despotismo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Diarquía');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Triunvirato');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tricracia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (9, 3, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (10, 3, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (11, 3, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (12, 3, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En que ciudad antiguia fue acuñado el término democracia?', 1, 3, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Esparta');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tebas');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Corinto');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Atenas');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (13, 4, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (14, 4, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (15, 4, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (16, 4, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué caracteriza a las esculturas góticas del siglo XV de Borgoña?', 1, 3, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Dramatismo y expresividad');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Realismo e inspiración clásica');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Vuelta al hieratismo y rigidez');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Idealización y amaneramiento');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (17, 5, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (18, 5, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (19, 5, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (20, 5, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cúal de estas obras no es de Donatello?', 1, 2, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Magdalena Penitente');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Puerta del Paraíso');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El banquete de Herodes');");
        execute("INSERT INTO 'answers' ('text') VALUES ('San Jorge');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (21, 6, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (22, 6, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (23, 6, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (24, 6, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué caracteriza el tratamiento del cuerpo humano en la pintura de Miguel Ángel?', 1, 2, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sus contornos suelen estar difuminados');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Formas lánguidas y espirituales');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tienen a una posición de reposo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tratamiento escultórico y monumental');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (25, 7, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (26, 7, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (27, 7, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (28, 7, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En que ciudad se localiza la Capilla Sixtina?', 1, 1, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Roma');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ciudad del Vaticano');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Nápoles');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Milán');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (29, 8, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (30, 8, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (31, 8, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (32, 8, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('A la hora de montar un circuito eléctrico es necesario tener en cuenta el concepto de resistencia interna. ¿A qué hace referencia?', 1, 3, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('La pérdida de energía por el calor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El sentido de circulación de los electrones');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El grado de conductividad del cable');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La oposición del paso de electrones de los aparatos conectados');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (33, 9, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (34, 9, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (35, 9, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (36, 9, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué símbolo se utiliza para representar la intesidad del campo gravitatorio?', 1, 2, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('N');");
        execute("INSERT INTO 'answers' ('text') VALUES ('G');");
        execute("INSERT INTO 'answers' ('text') VALUES ('F');");
        execute("INSERT INTO 'answers' ('text') VALUES ('g');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (37, 10, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (38, 10, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (39, 10, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (40, 10, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('La intesidad del campo gravitatorio es diferente según el lugar de la Tierra donde se mida. ¿En cuál de los siguientes lugares esa gravedad es menor?', 1, 1, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('En la cima de una montaña');");
        execute("INSERT INTO 'answers' ('text') VALUES ('En el ecuador');");
        execute("INSERT INTO 'answers' ('text') VALUES ('En el núcleo de la Tierra');");
        execute("INSERT INTO 'answers' ('text') VALUES ('En los polos');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (41, 11, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (42, 11, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (43, 11, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (44, 11, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('Madagascar es la isla más grande de África a la que separa el canal de Mozambique. ¿En que zona de África se encuentra?', 1, 2, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sureste');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Norte');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Oeste');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Suroeste');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (45, 12, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (46, 12, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (47, 12, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (48, 12, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Por cuál de los siguientes países no pasa el Amazonas?', 1, 1, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Perú');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Argentina');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Brasil');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Colombia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (49, 13, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (50, 13, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (51, 13, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (52, 13, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál de los siguientes archipiélagos se encuentra a caballo entre el continente asiático y el oceánico?', 1, 3, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Melanesia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Polinesia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Micronesia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Indonesia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (53, 14, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (54, 14, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (55, 14, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (56, 14, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('En Tanzania  se localiza la mayor altura de África, que además es uno de los volcanes más altos del mundo. ¿Cuál es su nombre?', 1, 2, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Baker');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kenya');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kilimanjaro');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Stanley');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (57, 15, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (58, 15, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (59, 15, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (60, 15, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cúal de los siguientes escritores naturalistas ambientó sus novelas en el mundo rural de su Valencia natal?', 1, 3, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Juan Valera');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ramón de Campoamor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Vicente Blasco Ibáñez');");
        execute("INSERT INTO 'answers' ('text') VALUES ('José María de Pereda');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (61, 16, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (62, 16, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (63, 16, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (64, 16, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('El actor y dramaturgo francés Molière falleció en un escenario mientras representaba la última obra que había escrito. ¿De cuál se trataba?', 1, 2, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El burgués gentilhombre');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El misántropo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tartufo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El enfermo imaginario');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (65, 17, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (66, 17, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (67, 17, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (68, 17, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('La protagonista que da título a La Celestina tiene un antecedente literario en Trotaconventos, personaje que aparece en la obra...', 1, 1, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Laberinto de Fortuna');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El conde Lucanor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rimado de Palacio');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Libro de buen amor');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (69, 18, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (70, 18, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (71, 18, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (72, 18, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('Del petróleo, además de gasolina, se obtienen distintas sustancias de gran utilidad para el hombre. ¿Cuál de estas no se destila del crudo extraído?', 1, 1, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Fueloil');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Gas natural');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Butano');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Queroseno');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (73, 19, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (74, 19, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (75, 19, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (76, 19, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué subnivel colocan su electrón diferenciador los lantánidos y actínidos?', 1, 3, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('d');");
        execute("INSERT INTO 'answers' ('text') VALUES ('s');");
        execute("INSERT INTO 'answers' ('text') VALUES ('f');");
        execute("INSERT INTO 'answers' ('text') VALUES ('p');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (77, 20, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (78, 20, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (79, 20, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (80, 20, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué alcohol, de fórmula C2H6O, se mezcla con agua y otras sustancias para formas las bebidas alcohólicas?', 1, 1, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Butanol');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Propanol');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Metanol');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Etanol');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (81, 21, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (82, 21, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (83, 21, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (84, 21, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué caracteriza a los homopolímeros?', 1, 2, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sólo existe un tipo de monómero');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Recuperan su forma original al cesar sobre ellos una fuerza deformante');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Son de origen sintético');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tienen una estructura cristalina');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (85, 22, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (86, 22, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (87, 22, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (88, 22, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuáles de estas unidades de medida no es informática?', 1, 1, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Bit');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tera');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Newton');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mega');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (89, 23, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (90, 23, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (91, 23, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (92, 23, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('Cuando hablamos de memoria RAM, ¿qué significas sus siglas?', 1, 2, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rápido Acceso Modular');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Random-access memory');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rapid Access Memory');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Relay Asynchronous Memory');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (93, 24, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (94, 24, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (95, 24, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (96, 24, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál de los siguientes nombres no corresponde un sistema operativo?', 1, 1, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Windows');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Unix');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Portix');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Minix');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (97, 25, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (98, 25, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (99, 25, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (100, 25, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué número decimal es 1010 en binario?', 1, 3, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('8');");
        execute("INSERT INTO 'answers' ('text') VALUES ('25');");
        execute("INSERT INTO 'answers' ('text') VALUES ('10');");
        execute("INSERT INTO 'answers' ('text') VALUES ('32');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (101, 26, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (102, 26, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (103, 26, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (104, 26, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De qué zona de España son los monumentos megalíticos conocidos como taulas, talayots o navetas?', 1, 3, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Cataluña');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Islas Baleares');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Comunidad Valenciana');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Aragón');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (105, 27, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (106, 27, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (107, 27, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (108, 27, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué combustible fósil  fue la fuente de energía fundamental para la Revolución Industrial del Siglo XVIII?', 1, 1, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pétroleo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Gas Natural');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Carbón');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Metano');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (109, 28, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (110, 28, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (111, 28, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (112, 28, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué decisiva batalla del Pacífico las tropas norteamericanas derrotaron a las japonesas en junio de 1942, lo que supuso un punto de inflexión en el dominio de la zona?', 1, 3, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Batalla del Mar del Coral');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Batalla de Pearl Harbor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Batalla de Midway');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Batalla de Iwo Jima');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (113, 29, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (114, 29, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (115, 29, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (116, 29, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué patente en el campo del sonido se debe al inventor estadounidense Lee de Forest?', 1, 3, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El primer oscilador electrónico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El disco de vinilo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El amplificador');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El gramófono');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (117, 30, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (118, 30, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (119, 30, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (120, 30, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En cuál de estos sistemas de grabación sonora se puede borrar un sonido registrado previamente?', 1, 3, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Gramófono');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tocadiscos');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Magnetófono');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Fonógrafo');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (121, 31, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (122, 31, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (123, 31, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (124, 31, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué zona de Europa tiene su principal foco de creación la música celta?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Países del Este');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Atlántico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mediterráneo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Países Nórdicos');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (125, 32, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (126, 32, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (127, 32, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (128, 32, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué familia de instrumentos es especialmente importante en la música africana?', 1, 1, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Percusión');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Cuerda');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Viento madera');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Viento metal');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (129, 33, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (130, 33, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (131, 33, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (132, 33, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Entre qué años tuvo lugar la dictadura de Franco?', 1, 1, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('De 1939 a 1975');");
        execute("INSERT INTO 'answers' ('text') VALUES ('De 1839 a 1875');");
        execute("INSERT INTO 'answers' ('text') VALUES ('De 1936 a 1970');");
        execute("INSERT INTO 'answers' ('text') VALUES ('De 1940 a 1972');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (133, 34, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (134, 34, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (135, 34, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (136, 34, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién es José Martí?', 1, 2, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El libertador de Argentina y Chile');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El fundador de la ciudad de La Habana');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Creador del Partido Revolucionario Cubano en 1892');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un gobernador español de Cuba');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (137, 35, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (138, 35, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (139, 35, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (140, 35, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De que estado de la antigüedad fue rey Alejandro Magno?', 1, 2, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Atenas');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Esparta');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Corinto');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Macedonia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (141, 36, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (142, 36, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (143, 36, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (144, 36, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué dos países separaba el Muro de Berlín?', 1, 1, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rusia y Alemania');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alemania Occidental y Alemania Oriental');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Praga y Varsovia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Hungría y Austria');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (145, 37, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (146, 37, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (147, 37, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (148, 37, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Entre qué años tuvo lugar la Guerra Civil Española?', 1, 2, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('1939 - 1945');");
        execute("INSERT INTO 'answers' ('text') VALUES ('1936 - 1939');");
        execute("INSERT INTO 'answers' ('text') VALUES ('1934 - 1938');");
        execute("INSERT INTO 'answers' ('text') VALUES ('1939 - 1943');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (149, 38, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (150, 38, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (151, 38, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (152, 38, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién es Gonzalo Jiménez de Quesada?', 1, 3, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El fudador de la ciudad de Bogotá');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Conquistador de Guatemala');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El primer explorador español en llegar a la actual Venezuela');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un presidente democrático Chileno');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (153, 39, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (154, 39, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (155, 39, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (156, 39, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué año estalló la guerra entre Irán e Irak?', 1, 2, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('1967');");
        execute("INSERT INTO 'answers' ('text') VALUES ('1991');");
        execute("INSERT INTO 'answers' ('text') VALUES ('1980');");
        execute("INSERT INTO 'answers' ('text') VALUES ('1948');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (157, 40, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (158, 40, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (159, 40, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (160, 40, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué país tiene la bandera nacional oficial más antigua del mundo?', 1, 3, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Dinamarca');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Finlandia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Inglaterra');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Noruega');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (161, 41, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (162, 41, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (163, 41, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (164, 41, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién fue el primer califa independiente de Córdoba?', 1, 2, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alhaken I');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Abderraman I');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Almanzor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Abderraman III');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (165, 42, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (166, 42, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (167, 42, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (168, 42, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿A qué civilización pertenece la ciudadela de Machu Pichu?', 1, 1, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Azteca');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Maya');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Inca');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mapuche');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (169, 43, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (170, 43, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (171, 43, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (172, 43, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿A qué civilización pertenece la ciudadela de Machu Pichu?', 1, 2, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un emperador romano');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un gobernante de la dinastía caldea de Babilonia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un faraón egipcio');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un emperador Persa');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (173, 44, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (174, 44, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (175, 44, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (176, 44, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué honor ostenta Creta?', 1, 1, 1);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Fue la primera civilización del mundo griego');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Nunca fue conquistada por el imperio romano');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Desarrollo el imperio más grande de Europa');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Fue cuna de la civilización cartaginesa');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (177, 45, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (178, 45, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (179, 45, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (180, 45, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Dónde está Niagara Falls?', 1, 2, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Nueva York');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pensilvania');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ohio');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Michigan');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (181, 46, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (182, 46, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (183, 46, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (184, 46, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual de los siguientes estados de Estados Unidos está situado más al este?', 1, 2, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Utah');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kansas');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Colorado');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Virginia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (185, 47, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (186, 47, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (187, 47, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (188, 47, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Además del ruso ¿Qué idioma es oficial en Kazajistán?', 1, 1, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mongol');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kazaguistaní');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kazajo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Chino');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (189, 48, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (190, 48, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (191, 48, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (192, 48, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual es la capital de Australia?', 1, 2, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Lima');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Camberra');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Roma');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Riga');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (193, 49, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (194, 49, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (195, 49, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (196, 49, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué es una catarata?', 1, 1, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un bosque de encinas y abedules muy poblado');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un pico que sobresale entre todos los de su cordillera');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un río muy accidentado');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Una cascada de grandes dimensiones');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (197, 50, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (198, 50, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (199, 50, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (200, 50, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué país está el Lago Onega?', 1, 3, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sudáfrica');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Guatemala');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rusia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Australia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (201, 51, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (202, 51, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (203, 51, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (204, 51, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual es el río más largo del mundo?', 1, 1, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Nilo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Amazonas');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Congo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mississippi-Missouri');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (205, 52, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (206, 52, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (207, 52, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (208, 52, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué estado está la ciudad de Rochester?', 1, 3, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pensilvania');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ohio');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Michigan');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Nueva York');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (209, 53, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (210, 53, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (211, 53, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (212, 53, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual es la capital de la Confederación Suiza?', 1, 1, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Berna');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Zurich');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ginebra');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Friburgo');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (213, 54, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (214, 54, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (215, 54, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (216, 54, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Dónde está la Plaza de San Pedro más famosa?', 1, 1, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Florencia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Roma');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ciudad del Vaticano');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Venecia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (217, 55, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (218, 55, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (219, 55, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (220, 55, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En que país está la ciudad de Carapeguá?', 1, 3, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Bolivia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alemania');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Hungría');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Paraguay');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (221, 56, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (222, 56, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (223, 56, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (224, 56, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué océano se encuentra la República de Mauricio?', 1, 3, 2);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Océano Atlántico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Océano Ártico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Océano Índico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Océano Pacífico');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (225, 57, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (226, 57, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (227, 57, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (228, 57, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cómo se llamaba la empresa fundada por Steve Jobs durante sus años fuera de Apple?', 1, 2, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pixar');");
        execute("INSERT INTO 'answers' ('text') VALUES ('NeXT Computer');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Macintosh');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Cloud 9');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (229, 58, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (230, 58, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (231, 58, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (232, 58, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué es la tecnología HSDPA?', 1, 2, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un sistema de encriptación de canciones');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un protocolo de comunicación de satélites');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El código con el que cuenta la consola portátil PSP');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Un protocolo de acceso a datos para dispositivos móviles');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (233, 59, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (234, 59, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (235, 59, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (236, 59, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué significa MP3?', 1, 1, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('MPEG-1 Audio Layer 3');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Music Public 3');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Music Partner 3');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Media Player 3');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (237, 60, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (238, 60, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (239, 60, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (240, 60, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué país se desarrolló la dactilografía?', 1, 1, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Argentina');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Estados Unidos');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Italia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alemania');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (241, 61, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (242, 61, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (243, 61, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (244, 61, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué país se desarrolló la dactilografía?', 1, 2, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Argentina');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Estados Unidos');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Italia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alemania');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (245, 62, 1); /*OK*/");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (246, 62, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (247, 62, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (248, 62, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién inventó y desarrolló el ciclotrón?', 1, 3, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Dennis Gabor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Carl David Anderson');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ernest Orlando Lawrence');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Edward Victor Appleton');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (249, 63, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (250, 63, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (251, 63, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (252, 63, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué Premio Nobel recibió Dennis Gabor?', 1, 3, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Premio Nobel de Física');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Premio Nobel de Química');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Premio Nobel de Medicina');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Premio Nobel de Economía');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (253, 64, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (254, 64, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (255, 64, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (256, 64, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué mide un amperímetro?', 1, 1, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('La dureza de un material');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La intensidad de corriente eléctrica');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El peso de una pieza');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El diámetro exterior de un elemento');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (257, 65, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (258, 65, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (259, 65, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (260, 65, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué es la trayectoria de un móvil?', 1, 2, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Es la dirección del movimiento');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Es el sentido del movimiento');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Es la dirección y sentido del movimiento');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Es la línea que describe en su movimiento');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (261, 66, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (262, 66, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (263, 66, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (264, 66, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿En qué unidad se mide la presión?', 1, 1, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kg');");
        execute("INSERT INTO 'answers' ('text') VALUES ('m/s');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pascales');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Newton');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (265, 67, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (266, 67, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (267, 67, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (268, 67, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Dónde nació Isaac Newton?', 1, 1, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Dinamarca');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Finlandia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Escocia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Inglaterra');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (269, 68, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (270, 68, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (271, 68, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (272, 68, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién originó esta frase?: Dadme un punto de apoyo y moveré el mundo', 1, 2, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Arquímedes');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sócrates');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Aristóteles');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Demócrito');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (273, 69, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (274, 69, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (275, 69, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (276, 69, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál de estas velocidades corresponde a los movimientos circulares?', 1, 3, 5);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Velocidad de trayectoria');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Velocidad lineal');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Velocidad angular');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Velocidad media');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (277, 70, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (278, 70, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (279, 70, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (280, 70, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual es el símbolo del Zinc?', 1, 2, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Znc');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Zi');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Zn');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Z');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (281, 71, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (282, 71, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (283, 71, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (284, 71, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual es la unidad básica de Temperatura según el SI?', 1, 2, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mol');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Candela');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Kelvin');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Metro');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (285, 72, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (286, 72, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (287, 72, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (288, 72, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién fue el Premio Nobel de Química el año 2007?', 1, 3, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Peter Grumberg');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Albert Fert');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Jose Castro Urdiales');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Gerhard Ertl');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (289, 73, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (290, 73, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (291, 73, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (292, 73, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué es un Mol?', 1, 2, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('La unidad básica de temperatura');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La unidad básica de longitud');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La unidad básica de cantidad de sustancia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La unidad básica de masa');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (293, 74, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (294, 74, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (295, 74, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (296, 74, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual es la temperatura de fusión del agua según la escala Celsius?', 1, 1, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('212 grados');");
        execute("INSERT INTO 'answers' ('text') VALUES ('100 grados');");
        execute("INSERT INTO 'answers' ('text') VALUES ('32 grados');");
        execute("INSERT INTO 'answers' ('text') VALUES ('0 grados');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (297, 75, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (298, 75, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (299, 75, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (300, 75, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué descubrió Marie Sklodowska-Curie?', 1, 2, 6);");
        execute("INSERT INTO 'answers' ('text') VALUES ('La fisión nuclear de los átomos');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El radio y el polonio');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El método del carbono 14');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Los componentes gaseosos del aire');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (301, 76, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (302, 76, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (303, 76, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (304, 76, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cual de los siguientes NO es un libro del escritor británico Tolkien?', 1, 1, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Hobbit');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El hombre bicentenario');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Señor de los anillos');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Silmarillion');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (305, 77, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (306, 77, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (307, 77, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (308, 77, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál es la primera de las Novelas Ejemplares de Miguel de Cervantes?', 1, 2, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Los trabajos de Persiles y Sigismunda');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La Galatea');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Quijote');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La Gitanilla');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (309, 78, 0); --OK");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (310, 78, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (311, 78, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (312, 78, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué autores pertenecen a la Generación de 27?', 1, 2, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Vicente Aleixandre');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Federico García Lorca');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Todos los citados');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pedro Salinas');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (313, 79, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (314, 79, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (315, 79, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (316, 79, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué es la sístole? Una figura literaria que ...', 1, 2, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('confunde intencionadamente al lector para llamar su atención');");
        execute("INSERT INTO 'answers' ('text') VALUES ('cambia el acento para acortar la sílaba');");
        execute("INSERT INTO 'answers' ('text') VALUES ('repite intencionadamente un fonema dentro de la frase');");
        execute("INSERT INTO 'answers' ('text') VALUES ('atribuye cualidades humanas a objetos');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (317, 80, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (318, 80, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (319, 80, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (320, 80, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('Obra de Juan Ruíz, El Arcipreste de Hita...', 1, 3, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El libro de buen amor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Cantar de Mio Cid');");
        execute("INSERT INTO 'answers' ('text') VALUES ('La Divina Comedia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('El Conde Lucanor');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (321, 81, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (322, 81, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (323, 81, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (324, 81, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál de estos autores pertenece a La Generacion del 98?', 1, 3, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Todos son correctos');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Antonio Machado');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Pío Baroja');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ramón del Valle-Inclan');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (325, 82, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (326, 82, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (327, 82, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (328, 82, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién escribió El disputado voto del Señor Cayo?', 1, 3, 7);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Juan Ramón Jiménez');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Jacinto Benavente');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Camilo José Cela');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Miguel Delibes');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (329, 83, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (330, 83, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (331, 83, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (332, 83, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De quien es el disco Nuevo pequeño catálogo de seres y estares?', 1, 1, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('El último de la fila');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Siniestro total');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Medina Azahara');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Mecano');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (333, 84, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (334, 84, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (335, 84, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (336, 84, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De qué disco es la canción de Queen Death on Two Legs?', 1, 3, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('News of the World');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sheer Heart Attack');");
        execute("INSERT INTO 'answers' ('text') VALUES ('The Game');");
        execute("INSERT INTO 'answers' ('text') VALUES ('A Night at the Opera');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (337, 85, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (338, 85, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (339, 85, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (340, 85, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cómo se titula el primer disco de Madonna?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Like a Virgin');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Madonna');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Hard Candy');");
        execute("INSERT INTO 'answers' ('text') VALUES ('True Blue');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (341, 86, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (342, 86, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (343, 86, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (344, 86, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De quien es la cancion Nothing compares 2U?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('David Bowie');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Europe');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Sinead OConnor');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Prince');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (345, 87, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (346, 87, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (347, 87, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (348, 87, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál de los bajistas que ha tenido Metallica toca en la versión original de Master of Puppets?', 1, 3, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Robert Trujillo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Jason Newsted');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Ron McGovney');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Cliff Burton');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (349, 88, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (350, 88, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (351, 88, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (352, 88, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cómo se llama el cantante de Led Zeppelin?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Axl Rose');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Robert Plant');");
        execute("INSERT INTO 'answers' ('text') VALUES ('John Lennon');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Bruce Dickinson');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (353, 89, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (354, 89, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (355, 89, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (356, 89, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quienes son Kevin, Joe y Nick?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Los Kyuss');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Los Endo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Los Jonas Brothers');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Los Hellyeah');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (357, 90, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (358, 90, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (359, 90, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (360, 90, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién cantó How Deep Is your Love? (Banda sonora de Fiebre del Sábado Noche)', 1, 3, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Michael Jackson');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Billy Joel');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Meat Loaf');");
        execute("INSERT INTO 'answers' ('text') VALUES ('The Bee Gees');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (361, 91, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (362, 91, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (363, 91, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (364, 91, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién fue el líder de Los Carayos y Mano Negra?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Manu Chao');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Carlos Marín');");
        execute("INSERT INTO 'answers' ('text') VALUES ('José Mercé');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Julio José Iglesias');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (365, 92, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (366, 92, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (367, 92, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (368, 92, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué cantautor cantaba Hoy puede ser un gran día?', 1, 2, 8);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Perales');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Peret');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Baute');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Serrat');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (369, 93, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (370, 93, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (371, 93, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (372, 93, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿A qué país tendremos que viajar si queremos ver las pinturas rupestres del paleolítico superior de la Cueva de Lascaux?', 1, 1, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Francia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('España');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Reino Unido');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rusia');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (373, 94, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (374, 94, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (375, 94, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (376, 94, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Dónde se encuentra el Obelisco de Luxor?', 1, 2, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('En El Cairo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('En Alejandría');");
        execute("INSERT INTO 'answers' ('text') VALUES ('En París');");
        execute("INSERT INTO 'answers' ('text') VALUES ('En Luxor');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (377, 95, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (378, 95, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (379, 95, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (380, 95, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De qué artista es la obra el Nacimiento de Venus?', 1, 2, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('De Piero della Francesca');");
        execute("INSERT INTO 'answers' ('text') VALUES ('De Botticelli');");
        execute("INSERT INTO 'answers' ('text') VALUES ('De Giotto');");
        execute("INSERT INTO 'answers' ('text') VALUES ('De Miguel Angel');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (381, 96, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (382, 96, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (383, 96, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (384, 96, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿De qué estilo es el Palacio de Fontainebleau?', 1, 3, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Renacentista');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Neoclásico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Prerománico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Gótico');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (385, 97, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (386, 97, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (387, 97, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (388, 97, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿A quién pintó múltiples veces Honoré Daumier?', 1, 3, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('A Rembrandt');");
        execute("INSERT INTO 'answers' ('text') VALUES ('A Carl Marx');");
        execute("INSERT INTO 'answers' ('text') VALUES ('A Don Quijote');");
        execute("INSERT INTO 'answers' ('text') VALUES ('A Napoleón Bonaparte');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (389, 98, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (390, 98, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (391, 98, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (392, 98, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién pintó La Gioconda (La Mona Lisa)?', 1, 1, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alberto Durero');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Leonardo da Vinci');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Miguel Angel');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rafael');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (393, 99, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (394, 99, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (395, 99, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (396, 99, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Qué estadounidense es el creador del juguete móvil colgante y precursor de la escultura cinética?', 1, 3, 9);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Alexander Calder');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Joan Miró');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Phidias');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Auguste Rodin');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (397, 100, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (398, 100, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (399, 100, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (400, 100, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién es el actual (año 2003) presidente de Alemania?', 1, 3, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Angela Merkel');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Philipp Rösler');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Joachim Gauck');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Christian Wulff');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (401, 101, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (402, 101, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (303, 101, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (404, 101, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál es la forma de gobierno de Francia?', 1, 1, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Teocracia');");
        execute("INSERT INTO 'answers' ('text') VALUES ('República federal');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Monarquía parlamentaria');");
        execute("INSERT INTO 'answers' ('text') VALUES ('República semipresidencialista');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (405, 102, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (406, 102, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (407, 102, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (408, 102, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuántas veces ha cambiado la ley de educación española desde los años 70?', 1, 2, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Tres');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Doce');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Seis');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Veinticinco');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (409, 103, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (410, 103, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (411, 103, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (412, 103, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Cuál ha sido el imperio contiguo mas extenso en superficie?', 1, 1, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Imperio ruso');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Imperio británico');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Imperio mongol');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Imperio alemán');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (413, 104, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (414, 104, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (415, 104, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (416, 104, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién escribio El Capital?', 1, 1, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Friedrich Engels');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Rosa Luxemburgo');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Che Guevara');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Karl Marx');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (417, 105, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (418, 105, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (419, 105, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (420, 105, 1);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('El politólogo Max Weber hizo una definición del Estado que ha sido fundamental en el estudio de la ciencia política moderna, ¿que definición es?', 1, 3, 3);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Conjunto de instituciones que poseen la autoridad y potestad para establecer las normas que regulan una sociedad');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Entidad que detenta el monopolio de la violencia y los medios de coacción');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Área geográfica bien delimitada y una entidad políticamente independiente');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Forma de organización social, económica, política soberana y coercitiva');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (421, 106, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (422, 106, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (423, 106, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (424, 106, 0);");
        execute("INSERT INTO 'questions' ('statement', 'language', 'difficulty', 'discipline') VALUES ('¿Quién fue el creador del lenguaje de programación C?', 1, 1, 4);");
        execute("INSERT INTO 'answers' ('text') VALUES ('Bjarne Stroustrup');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Linus Torvalds');");
        execute("INSERT INTO 'answers' ('text') VALUES ('Dennis Ritchie');");
        execute("INSERT INTO 'answers' ('text') VALUES ('James Gosling');");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (425, 107, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (426, 107, 0);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (427, 107, 1);");
        execute("INSERT INTO 'ans_que' ('answer', 'question', 'right') VALUES (428, 107, 0);");
    }

    public final void addScoreForGameMode(final GameMode gm, final int add) {
        final Result res = query("SELECT value FROM scoreboard WHERE game_mode LIKE '"
                + gm.getDbName() + "';");
        res.moveToNext(); // Android issue
        int score = res.getInt(res.getColumnIndex("value"));
        score += add;
        execute("UPDATE scoreboard SET value = " + score
                + " WHERE game_mode LIKE '"
                + gm.getDbName() + "';");
    }

    public final int getScoreForGameMode(final GameMode gm) {
        final Result res = query("SELECT value FROM scoreboard WHERE game_mode LIKE '"
                + gm.getDbName() + "';");
        res.moveToNext();
        return res.getInt(res.getColumnIndex("value"));
    }

    /**
     * Handles the mapping.
     * 
     * TODO consider refactoring
     * 
     * @return List of questions.
     */
    public final List<Question> getQuestions(final Language lang,
            final Difficulty diff, final Discipline dis) {
        List<Question> questions = new ArrayList<Question>();
        final StringBuilder queryStr = new StringBuilder();

        queryStr.append("SELECT q.pkid, q.statement, lang.name as 'language', "
                +
                "dif.name as 'difficulty', dis.name as 'discipline' ");
        queryStr.append("FROM questions q, language lang, difficulty dif, discipline dis ");
        queryStr.append("WHERE q.language = lang.pkid ");
        queryStr.append("AND q.difficulty = dif.pkid ");
        queryStr.append("AND q.discipline = dis.pkid");

        if (lang != null) {
            queryStr.append(" AND lang.name LIKE '" + lang.getName() + "'");
        }

        if (diff != null) {
            queryStr.append(" AND dif.name LIKE '" + diff.getName() + "'");
        }

        if (dis != null) {
            queryStr.append(" AND dis.name LIKE '" + dis.getName() + "'");
        }

        queryStr.append(";");

        final Result result = query(queryStr.toString());

        if (!result.isEmpty()) {
            while (result.moveToNext()) {
                final String pkid = result
                        .getString((result.getColumnIndex("pkid")));
                final String statement = result.getString((result
                        .getColumnIndex("statement")));
                final Language language = Language.getLanguageByName(result
                        .getString((result
                                .getColumnIndex("language"))));
                final Difficulty difficulty = Difficulty.getDifficultyByName(result
                        .getString((result.getColumnIndex("difficulty"))));
                final Discipline discipline = Discipline.getDisciplineByName(result
                        .getString((result.getColumnIndex("discipline"))));

                final Question que = new Question(pkid, statement, language,
                        difficulty, discipline);
                questions.add(que);
            }

            for (final Question que : questions) {
                queryStr.delete(0, queryStr.length());
                queryStr.append("SELECT a.text, aq.right FROM answers a, ans_que aq, questions q ");
                queryStr.append("WHERE a.pkid = aq.answer ");
                queryStr.append("AND q.pkid = aq.question ");
                queryStr.append("AND q.pkid = " + que.getPkid() + ";");
                final Result resAns = query(queryStr.toString());

                final String[] answers = new String[Question.MAX_ANSWERS];
                int i = 0;
                while (resAns.moveToNext()) {
                    final String text = resAns.getString(resAns
                            .getColumnIndex("text"));
                    final int right = resAns.getInt(resAns
                            .getColumnIndex("right"));
                    answers[i] = text;
                    if (right == 1) {
                        que.setRight(i);
                    }
                    i += 1;
                }

                que.setAnswers(answers);
            }
        } else {
            questions = null;
        }

        return questions;
    }

    /**
     * Interface to be implemented on both Android and Desktop Applications.
     * 
     * @author Sergio Torró
     * @since 26/05/2013
     * @version 0.1
     */
    public interface Result {
        /**
         * Checks if the database is empty.
         * 
         * @return If it's empty or not.
         */
        boolean isEmpty();

        /**
         * Move to the next row if any.
         * 
         * @return True if there any any row.
         */
        boolean moveToNext();

        /**
         * It gets the column index by name.
         * 
         * @param name
         *            The column name.
         * @return The index of the column.
         */
        int getColumnIndex(String name);

        /**
         * It returns a float value from the column specified.
         * 
         * @param columnIndex
         *            The column index.
         * @return A float value.
         */
        float getFloat(int columnIndex);

        /**
         * It returns a integer value from the column specified.
         * 
         * @param columnIndex
         *            The column index.
         * @return A integer value.
         */
        int getInt(int columnIndex);

        /**
         * It returns a String value from the column specified.
         * 
         * @param columnIndex
         *            The index of the column.
         * @return A String value.
         */
        String getString(int columnIndex);
    }
}
