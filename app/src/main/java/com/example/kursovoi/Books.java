package com.example.kursovoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Books extends Activity {
    private String[] mas;
   // ListView listView;
    DBHELPER dbHelper;
    ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    private SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        dbHelper=new DBHELPER(this);
        HashMap<String,Object> item;
        ArrayList<String> news=dbHelper.returndData();
            for(int i=0;i<news.size();i++){
                item=new HashMap<String, Object>();
                item.put("line1",news.get(i).split(":")[0].trim());
                item.put("line2",news.get(i).split(":")[1].trim());
                item.put("line3",news.get(i).split(":")[2].trim());
                item.put("picture",R.mipmap.ic_launcher);
                list.add(item);
            }
        sa = new SimpleAdapter(this, list,R.layout.multi_lines_2,new String[] { "line1","line2", "line3","picture" },new int[] {R.id.textView9, R.id.textView10, R.id.textView11,R.id.imageView});
  //    HashMap<String,String> item;
  //    ArrayList<String> news=dbHelper.returndData();
  //    for(int i=0;i<news.size();i++){
  //        item=new HashMap<String, String>();
  //        item.put("line1",news.get(i).split(":")[0].trim());
  //        item.put("line2",news.get(i).split(":")[1].trim());
  //        item.put("line3",news.get(i).split(":")[2].trim());
  //        list.add(item);
  //    }
  //    sa = new SimpleAdapter(this, list,R.layout.multi_lines,new String[] { "line1","line2", "line3" },new int[] {R.id.line_a, R.id.line_b, R.id.line_c});


        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,dbHelper.returndData());
       //  listView.setAdapter(sa);
        ((ListView)findViewById(R.id.listView)).setAdapter(sa);

        ((ListView)findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             HashMap<String,String> obj=(HashMap<String,String>)parent.getItemAtPosition(position);
             Intent i = new Intent(getApplicationContext(),BookDescription.class);
             String string= obj.get("line1")+"/"+obj.get("line2")+"/"+obj.get("line3");
            // передаем индекс массива
            // i.putExtra("name", ((TextView) view).getText());
             i.putExtra("name", string);
             startActivity(i);

         }


     //  simple_spinner_item
      });

    }

    public void searchClick(View view) {
        Intent intentView = new Intent(this, Search.class);
        startActivity(intentView);
    }
}
