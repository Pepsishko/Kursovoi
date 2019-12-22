package com.example.kursovoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class BookDescription extends Activity {
    TextView textView;
    ArrayList<String> strings;
    DBHELPER dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent= getIntent();
        String text= intent.getExtras().getString("name");
        String[] split= text.split("/");
        split[0]=split[0].trim();
        split[1]=split[1].trim();
        dbhelper=new DBHELPER(this);
        strings= dbhelper.description(split[0]);
        textView=findViewById(R.id.description);
        textView.setText("Название книги : "+ strings.get(0)+"\n"+"Жанр : "+strings.get(1)+"\n"+"Автор : "+strings.get(2)+"\n"+
                "Город : "+strings.get(3)+"\n"+"Издательство : "+strings.get(4)+"\n"+"Год : "+strings.get(5)+"\n"+"Количество страниц : "+ strings.get(6)+"\n"+"ISBN : "+strings.get(7));
    }

    public void deleteBook(View view) {
        dbhelper.deleteRecord(strings.get(0));
        Intent intentView = new Intent(this, Books.class);
        startActivity(intentView);
    }
}
