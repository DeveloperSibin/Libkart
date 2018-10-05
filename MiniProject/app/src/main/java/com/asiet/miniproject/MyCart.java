package com.asiet.miniproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    String x, count, a;
    int i;
    ArrayAdapter adapter;
    ArrayList<String> bookArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        Intent intent = getIntent();
        a = intent.getExtras().getString("name");
        x = intent.getExtras().getString("id");
        count = intent.getExtras().getString("count");
        final ListView listView = (ListView) findViewById(R.id.listView);
        MyCart.BackGround b = new MyCart.BackGround();
        b.execute(x);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,bookArray);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String bookName = listView.getItemAtPosition(i).toString();
                //Toast.makeText(getApplicationContext(),bookName,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyCart.this,FinalCart.class);
                intent.putExtra("userid",x);
                intent.putExtra("name",bookName);
                intent.putExtra("count",count);
                intent.putExtra("username",a);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(MyCart.this,HomeScreen.class);
        i.putExtra("name",a);
        i.putExtra("id",x);
        startActivity(i);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String userid = params[0];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/cartview.php");
                String urlParams = "userid=" + userid;

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
                JSONArray user_data = root.getJSONArray("user_data");
                for (i = 0; i < user_data.length(); i++) {
                    bookArray.add(user_data.getJSONObject(i).getString("bookname"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
        }
    }

    public void Back(View view){
        Intent i = new Intent(MyCart.this,HomeScreen.class);
        i.putExtra("name",a);
        i.putExtra("id",x);
        startActivity(i);
    }
}
