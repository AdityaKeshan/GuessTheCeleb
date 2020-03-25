package com.example.guesstheceleb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button button;
    LinearLayout lin;
    RadioButton rab1,rab2,rab3,rab4;
    String[] names;
    String[] urls;
    ImageView image;
    int k;
    public void check(View v)
    {
        String tag= (String) v.getTag();
        if(tag.equals(Integer.toString(k)))
        {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Incorrect!The correct answer is "+names[k], Toast.LENGTH_SHORT).show();
        }
        generateques();
    }
    public void start(View v)
    {
        button.setVisibility(View.INVISIBLE);
        lin.setVisibility(View.VISIBLE);
        generateques();

    }
    public void generateques()
    {
        Random ob=new Random();
        int a=ob.nextInt(100);
        int b=ob.nextInt(4)+1;
        k=b;
        String z=names[a];
        Bitmap o=getBitmapFromURL(urls[a]);
        image.setImageBitmap(o);
        if(b==1)
        {
            rab1.setText(z);
            a=ob.nextInt(100);
            rab3.setText(names[a]);
            a=ob.nextInt(100);
            rab2.setText(names[a]);
            a=ob.nextInt(100);
            rab4.setText(names[a]);

        }
        else if(b==2)
        {
            rab2.setText(z);
            a=ob.nextInt(100);
            rab3.setText(names[a]);
            a=ob.nextInt(100);
            rab1.setText(names[a]);
            a=ob.nextInt(100);
            rab4.setText(names[a]);

        }
        else if(b==3)
        {
            rab3.setText(z);
            a=ob.nextInt(100);
            rab1.setText(names[a]);
            a=ob.nextInt(100);
            rab2.setText(names[a]);
            a=ob.nextInt(100);
            rab4.setText(names[a]);

        }
        else if(b==4)
        {
            rab4 .setText(z);
            a=ob.nextInt(100);
            rab3.setText(names[a]);
            a=ob.nextInt(100);
            rab2.setText(names[a]);
            a=ob.nextInt(100);
            rab1.setText(names[a]);

        }

    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public class DownloadTask extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... urls)
        {
            
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names= new String[100];
        urls=new String[100];
        button=(Button)findViewById(R.id.button);
        lin=(LinearLayout)findViewById(R.id.lin);
        rab1=(RadioButton)findViewById(R.id.rab1);
        rab2=(RadioButton)findViewById(R.id.rab2);
        rab3=(RadioButton)findViewById(R.id.rab3);
        rab4=(RadioButton)findViewById(R.id.rab4);
        lin.setVisibility(View.INVISIBLE);
        image=(ImageView)findViewById(R.id.image);
        DownloadTask task=new DownloadTask();
        task.execute("https://www.posh24.se/kandisar/");

    }
}
