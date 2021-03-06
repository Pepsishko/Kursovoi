package com.example.kursovoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class Search extends Activity {
    EditText nameEdit;
    EditText bookEdit;
    EditText page;
    EditText page1;
    EditText year;
    EditText year1;
    EditText isbn;
    Spinner genre;
    Spinner city;
    Spinner publisher;
    DBHELPER dbHelper;
    ListView listView;
   // String request="SELECT NAME_OF_BOOK,(SELECT NAME_OF_GENRE from genretable where ID_GENRE=GENRE),(SELECT NAME_OF_AUTHOR from authortable where ID_AUTHOR=AUTHOR),(SELECT NAME_OF_CITY from citytable where ID_CITY=CITY),(SELECT NAME_OF_PUBLISHER from publishertable where ID_PUBLISHER=PUBLISHER),YEAR,PAGES,ISBN from maintable where ";
    String request1 ="SELECT NAME_OF_BOOK,NAME_OF_GENRE,NAME_OF_AUTHOR,NAME_OF_CITY,NAME_OF_PUBLISHER,maintable.YEAR,maintable.PAGES,maintable.ISBN,maintable.ID_MAIN " +
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
        page.setInputType(InputType.TYPE_CLASS_NUMBER);
        page1=findViewById(R.id.pageEdit2);
        page1.setInputType(InputType.TYPE_CLASS_NUMBER);
        year=findViewById(R.id.yearEdit1);
        year.setInputType(InputType.TYPE_CLASS_NUMBER);
        year1=findViewById(R.id.yearEdit2);
        year1.setInputType(InputType.TYPE_CLASS_NUMBER);
        isbn=findViewById(R.id.isbnEdit);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,dbHelper.genre());
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,dbHelper.city());
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,dbHelper.publisher());
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
        if(!page.getText().toString().equals("")||!(page1.getText().toString().equals(""))){
            if(!page.getText().toString().equals(""))
            request+="maintable.PAGES="+page.getText().toString()+" AND ";
            if(!page1.getText().toString().equals(""))
                request+="maintable.PAGES="+page1.getText().toString()+" AND ";
        }
        if(!year.getText().toString().equals("")&&!(year1.getText().toString().equals(""))){
            request+="maintable.YEAR BETWEEN "+year.getText().toString()+" AND "+year1.getText().toString()+" AND ";
        }
        if(!isbn.getText().toString().equals("")){
            request+="maintable.ISBN="+"\""+isbn.getText().toString()+"\""+" AND ";
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


        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> item;
        ArrayList<String> news=dbHelper.search(str);
        for(int i=0;i<news.size();i++){
            item=new HashMap<String, String>();
            item.put("line1",news.get(i).split(":")[0].trim());
            item.put("line2",news.get(i).split(":")[1].trim());
            item.put("line3",news.get(i).split(":")[2].trim());
            item.put("line4",news.get(i).split(":")[3].trim());
            list.add(item);
        }
        SimpleAdapter sa=new SimpleAdapter(this, list,R.layout.multi_lines,new String[] { "line1","line2", "line3","line4"},new int[] {R.id.line_a, R.id.line_b, R.id.line_c,R.id.line_d});

        listView.setAdapter(sa);
        ((ListView)findViewById(R.id.searchBo)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> obj=(HashMap<String,String>)parent.getItemAtPosition(position);
                Intent i = new Intent(getApplicationContext(),BookDescription.class);
                String string= obj.get("line4");
                i.putExtra("name", string);
                startActivityForResult(i,97);

            }


            //  simple_spinner_item
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==97){
            listView.setAdapter(null);

        }
    }

    /**
     * Вызов метода формирования запроса поиска
     * @param view параметр отвечающий за отображение
     */
    public void searchBTNClick(View view) {
       formation();
    }
}
