package com.asiet.miniproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MyBooks extends AppCompatActivity {

    String id, t1, t2, t3, t4, t5, t6, bname;
    TextView bookName, authorName, daysLeft, renewLeft, status;
    Button renewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        renewButton = (Button) findViewById(R.id.renewButton);
        bookName = (TextView) findViewById(R.id.bookName);
        authorName = (TextView) findViewById(R.id.authorName);
        daysLeft = (TextView) findViewById(R.id.daysLeft);
        renewLeft = (TextView) findViewById(R.id.renewLeft);
        status = (TextView) findViewById(R.id.status);

        Intent intent = getIntent();
        id = intent.getExtras().getString("userid");
        bname = intent.getExtras().getString("name");
        //Toast.makeText(getApplicationContext(),id+" "+bname,Toast.LENGTH_SHORT).show();
        MyBooks.First f = new MyBooks.First();
        f.execute(id,bname);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void Logout(View view){
        Intent i = new Intent(MyBooks.this,HomeScreen.class);
        i.putExtra("name",t1);
        i.putExtra("id",id);
        startActivity(i);
    }

    class First extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String name = params[1];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/mybooks.php");
                String urlParams = "id=" + id +"&name=" + name;

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
                t1 = user_data.getString("bookid");
                t2 = user_data.getString("days");
                t3 = user_data.getString("count");
                t6 = user_data.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

            /*if(!err.isEmpty()){

            }else{*/
                if(!t1.isEmpty())
                {
                    MyBooks.Second second = new MyBooks.Second();
                    second.execute(t1);
                }
            //}

        }
    }

    class Second extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String abc = params[0];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/mybooks2.php");
                String urlParams = "id=" + abc;

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
                t4 = user_data.getString("bookname");
                t5 = user_data.getString("author");

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }


            if(!t4.isEmpty()) {
                bookName.setText("Book: "+t4);
                authorName.setText("Author: "+t5);
                daysLeft.setText("Days left for return: "+t2);
                if(t6.equals("1")){
                    status.setText("Status: Reserved");
                } else {
                    status.setText("Status: Reservation pending");
                }

                int days = Integer.parseInt(t2);
                int fine = 5;
                if(days<0){
                    days = days * -1;
                    fine = fine * days;
                    t3 = Integer.toString(fine);
                    renewLeft.setText("Fine: "+t3);
                    daysLeft.setTextColor(getResources().getColor(R.color.red));
                    renewButton.setEnabled(false);
                    renewButton.setTextColor(getResources().getColor(R.color.red));
                    renewButton.setText("OVERDUE. NO RENEW AVAILABLE");
                }else {
                    renewLeft.setText("No. of renew left: "+t3);
                    if(t3.equals("0")){
                        renewButton.setEnabled(false);
                    }
                }
            }
        }
    }

    public void Renew(View view) {
        MyBooks.RenewBook renewBook = new MyBooks.RenewBook();
        if(t2.equals("0")||t2.equals("1")||t2.equals("2")){
            renewBook.execute(id,t1,t2,t3);
            Toast.makeText(getApplicationContext(),"Book succesfully renewed..!",Toast.LENGTH_SHORT).show();

            int days = Integer.parseInt(t2);
            days = days + 7;
            t2 = Integer.toString(days);
            daysLeft.setText("Days left for return: "+t2);

            int rleft = Integer.parseInt(t3);
            rleft = rleft - 1;
            t3 = Integer.toString(rleft);
            renewLeft.setText("No. of renew left: "+t3);
            if(t3.equals("0")){
                renewButton.setEnabled(false);
            }
        } else {
            Toast.makeText(getApplicationContext(),"Book cannot be renewed now..!",Toast.LENGTH_SHORT).show();
        }
    }

    class RenewBook extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String userid = params[0];
            String bookid = params[1];
            String days = params[2];
            String rcount = params[3];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/renew.php");
                String urlParams = "userid=" + userid + "&bookid=" + bookid + "&days=" + days + "&rcount=" + rcount;

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
    }
}
