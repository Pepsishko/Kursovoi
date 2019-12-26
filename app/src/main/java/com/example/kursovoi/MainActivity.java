package com.example.kursovoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends Activity {
    private static final String TAG = "Prov";
    EditText txt;
    TextView text;
    DBHELPER dbhelper;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 10001;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 20002;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    final int PIC_CROP = 2;
    byte[] massPhoto;
    private Uri outputFileUri;
    Bitmap bp;
    public static String DATA_PATH;

    /**
     * Просмотр разрешений
     * @param requestCode используется, чтобы отличать друг от друга пришедшие результаты
     * @param permissions список разрешений
     * @param grantResults здесь хранятся результаты по каждому из запрашиваемых разрешений
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Разрешения получены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Разрешения не получены", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Задаёт начальную установку параметров при инициализации активности
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.editText2);
        text=findViewById(R.id.textView);
        if (isPermissionGranted(READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Разрешения есть, можно работать", Toast.LENGTH_SHORT).show();
        } else {
            // иначе запрашиваем разрешение у пользователя
            requestPermission(READ_EXTERNAL_STORAGE, REQUEST_READ_EXTERNAL_STORAGE);
        }
        if (isPermissionGranted(WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Разрешения есть, можно работать", Toast.LENGTH_SHORT).show();
        } else {
            // иначе запрашиваем разрешение у пользователя
            requestPermission(WRITE_EXTERNAL_STORAGE, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        OpenCVLoader.initDebug();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        dbhelper=new DBHELPER(this);
    }

    /**
     * Проверка разрешения
     * @param permission строковое название разрешения
     * @return true-если есть это разрешение, false-если нет
     */
    private boolean isPermissionGranted(String permission) {
        // проверяем разрешение - есть ли оно у нашего приложения
        int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
        // true - если есть, false - если нет
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    /**
     *
     * @param permission строковое название разрешения
     * @param requestCode используется, чтобы отличать друг от друга результаты
     */
    private void requestPermission(String permission, int requestCode) {
        // запрашиваем разрешение
        ActivityCompat.requestPermissions(this,
                new String[]{permission}, requestCode);
    }

    public static Bitmap createContrast(Bitmap src, double value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (R < 0) {
                    R = 0;
                } else if (R > 255) {
                    R = 255;
                }

                G = Color.red(pixel);
                G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (G < 0) {
                    G = 0;
                } else if (G > 255) {
                    G = 255;
                }

                B = Color.red(pixel);
                B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if (B < 0) {
                    B = 0;
                } else if (B > 255) {
                    B = 255;
                }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        return bmOut;
    }


    public static Bitmap create(Bitmap src) {
        // image size
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int color = src.getPixel(x, y);

// extract each color component
                int red = (color >>> 16) & 0xFF;
                int green = (color >>> 8) & 0xFF;
                int blue = (color >>> 0) & 0xFF;

// calc luminance in range 0.0 to 1.0; using SRGB luminance constants
                float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;

// choose brightness threshold as appropriate:
                if (luminance >= 0.5f) {
                    bmOut.setPixel(x, y, Color.WHITE);
                } else {
                    bmOut.setPixel(x, y, Color.BLACK);
                }
            }

        }

        return bmOut;
    }


    /**
     * Преобразование изображения сначала в черно-белое, затем в бинарный вид
     * @param src Исходное изображение
     * @return Обработанное изображение
     */
    public Bitmap open(Bitmap src) {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
        Mat imageMat = new Mat();
        Utils.bitmapToMat(src, imageMat);
        Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(imageMat, imageMat, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Utils.matToBitmap(imageMat, bmOut);
        return bmOut;
    }

    public static Bitmap convertImage(Bitmap original) {
        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        int A, R, G, B;
        int colorPixel;
        int width = original.getWidth();
        int height = original.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                colorPixel = original.getPixel(x, y);
                A = Color.alpha(colorPixel);
                R = Color.red(colorPixel);
                G = Color.green(colorPixel);
                B = Color.blue(colorPixel);

                R = (R + G + B) / 3;
                G = R;
                B = R;
                finalImage.setPixel(x, y, Color.argb(A, R, G, B));
            }

        }

        return finalImage;
    }

    /**
     * Метод распознания текста
     * @return Распознанный текст
     */
    private String extractText() {
      //  Bitmap bitmap= BitmapFactory.decodeByteArray(massPhoto,0,massPhoto.length);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.testx);
        Test();
        Test1();
       //  Bitmap btmp = open(bp);
        Bitmap btmp = open(bitmap);
        TessBaseAPI tessBaseApi = new TessBaseAPI();
        tessBaseApi.init("/mnt/sdcard/tesseract", "rus");
        // tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST,"ISBN");
         tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST,";");
        tessBaseApi.setImage(btmp);
        String extractedText = tessBaseApi.getUTF8Text();
        tessBaseApi.end();
        return extractedText;
    }
    /**
     * Метод создания папки для файла .traineddata
     */
    public void Test() {

        File directory = new File("/mnt/sdcard/tesseract", "tessdata");
        if (!directory.exists())
            directory.mkdirs();
        else {
            Log.d(TAG, "Папка уже существует");
        }
        if (directory.exists())
            Log.d(TAG, "Создана");
        else
            Log.d(TAG, "Не создана");

    }

    /**
     * Метод создания записи файла в папку tessdata файла .traineddata
     */
    public void Test1() {
        File document = new File("/mnt/sdcard/tesseract/tessdata", "rus.traineddata");
        if(!document.exists()){
        Resources r = this.getResources();
        InputStream is = r.openRawResource(R.raw.rus);
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            FileOutputStream outputStream = new FileOutputStream(document.getPath());
            outputStream.write(buffer);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    Work work=new Work();

    /**
     * Получение данных от вызаемой Activity
     * @param requestCode используется, чтобы отличать друг от друга пришедшие результаты
     * @param resultCode позволяет определить успешно прошел вызов или нет
     * @param data содержит данные с предыдущего Intent'а
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
                performCrop();
            }
            if (requestCode == PIC_CROP) {
                try {
                    InputStream pictureInputStream = this.getContentResolver().openInputStream(outputFileUri);
                    bp = BitmapFactory.decodeStream(pictureInputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                txt.setText(extractText());
                work.load(txt.getText().toString());
            }


        }
    }
    /**
     * Button для начала работы с камерой
     * @param view параметр отвечающий за отображение
     */
    public void onPhotoClick(View view) {

        saveFullImage();

    }

    /**
     * Сохранение изображения в памяти телефона
     */
    private void saveFullImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
        outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);

    }

    /**
     * Кадрирование фотографии
     */
    private void performCrop() {
        try {

            // Намерение для кадрирования. Не все устройства поддерживают его
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(outputFileUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("output",outputFileUri);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "Извините, но ваше устройство не поддерживает кадрирование";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    String[] strings;

    /**
     * Button для просмотра распознанного текста
     * @param view параметр отвечающий за отображение
     */
    public void onClick1(View view) {
        try {
            work.load(txt.getText().toString());
            strings = work.splitter();
            text.setText("Название книги : " + strings[0] + "\n" + "Жанр : " + strings[1] + "\n" + "Автор : " + strings[2] + "\n" +
                    "Город : " + strings[3] + "\n" + "Издательство : " + strings[4] + "\n" + "Год : " + strings[5] + "\n" + "Количество страниц : " + strings[6] + "\n" + "ISBN : " + strings[7]);
        }
        catch (IllegalStateException ex){
            toast("Нет изображения, чтобы \n просмотреть данные").show();
        }
        catch (NullPointerException ex){
            toast("Нет изображения или \n данные введены неверно").show();
        }
    }
        Toast toast(String value){
            Toast toast = Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            return toast;
        }
    /**
     * Button для загрузки данных в базу данных
     * @param view параметр отвечающий за отображение
     */
    public void onLoadDbClick(View view) {
        try {
            dbhelper.insertLabel(strings[0],strings[5],strings[6],strings[1],strings[2],strings[3],strings[4],strings[7]);
            finish();
        }
        catch (NullPointerException ex){
            toast("Нет изображения или \n данные введены неверно").show();
        }
    }

    /**
     * закрытие Activity
     * @param view параметр отвечающий за отображение
     */
    public void openBooksClick(View view) {
        finish();
    }

}
