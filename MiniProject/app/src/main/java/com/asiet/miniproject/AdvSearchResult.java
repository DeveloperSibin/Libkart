package com.asiet.miniproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AdvSearchResult extends AppCompatActivity {

    String id, name, author, t1, t2, t3;
    TextView bookName, authorName, quantity;
    Button holdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search_result);

        holdButton = (Button) findViewById(R.id.reserveButton);
        bookName = (TextView) findViewById(R.id.bookName);
        authorName = (TextView) findViewById(R.id.authorName);
        quantity = (TextView) findViewById(R.id.quantity);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");
        author = intent.getExtras().getString("author");
        AdvSearchResult.BackGround b = new AdvSearchResult.BackGround();
        b.execute(id,name,author);

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String name = params[1];
            String author = params[2];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/advsearch.php");
                String urlParams = "id=" + id + "&name=" + name + "&author=" + author;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null, type = "";
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                t1 = user_data.getString("bookname");
                t2 = user_data.getString("author");
                t3 = user_data.getString("qty");


            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

            if(!t1.isEmpty())
            {
                bookName.setText("BookName: "+t1);
                authorName.setText("Author: "+t2);
                if(t3.equals("0")){
                    holdButton.setEnabled(false);
                    quantity.setText("Quantity: "+t3+" (Out Of Stock)");
                } else {
                    quantity.setText("Quantity: "+t3+" (In Stock)");
                }
            }
        }
    }

    public  void Reserve(View view){

    }

}
