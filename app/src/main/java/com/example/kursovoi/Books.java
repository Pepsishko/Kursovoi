package com.example.kursovoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class Books extends Activity {
    DBHELPER dbHelper;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter sa;
    /**
     * Задаёт начальную установку параметров при инициализации активности
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        dbHelper=new DBHELPER(this);
        HashMap<String,String> item;
        ArrayList<String> news=dbHelper.returndData();
            for(int i=0;i<news.size();i++){
                item=new HashMap<String, String>();
                item.put("line1",news.get(i).split(":")[0].trim());
                item.put("line2",news.get(i).split(":")[1].trim());
                item.put("line3",news.get(i).split(":")[2].trim());
                item.put("line4",news.get(i).split(":")[3].trim());
                list.add(item);
            }
        sa = new SimpleAdapter(this, list,R.layout.multi_lines,new String[] { "line1","line2", "line3","line4"},new int[] {R.id.line_a, R.id.line_b, R.id.line_c,R.id.line_d});
        ((ListView)findViewById(R.id.listView)).setAdapter(sa);

        ((ListView)findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             HashMap<String,String> obj=(HashMap<String,String>)parent.getItemAtPosition(position);
             Intent i = new Intent(getApplicationContext(),BookDescription.class);
             String string= obj.get("line4");
             i.putExtra("name", string);
             startActivityForResult(i,1);

         }


     //  simple_spinner_item
      });

    }

    /**
     * Получение данных от вызаемой Activity
     * @param requestCode используется, чтобы отличать друг от друга пришедшие результаты
     * @param resultCode позволяет определить успешно прошел вызов или нет
     * @param data содержит данные с предыдущего Intent'а
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            list.clear();
            HashMap<String,String> item;
            ArrayList<String> news=dbHelper.returndData();
            for(int i=0;i<news.size();i++){
                item=new HashMap<String, String>();
                item.put("line1",news.get(i).split(":")[0].trim());
                item.put("line2",news.get(i).split(":")[1].trim());
                item.put("line3",news.get(i).split(":")[2].trim());
                item.put("line4",news.get(i).split(":")[3].trim());
                list.add(item);
            }
            sa = new SimpleAdapter(this, list,R.layout.multi_lines,new String[] { "line1","line2", "line3","line4"},new int[] {R.id.line_a, R.id.line_b, R.id.line_c,R.id.line_d});
            ((ListView)findViewById(R.id.listView)).setAdapter(sa);

            ((ListView)findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,String> obj=(HashMap<String,String>)parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(),BookDescription.class);
                    String string= obj.get("line4");
                    i.putExtra("name", string);
                    startActivityForResult(i,1);
                }
            });
        }
        if(requestCode==65){
            list.clear();
            HashMap<String,String> item;
            ArrayList<String> news=dbHelper.returndData();
            for(int i=0;i<news.size();i++){
                item=new HashMap<String, String>();
                item.put("line1",news.get(i).split(":")[0].trim());
                item.put("line2",news.get(i).split(":")[1].trim());
                item.put("line3",news.get(i).split(":")[2].trim());
                item.put("line4",news.get(i).split(":")[3].trim());
                list.add(item);
            }
            sa = new SimpleAdapter(this, list,R.layout.multi_lines,new String[] { "line1","line2", "line3","line4"},new int[] {R.id.line_a, R.id.line_b, R.id.line_c,R.id.line_d});
            ((ListView)findViewById(R.id.listView)).setAdapter(sa);
            ((ListView)findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,String> obj=(HashMap<String,String>)parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(),BookDescription.class);
                    String string= obj.get("line4");
                    i.putExtra("name", string);
                    startActivityForResult(i,1);
                }
            });
        }
        if(requestCode==77){
            list.clear();
            HashMap<String,String> item;
            ArrayList<String> news=dbHelper.returndData();
            for(int i=0;i<news.size();i++){
                item=new HashMap<String, String>();
                item.put("line1",news.get(i).split(":")[0].trim());
                item.put("line2",news.get(i).split(":")[1].trim());
                item.put("line3",news.get(i).split(":")[2].trim());
                item.put("line4",news.get(i).split(":")[3].trim());
                list.add(item);
            }
            sa = new SimpleAdapter(this, list,R.layout.multi_lines,new String[] { "line1","line2", "line3","line4"},new int[] {R.id.line_a, R.id.line_b, R.id.line_c,R.id.line_d});
            ((ListView)findViewById(R.id.listView)).setAdapter(sa);
            ((ListView)findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String,String> obj=(HashMap<String,String>)parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(),BookDescription.class);
                    String string= obj.get("line4");
                    i.putExtra("name", string);
                    startActivityForResult(i,1);
                }
            });
        }
    }

    /**
     * Button для открытия Activity поиска книг
     * @param view параметр отвечающий за отображение
     */
    public void searchClick(View view) {
        Intent intentView = new Intent(this, Search.class);
        startActivityForResult(intentView,77);
    }

    /**
     * Button для открытия Activity для добавления книг
     * @param view параметр отвечающий за отображение
     */
    public void loadBook(View view) {
        Intent intentView = new Intent(this, MainActivity.class);
        startActivityForResult(intentView,65);
    }
}
