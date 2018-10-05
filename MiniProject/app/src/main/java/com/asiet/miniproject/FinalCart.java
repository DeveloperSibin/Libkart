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

public class FinalCart extends AppCompatActivity {

    String t1, t2, t3, t4, x, bcount, username;
    Button holdButton;
    TextView bookName, authorName, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_cart);

        holdButton = (Button) findViewById(R.id.reserveButton);
        bookName = (TextView) findViewById(R.id.bookName);
        authorName = (TextView) findViewById(R.id.authorName);
        quantity = (TextView) findViewById(R.id.quantity);

        Intent intent = getIntent();
        x = intent.getExtras().getString("userid");
        String name = intent.getExtras().getString("name");
        bcount = intent.getExtras().getString("count");
        username = intent.getExtras().getString("username");

        FinalCart.BackGround b = new FinalCart.BackGround();
        b.execute(name);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(FinalCart.this,MyCart.class);
        i.putExtra("name",username);
        i.putExtra("id",x);
        i.putExtra("count",bcount);
        startActivity(i);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/search.php");
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
                t1 = user_data.getString("bookid");
                t2 = user_data.getString("bookname");
                t3 = user_data.getString("author");
                t4 = user_data.getString("qty");


            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }

            if(!t1.isEmpty())
            {
                //Toast.makeText(getApplicationContext(),t1+","+t2+","+t3+"",Toast.LENGTH_SHORT).show();
                bookName.setText("BookName: "+t2);
                authorName.setText("Author: "+t3);
                //Toast.makeText(getApplicationContext(),bcount,Toast.LENGTH_SHORT).show();
                if(t4.equals("0")){
                    holdButton.setEnabled(false);
                    quantity.setText("Books Left: "+t4+" (Out Of Stock)");
                } else {
                    quantity.setText("Books Left: "+t4+" (In Stock)");
                }
            }
        }
    }

    public void RCart(View view) {
        FinalCart.RemCart r = new FinalCart.RemCart();
        r.execute(x,t1);
        Toast.makeText(getApplicationContext(),"Book Has Been Removed From Cart..!",Toast.LENGTH_SHORT).show();
        //Go back
    }

    class RemCart extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String userid = params[0];
            String bookid = params[1];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/removecart.php");
                String urlParams = "userid=" + userid + "&bookid=" + bookid;

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

    public void Reserve(View view) {
        if(bcount.equals("3")){
            Toast.makeText(getApplicationContext(),"You have already place hold 3 books.",Toast.LENGTH_SHORT).show();
        }else {
            FinalCart.PlaceHold b = new FinalCart.PlaceHold();
            b.execute(x,t1,t2,t4);
            int bc = Integer.parseInt(t4);
            bc--;
            t4 = Integer.toString(bc);
            if(bc==0){
                holdButton.setEnabled(false);
                quantity.setText("Books Left: "+t4+" (Out Of Stock)");
            } else {
                quantity.setText("Books Left: "+t4+" (In Stock)");
            }
            Toast.makeText(getApplicationContext(),"Place Hold Succesfull..!",Toast.LENGTH_SHORT).show();
            int ac = Integer.parseInt(bcount);
            ac++;
            bcount = Integer.toString(ac);
            //Toast.makeText(getApplicationContext(),bcount,Toast.LENGTH_SHORT).show();
        }

        //Go back
    }

    class PlaceHold extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String userid = params[0];
            String bookid = params[1];
            String bookname = params[2];
            String qty = params[3];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/placehold.php");
                String urlParams = "userid=" + userid + "&bookid=" + bookid + "&bookname=" + bookname + "&qty=" + qty;

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

    public void ToCart(View view) {
        FinalCart.AddToCart b = new FinalCart.AddToCart();
        b.execute(x,t1,t2);
        Toast.makeText(getApplicationContext(),"Book Has Been Added To Cart..!",Toast.LENGTH_SHORT).show();
        //Go back
    }

    class AddToCart extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String userid = params[0];
            String bookid = params[1];
            String bookname = params[2];
            String data = "";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.95/library/cart.php");
                String urlParams = "userid=" + userid + "&bookid=" + bookid + "&bookname=" + bookname;

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
