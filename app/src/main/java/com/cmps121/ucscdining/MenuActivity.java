package com.cmps121.ucscdining;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

public class MenuActivity  extends AppCompatActivity {

    ArrayList<String> aList = new ArrayList<String>();
    private final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);




        new  AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String title = "";
                try {
                    Document document = Jsoup.connect("http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=20&locationName=Crown+Merrill+Dining+Hall&sName=&naFlag=").get();
                    while(document.select("div").hasText()){

                        if(!(document.select("div").first().text().equals("")))
                            aList.add(document.select("div").first().text());
                        document.select("div").first().remove();
                    }




                } catch (IOException e) {
                    e.printStackTrace();
                }
                title = aList.get(5);
                return title;
            }

            @Override
            protected void onPostExecute(String result) {

                TextView text = (TextView)findViewById(R.id.textView);
                text.setText(result);
            }
        }.execute();
    }
}
