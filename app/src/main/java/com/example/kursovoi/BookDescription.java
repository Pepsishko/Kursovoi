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
    String text;
    static final private int CHOOSE_PREP = 0;

    /**
     * Задаёт начальную установку параметров при инициализации активности
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent= getIntent();
        text= intent.getExtras().getString("name");
        dbhelper=new DBHELPER(this);
        strings= dbhelper.description(Integer.valueOf(text));
        textView=findViewById(R.id.description);
        textView.setText("Название книги : "+ strings.get(0)+"\n"+"Жанр : "+strings.get(1)+"\n"+"Автор : "+strings.get(2)+"\n"+
                "Город : "+strings.get(3)+"\n"+"Издательство : "+strings.get(4)+"\n"+"Год : "+strings.get(5)+"\n"+"Количество страниц : "+ strings.get(6)+"\n"+"ISBN : "+strings.get(7));
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
        if(requestCode==CHOOSE_PREP){
            strings= dbhelper.description(Integer.valueOf(text));
            textView.setText("Название книги : "+ strings.get(0)+"\n"+"Жанр : "+strings.get(1)+"\n"+"Автор : "+strings.get(2)+"\n"+
                    "Город : "+strings.get(3)+"\n"+"Издательство : "+strings.get(4)+"\n"+"Год : "+strings.get(5)+"\n"+"Количество страниц : "+ strings.get(6)+"\n"+"ISBN : "+strings.get(7));

        }
    }

    /**
     * Button для удаления книги
     * @param view параметр отвечающий за отображение
     */
    public void deleteBook(View view) {
        dbhelper.deleteRecord(Integer.valueOf(text));
        finish();
    }

    /**
     * Button для открытия активности обновления книг
     * @param view параметр отвечающий за отображение
     */
    public void updateClick(View view) {
        Intent intentView = new Intent(this, UpdateDB.class);
        intentView.putExtra("update",strings);
        startActivityForResult(intentView,CHOOSE_PREP);

    }
}
