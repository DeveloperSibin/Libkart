package com.asiet.miniproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class StartupScreen extends AppCompatActivity{

    int flag = 0;
    String id, password, t1, t2, t3;
    CheckBox remindMe;
    EditText idText, passwordText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_screen);

        if(SaveSharedPreference.getUserId(StartupScreen.this).length() != 0) {
            Intent i = new Intent(StartupScreen.this, HomeScreen.class);
            i.putExtra("name", SaveSharedPreference.getUserName(this));
            i.putExtra("id", SaveSharedPreference.getUserId(this));
            startActivity(i);
        }

        remindMe = (CheckBox) findViewById(R.id.remindMe);
        idText = (EditText) findViewById(R.id.nameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);
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

    public static class SaveSharedPreference {
        static final String PREF_USER_NAME= "username";
        static final String PREF_USER_ID= "userid";

        static SharedPreferences getSharedPreferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public static void setUserName(Context ctx, String userName) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_USER_NAME, userName);
            editor.commit();
        }

        public static void setUserId(Context ctx, String userId) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_USER_ID, userId);
            editor.commit();
        }

        public static String getUserName(Context ctx) {
            return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
        }

        public static String getUserId(Context ctx) {
            return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
        }
    }

    public void Login(View view){

        id = idText.getText().toString();
        password = passwordText.getText().toString();

        BackGround b = new BackGround();
        b.execute(id,password);

    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String password = params[1];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/login.php");
                String urlParams = "id=" + id+ "&password=" + password;

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
                return "Exception: "/* + e.getMessage()*/;
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "/* + e.getMessage()*/;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err = null, type = "";

            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                if(user_data.isNull("id")){

                }else{
                    t1 = user_data.getString("id");
                    t2 = user_data.getString("name");
                    t3 = user_data.getString("password");
                    if(remindMe.isChecked()) {
                        SaveSharedPreference.setUserId(getApplicationContext(),t1);
                        SaveSharedPreference.setUserName(getApplicationContext(),t2);
                    }
                    Intent i = new Intent(StartupScreen.this, HomeScreen.class);
                    i.putExtra("name", t2);
                    i.putExtra("id", t1);
                    startActivity(i);
                }

            } catch (JSONException e) {
                flag++;
                e.printStackTrace();
                err = "Exception: "/* + e.getMessage()*/;
                Toast.makeText(getApplicationContext(),"Incorrect username or password!!!",Toast.LENGTH_SHORT).show();
                idText.setText("");
                passwordText.setText("");
                if(flag == 3){
                    loginButton.setEnabled(false);
                }
            }
        }
    }

}
