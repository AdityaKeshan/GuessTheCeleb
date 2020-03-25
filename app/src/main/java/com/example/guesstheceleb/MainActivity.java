package com.example.guesstheceleb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button button;
    LinearLayout lin;
    RadioButton rab1,rab2,rab3,rab4;
    ArrayList<String> names=new ArrayList<String>();
    ArrayList<String> urls=new ArrayList<String>();
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
            Toast.makeText(this, "Incorrect!The correct answer is "+names.get(k), Toast.LENGTH_SHORT).show();
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
        String z=names.get(a);
        Bitmap o=getBitmapFromURL(urls.get(a));
        image.setImageBitmap(o);
        if(b==1)
        {
            rab1.setText(z);
            a=ob.nextInt(100);
            rab3.setText(names.get(a));
            a=ob.nextInt(100);
            rab2.setText(names.get(a));
            a=ob.nextInt(100);
            rab4.setText(names.get(a));

        }
        else if(b==2)
        {
            rab2.setText(z);
            a=ob.nextInt(100);
            rab3.setText(names.get(a));
            a=ob.nextInt(100);
            rab1.setText(names.get(a));
            a=ob.nextInt(100);
            rab4.setText(names.get(a));

        }
        else if(b==3)
        {
            rab3.setText(z);
            a=ob.nextInt(100);
            rab1.setText(names.get(a));
            a=ob.nextInt(100);
            rab2.setText(names.get(a));
            a=ob.nextInt(100);
            rab4.setText(names.get(a));

        }
        else if(b==4)
        {
            rab4 .setText(z);
            a=ob.nextInt(100);
            rab3.setText(names.get(a));
            a=ob.nextInt(100);
            rab2.setText(names.get(a));
            a=ob.nextInt(100);
            rab1.setText(names.get(a));

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
    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            URL url;
            HttpURLConnection urlConnection=null;
            try{
                String l="";
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int f=reader.read();
                while(f!=-1)
                {
                    char cur=(char)f;
                    l+=cur;
                    f=reader.read();}
                Log.i("The url gives:",l);

                return l;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            button = (Button) findViewById(R.id.button);
            lin = (LinearLayout) findViewById(R.id.lin);
            rab1 = (RadioButton) findViewById(R.id.rab1);
            rab2 = (RadioButton) findViewById(R.id.rab2);
            rab3 = (RadioButton) findViewById(R.id.rab3);
            rab4 = (RadioButton) findViewById(R.id.rab4);
            lin.setVisibility(View.INVISIBLE);
            image = (ImageView) findViewById(R.id.image);
            DownloadTask task = new DownloadTask();
            try {
                String result = task.execute("https://www.celebritynetworth.com/list/top-100-richest-people-in-the-world/").get();
                Log.i("The result is:",result);
                String spli[] = result.split("<div class=\"channels_nav\">");
                Pattern p = Pattern.compile("data-src=(.+?)>");
                Matcher m = p.matcher(spli[0]);
                int i = 0;
                while (m.find()) {
                    urls.add(m.group(1));
                    i++;
                }

                p = Pattern.compile("title=\"(.*?) Net Worth\"");
                m = p.matcher(spli[0]);
                i = 0;
                while (m.find()) {
                    names.add( m.group(1));
                    i++;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            Log.i("The e:",e.getMessage());
        }

    }
}
