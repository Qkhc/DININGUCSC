package com.cmps121.ucscdining;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.nodes.Document;


import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

public class MenuActivity  extends AppCompatActivity {

    String URL = "";

    private final String TAG = "TAG";


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");


        // pull extra
        // Take DH link and set that = URL
        // Use URL for next bit of code with "connect"
        Intent i = getIntent();
        int x = 0;
        int DH = i.getIntExtra("DH", x);

        switch (DH) {
            case (0):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=40&locationName=Colleges+Nine+%26+Ten+Dining+Hall&sName=&naFlag=";
                actionBar.setTitle("College Nine and Ten");
                break;

            case (1):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=05&locationName=Cowell+Stevenson+Dining+Hall&sName=&naFlag=";
                actionBar.setTitle("Cowell and Stevenson");
                break;

            case(2):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=20&locationName=Crown+Merrill+Dining+Hall&sName=&naFlag=";
                actionBar.setTitle("Crown and Merrill");
                break;

            case(3):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=25&locationName=Porter+Kresge+Dining+Hall&sName=&naFlag=" ;
                actionBar.setTitle("Porter and Kresge");
                break;

            case(4):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=30&locationName=Rachel+Carson+Oakes+Dining+Hall&sName=&naFlag=";
                actionBar.setTitle("Carson and Oakes");
                break;

        }






        //AsyncTask Required to do HTML parsing
         new AsyncTask<Void, Void, ArrayList<String>>() {

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {

                ArrayList<String> aList = new ArrayList<String>();
                String title = "";

                // Gets HTML from dining hall website and adds relevant information to an array list.
                try {

                    Document document = Jsoup.connect(URL).get();

                    // While there is text in the "div" querey we add to our array list
                    while (document.select("div").hasText()) {

                        // If the specific item is NOT blank/empty add to our array list.
                        if (!(document.select("div").first().text().equals("")))
                            aList.add(document.select("div").first().text());
                        document.select("div").first().remove(); // Delete This item regardless
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return aList;
            }

            @Override
            protected void onPostExecute(ArrayList result) {

                ListView list = findViewById(R.id.menuItems);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, result);
                list.setAdapter(adapter);
            }
        }.execute();
    }
}
