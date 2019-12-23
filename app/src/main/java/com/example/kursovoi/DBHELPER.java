package com.example.kursovoi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class DBHELPER extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATAVASE_NAME="kniga.db";
    public static final String MAIN_TABLE="maintable";
    public static final String GENRE_TABLE="genretable";
    public static final String AUTHOR_TABLE="authortable";
    public static final String CITY_TABLE="citytable";
    public static final String PUBLISHER_TABLE="publishertable";




   public ArrayList<String> nameBook;
   public ArrayList<String> author;
   public ArrayList<String> genre;


    public DBHELPER(Context context){
        super(context,DATAVASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MAIN_TABLE + " (ID_MAIN INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_BOOK TEXT,GENRE INTEGER,AUTHOR INTEGER,CITY INTEGER,PUBLISHER INTEGER,YEAR INTEGER,PAGES INTEGER,ISBN TEXT, PICTURES BLOB," +
                "FOREIGN KEY(GENRE) REFERENCES genretable (ID_GENRE) ,FOREIGN KEY(AUTHOR) REFERENCES authortable(ID_AUTHOR),FOREIGN KEY(CITY) REFERENCES citytable(ID_CITY)," +
                "FOREIGN KEY(PUBLISHER) REFERENCES publishertable (ID_PUBLISHER))");
        db.execSQL("CREATE TABLE " + GENRE_TABLE + " (ID_GENRE INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_GENRE TEXT)");
        db.execSQL("CREATE TABLE " + AUTHOR_TABLE + " (ID_AUTHOR INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_AUTHOR TEXT)");
        db.execSQL("CREATE TABLE " + CITY_TABLE + " (ID_CITY INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_CITY TEXT)");
        db.execSQL("CREATE TABLE " + PUBLISHER_TABLE + " (ID_PUBLISHER INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_PUBLISHER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GENRE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AUTHOR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PUBLISHER_TABLE);
        onCreate(db);
    }
    public void insertLabel(String label,String labe2,String labe3,String labe4,String labe5,String labe6,String labe7,String labe8 ) {
        SQLiteDatabase db = this.getWritableDatabase();
        checkAuthor(labe5);
        checkGenre(labe4);
        checkCity(labe6);
        checkPublisher(labe7);
        db.execSQL("INSERT INTO maintable(NAME_OF_BOOK,GENRE,AUTHOR,CITY,PUBLISHER,YEAR,PAGES,ISBN) VALUES ('" + label + "',(SELECT ID_GENRE FROM genretable WHERE NAME_OF_GENRE='"+ labe4 +"'),(SELECT ID_AUTHOR FROM authortable WHERE NAME_OF_AUTHOR='"+ labe5 +"'),(SELECT ID_CITY FROM citytable WHERE NAME_OF_CITY='"+ labe6 +"'),(SELECT ID_PUBLISHER FROM publishertable WHERE NAME_OF_PUBLISHER='"+ labe7 +"'),"+ labe2 +","+ labe3+",'" + labe8 + "' )");
      // db.execSQL("INSERT INTO genretable(NAME_OF_GENRE) VALUES ('" + labe4 + "')");
      // db.execSQL("INSERT INTO authortable(NAME_OF_AUTHOR) VALUES ('" + labe5 + "')");
      // db.execSQL("INSERT INTO citytable(NAME_OF_CITY) VALUES ('" + labe6 + "')");
      // db.execSQL("INSERT INTO publishertable(NAME_OF_PUBLISHER) VALUES ('" + labe7 + "')");
        db.close(); // Closing database connection
    }
    public boolean checkAuthor(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList= new ArrayList<String>();
        String selectAuthor = "SELECT  * FROM " + AUTHOR_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {
                stringList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
      //  db.close();
            if(stringList.contains(value)){
                return true;
            }else{
                db.execSQL("INSERT INTO authortable(NAME_OF_AUTHOR) VALUES ('" + value + "')");
            }
        db.close();
            return false;
    }
    public boolean checkGenre(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList= new ArrayList<String>();
        String selectAuthor = "SELECT  * FROM " + GENRE_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {
                stringList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
      //  db.close();
        if(stringList.contains(value)){
            return true;
        }else{
            db.execSQL("INSERT INTO genretable(NAME_OF_GENRE) VALUES ('" + value + "')");
        }
        db.close();
        return false;
    }
    public boolean checkCity(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList= new ArrayList<String>();
        String selectAuthor = "SELECT  * FROM " + CITY_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {
                stringList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
     //   db.close();
        if(stringList.contains(value)){
            return true;
        }else{
            db.execSQL("INSERT INTO citytable(NAME_OF_CITY) VALUES ('" + value + "')");
        }
        db.close();
        return false;
    }
    public boolean checkPublisher(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList= new ArrayList<String>();
        String selectAuthor = "SELECT  * FROM " + PUBLISHER_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {
                stringList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
      //  db.close();
        if(stringList.contains(value)){
            return true;
        }else{
            db.execSQL("INSERT INTO publishertable(NAME_OF_PUBLISHER) VALUES ('" + value + "')");
        }
        db.close();
        return false;
    }
    public ArrayList<String> returndData(){

        ArrayList<String> result=new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectAuthor = "SELECT NAME_OF_BOOK,(SELECT NAME_OF_AUTHOR  from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_GENRE  from genretable where ID_GENRE=GENRE) fROM " + MAIN_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {

                result.add(cursor.getString(0)+":"+cursor.getString(1)+":"+cursor.getString(2));
              //  nameBook.add();
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        return result;
    }
    public ArrayList<String> description(String value){
        ArrayList<String> result=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select="SELECT NAME_OF_BOOK,(SELECT NAME_OF_GENRE from genretable where ID_GENRE=GENRE),(SELECT NAME_OF_AUTHOR from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_CITY from citytable where ID_CITY=CITY),(SELECT NAME_OF_PUBLISHER from publishertable where ID_PUBLISHER=PUBLISHER),YEAR,PAGES,ISBN from maintable where NAME_OF_BOOK='"+value +"'";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
                result.add(cursor.getString(1));
                result.add(cursor.getString(2));
                result.add(cursor.getString(3));
                result.add(cursor.getString(4));
                result.add(cursor.getString(5));
                result.add(cursor.getString(6));
                result.add(cursor.getString(7));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  result;
    }
    public ArrayList<String> Genre(){
        ArrayList<String> result=new ArrayList<>();
        result.add("");
        SQLiteDatabase db = this.getWritableDatabase();
        String select="SELECT NAME_OF_GENRE from genretable";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  result;
    }

    public ArrayList<String> City(){
        ArrayList<String> result=new ArrayList<>();
        result.add("");
        SQLiteDatabase db = this.getWritableDatabase();
        String select="SELECT NAME_OF_CITY from citytable";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  result;
    }
    public ArrayList<String> Search(String quere){
        ArrayList<String> result=new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
       // String select=quere;
        Cursor cursor = db.rawQuery(quere, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  result;
    }
    public ArrayList<String> Publisher(){
        ArrayList<String> result=new ArrayList<>();
        result.add("");
        SQLiteDatabase db = this.getWritableDatabase();
        String select="SELECT NAME_OF_PUBLISHER from publishertable";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  result;
    }
    public void deleteRecord(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from maintable where NAME_OF_BOOK ='"+value+"'");
    }

}
