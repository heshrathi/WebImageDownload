package com.example.webimagedownload;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    Button load, reset;

    protected class LoadImage extends AsyncTask <String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream in = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);

                return bitmap;

            } catch (MalformedURLException e) {

                e.printStackTrace();
                return null;

            } catch (Exception e) {

                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editView);
        imageView = findViewById(R.id.imageView);
        load = findViewById(R.id.loadBtn);
        reset = findViewById(R.id.resetBtn);

        String urlText = editText.getText().toString();


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = null;

                editText.setText("");
                imageView.setImageBitmap(image);
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadImage loadImage = new LoadImage();
                Bitmap image = null;

                try {
                    loadImage.execute("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTrDZRK-C1iTTrghgRElEqNQ0XZUJ7fxqLlTQ&usqp=CAU").get();
                    imageView.setImageBitmap(image);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}