package com.asiet.miniproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MyProfile extends AppCompatActivity {

    String t1, t2, t3, t4, t5, t6;
    TextView name, id, branch, semester, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name = (TextView) findViewById(R.id.name);
        id = (TextView) findViewById(R.id.id);
        branch = (TextView) findViewById(R.id.branch);
        semester = (TextView) findViewById(R.id.semester);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        Intent intent = getIntent();
        t1 = intent.getExtras().getString("name");
        t2 = intent.getExtras().getString("id");
        t3 = intent.getExtras().getString("branch");
        t4 = intent.getExtras().getString("semester");
        t5 = intent.getExtras().getString("email");
        t6 = intent.getExtras().getString("phone");

        name.setText(t1);
        id.setText(t2);
        branch.setText(t3);
        semester.setText(t4);
        email.setText(t5);
        phone.setText(t6);

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(MyProfile.this,HomeScreen.class);
        i.putExtra("name",t1);
        i.putExtra("id",t2);
        startActivity(i);
    }

}
