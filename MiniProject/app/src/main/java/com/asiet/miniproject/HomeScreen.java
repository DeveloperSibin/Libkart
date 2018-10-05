package com.asiet.miniproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeScreen extends AppCompatActivity {

    int flag = 0;
    String name, t1, t2, t3, t4, t5, t6, x, id;
    public String count="";
    EditText nameText;
    TextView welcomeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        x = intent.getExtras().getString("name");
        id = intent.getExtras().getString("id");
        welcomeView = (TextView) findViewById(R.id.welcomeView);
        welcomeView.setText("Welcome "+x);
        nameText = (EditText) findViewById(R.id.searchText);
        HomeScreen.Count c = new HomeScreen.Count();
        c.execute(id);
    }

    public void Logout(View view){
        StartupScreen.SaveSharedPreference.setUserName(getApplicationContext(),"");
        StartupScreen.SaveSharedPreference.setUserId(getApplicationContext(),"");
        Intent i = new Intent(HomeScreen.this,StartupScreen.class);
        startActivity(i);
    }

    public void Search(View view){
        name = nameText.getText().toString();

        Intent i = new Intent(HomeScreen.this,SearchView.class);
        i.putExtra("userid",id);
        i.putExtra("name", name);
        i.putExtra("count", count);
        i.putExtra("username",x);
        startActivity(i);
    }

     public void MyBooks(View view){
         if(count.equals("0")){
             Toast.makeText(getApplicationContext(),"No Books Reserved!!!",Toast.LENGTH_SHORT).show();
         }
        Intent i = new Intent(HomeScreen.this,MyBookView.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    public void AdvSearch(View view){
        Intent i = new Intent(HomeScreen.this,AdvSearch.class);
        i.putExtra("id", id);
        i.putExtra("count", count);
        i.putExtra("username",x);
        startActivity(i);
    }

    public void Cart(View view){
        Intent i = new Intent(HomeScreen.this,MyCart.class);
        i.putExtra("name", x);
        i.putExtra("id", id);
        i.putExtra("count", count);
        startActivity(i);
    }

    public void Profile(View view){
        HomeScreen.BackGround b = new HomeScreen.BackGround();
        b.execute(x);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/myprofile.php");
                String urlParams = "name=" + name;

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
                t1 = user_data.getString("name");
                t2 = user_data.getString("id");
                t3 = user_data.getString("branch");
                t4 = user_data.getString("semester");
                t5 = user_data.getString("email");
                t6 = user_data.getString("phone");

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

            if(!t1.isEmpty())
            {
                Intent i = new Intent(HomeScreen.this,MyProfile.class);
                i.putExtra("name", t1);
                i.putExtra("id", t2);
                i.putExtra("branch", t3);
                i.putExtra("semester", t4);
                i.putExtra("email", t5);
                i.putExtra("phone", t6);
                startActivity(i);
            }
        }
    }

    class Count extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String userid = params[0];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/count.php");
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
                JSONObject user_data = root.getJSONObject("user_data");
                count = user_data.getString("count");
                //Toast.makeText(getApplicationContext(),count,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
