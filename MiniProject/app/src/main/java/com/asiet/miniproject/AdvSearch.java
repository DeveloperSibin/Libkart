package com.asiet.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdvSearch extends AppCompatActivity {

    EditText idText, nameText, authorText;
    String id, name, author, x, count, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_search);
        Intent intent = getIntent();
        x = intent.getExtras().getString("id");
        count = intent.getExtras().getString("count");
        username = intent.getExtras().getString("username");

        idText = (EditText) findViewById(R.id.idText);
        nameText = (EditText) findViewById(R.id.nameText);
        authorText = (EditText) findViewById(R.id.authorText);
    }

    public void Logout(View view){
        Intent i = new Intent(AdvSearch.this,StartupScreen.class);
        startActivity(i);
    }

    public void Search(View view){
        id = idText.getText().toString();
        name = nameText.getText().toString();
        author = authorText.getText().toString();

        Intent i = new Intent(AdvSearch.this,AdvSearchView.class);
        i.putExtra("userid", x);
        i.putExtra("id", id);
        i.putExtra("name", name);
        i.putExtra("author", author);
        i.putExtra("count",count);
        i.putExtra("username",username);
        startActivity(i);
    }
}
