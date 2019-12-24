package com.example.kursovoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

public class UpdateDB extends Activity {
    EditText nameEdit;
    EditText bookEdit;
    EditText page;
    EditText year;
    Spinner genre;
    Spinner city;
    Spinner publisher;
    DBHELPER dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_book);
        Intent intent = getIntent();
        String te=intent.getExtras().getString("update");
        String[] split= te.split("/");
        nameEdit=findViewById(R.id.nameEditUpdate);
        bookEdit=findViewById(R.id.bookEditUpdate);
        page=findViewById(R.id.pageEdit1Update);
        year=findViewById(R.id.yearEdit1Update);
        genre=findViewById(R.id.spinnerUpdate);
        city=findViewById(R.id.spinner2Update);
        publisher=findViewById(R.id.spinner3Update);
        dbHelper=new DBHELPER(this);
        nameEdit.setText(split[2]);
        bookEdit.setText(split[0]);
        page.setText(split[6]);
        year.setText(split[5]);

    }

    public void updClickBtn(View view) {

    }
}
