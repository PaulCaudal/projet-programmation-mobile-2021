package fr.android.projet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private final String match="create table Matchs("
            +"ID INTEGER PRIMARY KEY,"
            +"Date TEXT NOT NULL,"
            +"Loc TEXT ,"
            +"Winner INTEGER NOT NULL,"
            +"Loser INTEGER NOT NULL,"
            +"Joueur1 TEXT NOT NULL,"
            +"Joueur2 TEXT NOT NULL);";

    private String set="create table Sets("
            +"IDMatch INTEGER NOT NULL,"
            +"NumSet INTEGER NOT NULL,"
            +"Score1 INTEGER NOT NULL,"
            +"Score2 INTEGER NOT NULL);";

    private String jeu="create table Jeux("
            +"IDMatch INTEGER NOT NULL,"
            +"NumSet INTEGER NOT NULL,"
            +"NumJeu INTEGER NOT NULL,"
            +"PremierserviceJ1 INTEGER,"
            +"DeuxiemeserviceJ1 INTEGER,"
            +"aceJ1 INTEGER,"
            +"DoublefauteJ1 INTEGER,"
            +"PointgagnantJ1 INTEGER,"
            +"FautePJ1 INTEGER,"
            +"FauteDJ1 INTEGER,"
            +"PremierserviceJ2 INTEGER,"
            +"DeuxiemeserviceJ2 INTEGER,"
            +"aceJ2 INTEGER,"
            +"DoublefauteJ2 INTEGER,"
            +"PointgagnantJ2 INTEGER,"
            +"FautePJ2 INTEGER,"
            +"FauteDJ2 INTEGER);";

    private String uri="create table Uris("
            +"IDMatch INTEGER NOT NULL,"
            +"uri Text NOT NULL);";


    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(match);
        db.execSQL(set);
        db.execSQL(jeu);
        db.execSQL(uri);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
