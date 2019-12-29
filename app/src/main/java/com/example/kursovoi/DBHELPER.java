package com.example.kursovoi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHELPER extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATAVASE_NAME = "kniga.db";
    public static final String MAIN_TABLE = "maintable";
    public static final String GENRE_TABLE = "genretable";
    public static final String AUTHOR_TABLE = "authortable";
    public static final String CITY_TABLE = "citytable";
    public static final String PUBLISHER_TABLE = "publishertable";

    /**
     * Конструктор класса
     * @param context контекст Activity
     */
    public DBHELPER(Context context) {
        super(context, DATAVASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Задаёт начальную установку параметров при инициализации активности
     * @param db База данных
     */
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

    /**
     * Обновление БД
     * @param db         объект класса базы данных
     * @param oldVersion старая версия базы данных
     * @param newVersion новая версия базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GENRE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AUTHOR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CITY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PUBLISHER_TABLE);
        onCreate(db);
    }

    /**
     * Метод для добавления книги в базу данных
     * @param label Название книги
     * @param labe2 Год
     * @param labe3 Кол-во страниц
     * @param labe4 Название жанра
     * @param labe5 Имя автора
     * @param labe6 Название города
     * @param labe7 Название издательства
     * @param labe8 ISBN
     */
    public void insertLabel(String label, String labe2, String labe3, String labe4, String labe5, String labe6, String labe7, String labe8) {
        SQLiteDatabase db = this.getWritableDatabase();
        checkAuthor(labe5);
        checkGenre(labe4);
        checkCity(labe6);
        checkPublisher(labe7);
        db.execSQL("INSERT INTO maintable(NAME_OF_BOOK,GENRE,AUTHOR,CITY,PUBLISHER,YEAR,PAGES,ISBN) VALUES ('" + label + "',(SELECT ID_GENRE FROM genretable WHERE NAME_OF_GENRE='" + labe4 + "'),(SELECT ID_AUTHOR FROM authortable WHERE NAME_OF_AUTHOR='" + labe5 + "'),(SELECT ID_CITY FROM citytable WHERE NAME_OF_CITY='" + labe6 + "'),(SELECT ID_PUBLISHER FROM publishertable WHERE NAME_OF_PUBLISHER='" + labe7 + "')," + labe2 + "," + labe3 + ",'" + labe8 + "' )");
        db.close(); // Closing database connection
    }

    /**
     * Проверка наличия автора в БД
     * @param value Имя автора
     * @return возвращает в случае если автор существует возвращает true, в случае если автора не существует в базе данных то он добавляется и возвращается false
     */
    public boolean checkAuthor(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList = new ArrayList<String>();
        String selectAuthor = "SELECT  * FROM " + AUTHOR_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {
                stringList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (stringList.contains(value)) {
            return true;
        } else {
            db.execSQL("INSERT INTO authortable(NAME_OF_AUTHOR) VALUES ('" + value + "')");
        }
    //    db.close();
        return false;
    }

    /**
     * Проверка наличия жанра в БД
     * @param value Название жанра
     * @return возвращает в случае если жанр существует true, в случае если жанра не существует в базе данных то он добавляется и возвращается false
     */
    public boolean checkGenre(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList = new ArrayList<String>();
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
        if (stringList.contains(value)) {
            return true;
        } else {
            db.execSQL("INSERT INTO genretable(NAME_OF_GENRE) VALUES ('" + value + "')");
        }
  //      db.close();
        return false;
    }

    /**
     * Проверка наличия города в БД
     * @param value Название города
     * @return возвращает в случае если город существует true, в случае если города не существует в базе данных то он добавляется и возвращается false
     */
    public boolean checkCity(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList = new ArrayList<String>();
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
        if (stringList.contains(value)) {
            return true;
        } else {
            db.execSQL("INSERT INTO citytable(NAME_OF_CITY) VALUES ('" + value + "')");
        }
    //    db.close();
        return false;
    }

    /**
     * Проверка наличия издательства в БД
     * @param value Название издательства
     * @return возвращает в случае если издатель существует true, в случае если издателя не существует в базе данных то он добавляется и возвращается false
     */
    public boolean checkPublisher(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> stringList = new ArrayList<String>();
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
        if (stringList.contains(value)) {
            return true;
        } else {
            db.execSQL("INSERT INTO publishertable(NAME_OF_PUBLISHER) VALUES ('" + value + "')");
        }
      //  db.close();
        return false;
    }

    /**
     * Метод получения краткого описания книги
     * @return Возвращает строковый лист для отображения существующих книг в базе данных
     */
    public ArrayList<String> returndData() {

        ArrayList<String> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectAuthor = "SELECT NAME_OF_BOOK,(SELECT NAME_OF_AUTHOR  from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_GENRE  from genretable where ID_GENRE=GENRE),ID_MAIN fROM " + MAIN_TABLE;
        Cursor cursor = db.rawQuery(selectAuthor, null);
        if (cursor.moveToFirst()) {
            do {

                result.add(cursor.getString(0) + ":" + cursor.getString(1) + ":" + cursor.getString(2) + ":" + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
     //   db.close();
        return result;
    }

    public ArrayList<String> description(String value, String value1) {
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT NAME_OF_BOOK,(SELECT NAME_OF_GENRE from genretable where ID_GENRE=GENRE),(SELECT NAME_OF_AUTHOR from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_CITY from citytable where ID_CITY=CITY),(SELECT NAME_OF_PUBLISHER from publishertable where ID_PUBLISHER=PUBLISHER),YEAR,PAGES,ISBN,ID_MAIN from maintable where NAME_OF_BOOK='" + value + "' AND (SELECT NAME_OF_AUTHOR from authortable where ID_AUTHOR=AUTHOR)='" + value1 + "'";
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
                result.add(cursor.getString(8));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * Получение подробной информации о книге
     * @param ID Идентификатор записи в базе данных
     * @return Лист со всеми записями в таблице
     */
    public ArrayList<String> description(int ID) {
        ArrayList<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT NAME_OF_BOOK,(SELECT NAME_OF_GENRE from genretable where ID_GENRE=GENRE),(SELECT NAME_OF_AUTHOR from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_CITY from citytable where ID_CITY=CITY),(SELECT NAME_OF_PUBLISHER from publishertable where ID_PUBLISHER=PUBLISHER),YEAR,PAGES,ISBN,ID_MAIN from maintable where ID_MAIN=" + ID + "";
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
                result.add(cursor.getString(8));
            } while (cursor.moveToNext());
        }
        cursor.close();
       // db.close();
        return result;
    }

    /**
     * Изменение записи
     * @param id        Идентификатор записи в базе данных
     * @param nameBook  Название книги
     * @param genre     Жанр книги
     * @param author    Автор книги
     * @param city      Город издательства книги
     * @param publisher Издатель книги
     * @param year      Год книги
     * @param page      Кол-во страниц книги
     */
    public void update(int id, String nameBook, String genre, String author, String city, String publisher, int year, int page,String isbn) {
        checkAuthor(author);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE maintable SET NAME_OF_BOOK='" + nameBook + "',GENRE=" + returnIDGenre(genre) + ",AUTHOR=" + returnIDAuthor(author) + ",CITY=" + returnIDCity(city) + ",PUBLISHER=" + returnIDPublisher(publisher) + ",YEAR=" + year + ",PAGES=" + page + ",ISBN='"+isbn+"' where ID_MAIN=" + id + "");

    }

    /**
     * Метод для получения ID жанра
     * @param value Строковый параметр необходимый для составления запроса по поиску ID жанра
     * @return возвращает ID жанра
     */
    int returnIDGenre(String value) {
        int returnGenre = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT ID_GENRE from genretable where NAME_OF_GENRE='" + value + "'";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                returnGenre = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return returnGenre;
    }

    /**
     * Метод для получения ID автора
     * @param value Строковый параметр необходимый для составления запроса по поиску ID автора
     * @return возвращает ID автора
     */
    int returnIDAuthor(String value) {
        int returnAuthor = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT ID_AUTHOR from authortable where NAME_OF_AUTHOR='" + value + "'";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                returnAuthor = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return returnAuthor;
    }

    /**
     * Метод для получения ID города
     * @param value Строковый параметр необходимый для составления запроса по поиску ID города
     * @return возвращает ID города
     */
    int returnIDCity(String value) {
        int returnCity = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT ID_CITY from citytable where NAME_OF_CITY='" + value + "'";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                returnCity = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return returnCity;
    }

    /**
     * Метод для получения ID издательства
     * @param value Строковый параметр необходимый для составления запроса по поиску ID издательства
     * @return возвращает ID издательства
     */
    int returnIDPublisher(String value) {
        int returnPub = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT ID_PUBLISHER from publishertable where NAME_OF_PUBLISHER='" + value + "'";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                returnPub = cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        return returnPub;
    }

    /**
     * Метод для получения всех жанров
     * @return Возвращает лист с жанрами для элемента Spinner
     */
    public ArrayList<String> genre() {
        ArrayList<String> result = new ArrayList<>();
        result.add("");
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT NAME_OF_GENRE from genretable";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Метод для получения всех городов
     * @return Возвращает лист с городами для элемента Spinner
     */
    public ArrayList<String> city() {
        ArrayList<String> result = new ArrayList<>();
        result.add("");
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT NAME_OF_CITY from citytable";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Метод для поиска книг в базе данных
     * @param quere Сформированный запрос в базу данных, для поиска книг
     * @return Возвращает лист с результатами запроса
     */
    public ArrayList<String> search(String quere) {
        ArrayList<String> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(quere, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0)+":"+cursor.getString(2)+":"+cursor.getString(1)+":"+cursor.getString(8));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Метод для получения всего списка с издательствами
     * @return Возаращает лист с издательствами для загрузки в элемент Spinner
     */
    public ArrayList<String> publisher() {
        ArrayList<String> result = new ArrayList<>();
        result.add("");
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT NAME_OF_PUBLISHER from publishertable";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Удаление записи
     * @param value ID записи для удаления
     */
    public void deleteRecord(int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from maintable where ID_MAIN =" + value + "");
    }


}
