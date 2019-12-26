package com.example.kursovoi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UpdateDB extends Activity {
    EditText nameEdit;
    EditText bookEdit;
    EditText page;
    EditText year;
    Spinner genre;
    Spinner city;
    Spinner publisher;
    DBHELPER dbHelper;
    ArrayList<String> strings;
    /**
     * Задаёт начальную установку параметров при инициализации активности.
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_book);
        Intent intent = getIntent();
        strings=intent.getExtras().getStringArrayList("update");
        nameEdit=findViewById(R.id.nameEditUpdate);
        bookEdit=findViewById(R.id.bookEditUpdate);
        page=findViewById(R.id.pageEdit1Update);
        year=findViewById(R.id.yearEdit1Update);
        genre=findViewById(R.id.spinnerUpdate);
        city=findViewById(R.id.spinner2Update);
        publisher=findViewById(R.id.spinner3Update);
        dbHelper=new DBHELPER(this);
        nameEdit.setText(strings.get(2));
        bookEdit.setText(strings.get(0));
        page.setText(strings.get(6));
        year.setText(strings.get(5));
        ArrayList<String> genreList= dbHelper.genre();
        ArrayList<String> cityList= dbHelper.city();
        ArrayList<String> publisherList= dbHelper.publisher();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,genreList);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,cityList);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,R.layout.spinner_item_custom,publisherList);
        int getNumGenre=0;
        int getNumCity=0;
        int getNumPublisher=0;
        for(int i=0;i<genreList.size();i++){
            if(genreList.get(i).equals(strings.get(1))){
                getNumGenre=i;
            }

        }
        for(int i=0;i<cityList.size();i++){
            if(cityList.get(i).equals(strings.get(3))){
                getNumCity=i;
            }
        }
        for(int i=0;i<publisherList.size();i++){
            if(publisherList.get(i).equals(strings.get(4))){
                getNumPublisher=i;
            }
        }
        genre.setAdapter(adapter);
        city.setAdapter(adapter1);
        publisher.setAdapter(adapter2);
        genre.setSelection(getNumGenre);
        city.setSelection(getNumCity);
        publisher.setSelection(getNumPublisher);
    }

    /**
     * Метод выполняющий запрос в базу данных на изменение записи в ней
     * @param view параметр отвечающий за отображение
     */
    public void updClickBtn(View view) {
        dbHelper.update(Integer.valueOf(strings.get(8)) ,bookEdit.getText().toString(),genre.getSelectedItem().toString(),nameEdit.getText().toString(),city.getSelectedItem().toString(),publisher.getSelectedItem().toString(),Integer.valueOf(year.getText().toString()),Integer.valueOf(page.getText().toString()));
        finish();
    }
}
