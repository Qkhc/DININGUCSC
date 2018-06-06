package com.cmps121.ucscdining;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.nodes.Document;


import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuActivity  extends AppCompatActivity {

    String URL = "";

    private final String TAG = "TAG";
    ActionBar actionBar;
    ArrayList<String> aList;
    List<String> breakfastMenu;
    List<String> lunchMenu;
    List<String> dinnerMenu;
    List<String> lateNightMenu;



    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        actionBar = getSupportActionBar();
        actionBar.setTitle("");


        // pull extra
        // Take DH link and set that = URL
        // Use URL for next bit of code with "connect"
        Intent i = getIntent();
        int x = 0;
        final int DH = i.getIntExtra("DH", x);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        View item =  findViewById(R.id.lateNight_navigation);

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
                bottomNav.getMenu().removeItem(R.id.lateNight_navigation);
                break;

            case(3):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=25&locationName=Porter+Kresge+Dining+Hall&sName=&naFlag=" ;
                actionBar.setTitle("Porter and Kresge");
                bottomNav.getMenu().removeItem(R.id.lateNight_navigation);
                break;

            case(4):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=30&locationName=Rachel+Carson+Oakes+Dining+Hall&sName=&naFlag=";
                actionBar.setTitle("Carson and Oakes");
                break;

        }

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
        String formattedDate = df.format(c);

        actionBar.setSubtitle("Menu for " + formattedDate);








        //AsyncTask Required to do HTML parsing
         new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {

                aList = new ArrayList<String>();

                // Gets HTML from dining hall website and adds relevant information to an array list.
                try {

                    Document document = Jsoup.connect(URL).get();

                    // While there is text in the "div" querey we add to our array list
                    while (document.select("div").hasText()) {

                        // If the specific item is NOT blank/empty add to our array list.
                        if (!(document.select("div").first().text().equals(""))) {

                            aList.add(document.select("div").first().text());

                        }
                        document.select("div").first().remove(); // Delete This item regardless
                    }

                    int temp = 0;
                    for(int i = 0; i < aList.size(); i++){

                        if(aList.get(i).equals("Lunch")){

                            breakfastMenu = aList.subList(2,i);
                            temp = i;

                        }
                        else if(aList.get(i).equals("Dinner")){

                            lunchMenu = aList.subList(temp + 1, i);
                            temp = i;

                        }
                        else if((aList.get(i).equals("Late Night") || aList.get(i).contains("nutrient composition") && (DH == 3 || DH == 2))){

                            dinnerMenu = aList.subList(temp + 1, i);
                            temp = i;

                        }
                        else if(aList.get(i).contains("Powered by") && (DH == 0 || DH == 1 || DH == 4) ){

                            lateNightMenu = aList.subList(temp + 1, i-1);
                            temp = i;
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return breakfastMenu;
            }



            @Override
            protected void onPostExecute(List result) {

                ListView list = findViewById(R.id.menuItems);
                ListAdapter adapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, result);
                list.setAdapter(adapter);

            }
         }.execute();


          //On click listener for bottom navigation bar, used to switch between different meals
         BottomNavigationView botNav = findViewById(R.id.navigation);

         botNav.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        List selectedMenu = null;
                        switch (item.getItemId()) {
                            case R.id.breakfast_navigation:
                                selectedMenu = breakfastMenu;
                                break;
                            case R.id.lunch_navigation:
                                selectedMenu = lunchMenu;
                                break;
                            case R.id.dinner_navigation:
                                selectedMenu = dinnerMenu;
                                break;
                            case R.id.lateNight_navigation:
                                selectedMenu = lateNightMenu;
                                break;
                        }

                        ListView list = findViewById(R.id.menuItems);
                        ListAdapter adapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, selectedMenu);
                        list.setAdapter(adapter);

                        return true;
                    }
                });
    }
}
