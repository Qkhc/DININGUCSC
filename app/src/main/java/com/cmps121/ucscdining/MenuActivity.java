package com.cmps121.ucscdining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.nodes.Document;


import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuActivity  extends AppCompatActivity {

    String URL = "";
    String nutritionLink = "http://nutrition.sa.ucsc.edu/";
    Elements links;

    ActionBar actionBar;
    ArrayList<String> aList;
    List<String> breakfastMenu;
    List<String> lunchMenu;
    List<String> dinnerMenu;
    List<String> lateNightMenu;

    List selectedMenu = null;



    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        actionBar = getSupportActionBar();

        // pull extra
        // Take DH link and set that = URL
        // Use URL for next bit of code with "connect"
        Intent i = getIntent();
        int x = 0;
        final int DH = i.getIntExtra("DH", x);


        // Switch statement selects the appropriate dining hall menu to pull data from
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
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
                bottomNav.getMenu().removeItem(R.id.lateNight_navigation); // Removes late night button from nav bar
                break;

            case(3):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=25&locationName=Porter+Kresge+Dining+Hall&sName=&naFlag=" ;
                actionBar.setTitle("Porter and Kresge");
                bottomNav.getMenu().removeItem(R.id.lateNight_navigation); // Removes late night button from nav bar
                break;

            case(4):
                URL = "http://nutrition.sa.ucsc.edu/menuSamp.asp?locationNum=30&locationName=Rachel+Carson+Oakes+Dining+Hall&sName=&naFlag=";
                actionBar.setTitle("Carson and Oakes");
                break;

        }

        // This section sets the subtitle of the action bar to say "Menu for current date"
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

                    // Gets all links from URL allows us to get nutritional information
                    links = document.select("a[href]");


                    // While there is text in the "div" querey we add to our array list
                    while (document.select("div").hasText()) {

                        // If the specific item is NOT blank/empty add to our array list.
                        if (!(document.select("div").first().text().equals(""))) {

                            aList.add(document.select("div").first().text());

                        }
                        document.select("div").first().remove(); // Delete This item regardless
                    }


                    // Create a sublist for every section of the DH menu, i.e breakfast lunch and dinner
                    int temp = 0;
                    for(int i = 0; i < aList.size(); i++){

                        // If we get to lunch everything before that is breakfast
                        if(aList.get(i).equals("Lunch")){

                            breakfastMenu = aList.subList(2,i);
                            temp = i;

                        }
                        // if we get to Dinner everything before that is lunch
                        else if(aList.get(i).equals("Dinner")){

                            lunchMenu = aList.subList(temp + 1, i);
                            temp = i;

                        }
                        // If we get to Late night or the end then we have a dinner menu
                        else if((aList.get(i).equals("Late Night") || aList.get(i).contains("nutrient composition") && (DH == 3 || DH == 2))){

                            dinnerMenu = aList.subList(temp + 1, i);
                            temp = i;

                        }
                        // If we get to the end then we have a late night menu
                        else if(aList.get(i).contains("Powered by") && (DH == 0 || DH == 1 || DH == 4) ){

                            lateNightMenu = aList.subList(temp + 1, i-1);
                            temp = i;
                        }

                    }

                    nutritionLink = "http://nutrition.sa.ucsc.edu/" + (links.get(0)).attr("href");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Show breakfast menu by default.
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

                        // Switch statement for item clicked, displays correct menu
                        switch (item.getItemId()) {
                            case R.id.breakfast_navigation:
                                selectedMenu = breakfastMenu;
                                nutritionLink = "http://nutrition.sa.ucsc.edu/" + (links.get(0)).attr("href");
                                break;
                            case R.id.lunch_navigation:
                                selectedMenu = lunchMenu;
                                nutritionLink = "http://nutrition.sa.ucsc.edu/" + (links.get(1)).attr("href");
                                break;
                            case R.id.dinner_navigation:
                                selectedMenu = dinnerMenu;
                                nutritionLink = "http://nutrition.sa.ucsc.edu/" + (links.get(2)).attr("href");
                                break;
                            case R.id.lateNight_navigation:
                                selectedMenu = lateNightMenu;
                                nutritionLink = "http://nutrition.sa.ucsc.edu/" + (links.get(3)).attr("href");
                                break;
                        }

                        ListView list = findViewById(R.id.menuItems);
                        ListAdapter adapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, selectedMenu);
                        list.setAdapter(adapter);

                        return true;
                    }
                });



         ListView list = findViewById(R.id.menuItems);
         final Context context = this;
         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Create an Intent to reference our new activity, then call startActivity
                // to transition into the new Activity.
                Intent detailIntent = new Intent(context, nutritionActivity.class);

                detailIntent.putExtra("Nutrition Link", nutritionLink);
                detailIntent.putExtra("Food Item", position);
                System.out.println(nutritionLink);

                startActivity(detailIntent);
            }
        });

    }

    // This method will just show the menu item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        // the menu being referenced here is the menu.xml from res/menu/menu.xml
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // Here is the event handler for the menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.d(TAG, String.format("" + item.getItemId()));
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_favorite:
                /*the R.id.action_favorite is the ID of our button (defined in strings.xml).
                Change Activity here (if that's what you're intending to do, which is probably is).
                 */
                Intent i = new Intent(this, InfoActivity.class);
                Intent j = getIntent();
                int x = 0;
                int dh = j.getIntExtra("DH", x);
                i.putExtra("DH", dh);
                startActivity(i);
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}
