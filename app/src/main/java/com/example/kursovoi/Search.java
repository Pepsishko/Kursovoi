package com.example.kursovoi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class Search extends Activity {
    EditText nameEdit;
    EditText bookEdit;
    EditText page;
    EditText page1;
    EditText year;
    EditText year1;
    Spinner genre;
    Spinner city;
    Spinner publisher;
    DBHELPER dbHelper;
    ListView listView;
   // String request="SELECT NAME_OF_BOOK,(SELECT NAME_OF_GENRE from genretable where ID_GENRE=GENRE),(SELECT NAME_OF_AUTHOR from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_CITY from citytable where ID_CITY=CITY),(SELECT NAME_OF_PUBLISHER from publishertable where ID_PUBLISHER=PUBLISHER),YEAR,PAGES,ISBN from maintable where ";
    String request1 ="SELECT NAME_OF_BOOK,NAME_OF_GENRE,NAME_OF_AUTHOR,NAME_OF_CITY,NAME_OF_PUBLISHER,maintable.YEAR,maintable.PAGES,maintable.ISBN " +
           "from maintable,genretable,publishertable,citytable,authortable where maintable.GENRE=genretable.ID_GENRE AND maintable.AUTHOR=authortable.ID_AUTHOR AND " +
           "maintable.CITY=citytable.ID_CITY AND maintable.PUBLISHER=publishertable.ID_PUBLISHER AND ";

    /**
     * Задаёт начальную установку параметров при инициализации активности
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_books);
        nameEdit=findViewById(R.id.nameEdit);
        dbHelper=new DBHELPER(this);
        bookEdit=findViewById(R.id.bookEdit);
        listView=findViewById(R.id.searchBo);
        genre=findViewById(R.id.spinner);
        city=findViewById(R.id.spinner2);
        publisher=findViewById(R.id.spinner3);
        page=findViewById(R.id.pageEdit1);
        page1=findViewById(R.id.pageEdit2);
        year=findViewById(R.id.yearEdit1);
        year1=findViewById(R.id.yearEdit2);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dbHelper.genre());
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dbHelper.city());
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dbHelper.publisher());
        genre.setAdapter(adapter);
        city.setAdapter(adapter1);
        publisher.setAdapter(adapter2);
    }

    /**
     * Формирование запроса по поиску книг(и) и отображение в ListView
     */
    void formation(){
        String request=request1;
        if(!nameEdit.getText().toString().equals("")) {
            request+="authortable.NAME_OF_AUTHOR="+"\""+nameEdit.getText().toString()+"\""+" AND ";
        }
        if(!bookEdit.getText().toString().equals("")){
            request+="maintable.NAME_OF_BOOK="+"\""+bookEdit.getText().toString()+"\""+" AND ";
        }
        if(!genre.getSelectedItem().toString().equals("")){
            request+="genretable.NAME_OF_GENRE="+"\""+genre.getSelectedItem().toString()+"\""+" AND ";
        }
        if(!city.getSelectedItem().toString().equals("")){
            request+="citytable.NAME_OF_CITY="+"\""+city.getSelectedItem().toString()+"\""+" AND ";
        }
        if(!publisher.getSelectedItem().toString().equals("")){
            request+="publishertable.NAME_OF_PUBLISHER="+"\""+publisher.getSelectedItem().toString()+"\""+" AND ";
        }
        if(!page.getText().toString().equals("")&&!(page1.getText().toString().equals(""))){
            request+="maintable.PAGES BETWEEN "+page.getText().toString()+" AND "+page1.getText().toString()+" AND ";
        }
        if(!year.getText().toString().equals("")&&!(year1.getText().toString().equals(""))){
            request+="maintable.YEAR BETWEEN "+year.getText().toString()+" AND "+year1.getText().toString()+" AND ";
        }
        String var=request.trim();
        String[] varSplit=var.split(" ");
        for(int i=0;i<varSplit.length;i++){
            if((varSplit[i].equals("AND"))&&(i==varSplit.length-1))
            {
                varSplit[i]="";
                break;
            }
        }
        String str="";
        for(int i=0;i<varSplit.length;i++){

            str+=varSplit[i]+" ";

        }
        str=str.trim();
        ArrayAdapter<String> searchAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dbHelper.search(str));
        listView.setAdapter(searchAdapter);

    }
    /**
     * Вызов метода формирования запроса поиска
     * @param view параметр отвечающий за отображение
     */
    public void searchBTNClick(View view) {
       formation();
    }
}
