package com.cmps121.ucscdining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class nutritionActivity extends AppCompatActivity {

    String URL = "";
    Elements links;

    String menu,serving,cal,cal_fat,total_fat,sat_fat,sat_fat_percent, tran_fat, chol, chol_percent
            ,sodium,sodium_percent, total_car,total_car_percent,fiber,fiber_percent,sugar,protein,vit_a,vit_b12,vit_c
            ,iron,ingredient, allergen;
    private final String TAG = "TAG";
    int level2 = 0;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        // pull extra
        // Take DH link and set that = URL
        // Use URL for next bit of code with "connect"
        Intent i = getIntent();
        int x = 0;
        final int position = i.getIntExtra("Food Item",x);
        final String link = i.getStringExtra("Nutrition Link");

        //AsyncTask Required to do HTML parsing
        new AsyncTask<Void, Void, ArrayList<String>>() {

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {

                ArrayList<String> aList = new ArrayList<String>();

                // Gets HTML from dining hall website and adds relevant information to an array list.
                try {

                    Document document = Jsoup.connect(link).get();

                    // Gets links from our document, which is used to go to another link for the nutritional info of the specified items
                    links = document.select("a[href]");
                    URL = "http://nutrition.sa.ucsc.edu/" + links.get(position).attr("href");

                    Document page = Jsoup.connect(URL).get();
                    Elements fonts = page.select("font");


                    menu = page.select("div").get(0).text();
                    serving = fonts.get(2).text();
                    cal = fonts.get(3).text();//page.select("b").get(1).text();
                    cal_fat = fonts.get(4).text();
                    total_fat = fonts.get(11).text();
                    sat_fat = fonts.get(18).text();
                    sat_fat_percent = fonts.get(57).text().substring(1);
                    tran_fat = fonts.get(26).text();
                    chol = fonts.get(32).text();
                    chol_percent = fonts.get(61).text().substring(1);
                    sodium = fonts.get(39).text();
                    sodium_percent = fonts.get(40).text() + "%";
                    total_car = fonts.get(14).text();
                    total_car_percent = fonts.get(15).text() + "%";
                    fiber = fonts.get(22).text();
                    fiber_percent = fonts.get(23).text() + "%";
                    sugar = fonts.get(29).text();
                    protein = fonts.get(36).text();
                    vit_a = fonts.get(67).text().substring(1);
                    vit_b12 = fonts.get(71).text().substring(1);
                    vit_c = fonts.get(69).text().substring(1);
                    iron = fonts.get(63).text().substring(1);
                    ingredient = page.select("span").get(1).text();
                    allergen = page.select("span").get(3).text();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (RuntimeException r){

                }
                return aList;
            }



            @Override
            protected void onPostExecute(ArrayList result) {

//                ListView list = findViewById(R.id.menuItems);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Nutrition2Activity.this, android.R.layout.simple_list_item_1, result);
//                list.setAdapter(adapter);
                TextView text = (TextView) findViewById(R.id.textView);
                TextView text2 = (TextView) findViewById(R.id.textView2);
                TextView text3 = (TextView) findViewById(R.id.textView3);
                TextView text4 = (TextView) findViewById(R.id.textView4);
                TextView text5 = (TextView) findViewById(R.id.textView5);
                TextView text6 = (TextView) findViewById(R.id.textView6);
                TextView text7 = (TextView) findViewById(R.id.textView7);
                TextView text8 = (TextView) findViewById(R.id.textView8);
                TextView text9 = (TextView) findViewById(R.id.textView9);
                TextView text10 = (TextView) findViewById(R.id.textView10);
                TextView text11 = (TextView) findViewById(R.id.textView11);
                TextView text12 = (TextView) findViewById(R.id.textView12);
                TextView text13 = (TextView) findViewById(R.id.textView13);
                TextView text14 = (TextView) findViewById(R.id.textView14);
                TextView text15 = (TextView) findViewById(R.id.textView15);
                TextView text16 = (TextView) findViewById(R.id.textView16);
                TextView text17 = (TextView) findViewById(R.id.textView17);
                TextView text18 = (TextView) findViewById(R.id.textView18);
                TextView text19 = (TextView) findViewById(R.id.textView19);
                TextView text20 = (TextView) findViewById(R.id.textView20);
                TextView text21 = (TextView) findViewById(R.id.textView21);
                TextView text22 = (TextView) findViewById(R.id.textView22);
                TextView text23 = (TextView) findViewById(R.id.textView23);
                TextView text24 = (TextView) findViewById(R.id.textView24);
                TextView text25 = (TextView) findViewById(R.id.textView25);
                TextView text26 = (TextView) findViewById(R.id.textView26);
                TextView text27 = (TextView) findViewById(R.id.textView27);
                TextView text28 = (TextView) findViewById(R.id.textView28);
                TextView text32 = (TextView) findViewById(R.id.textView32);
                TextView text34 = (TextView) findViewById(R.id.textView34);

//                text.setText(menu);
//                text7.setText(cal + "\t\t\t\t" + cal_fat);
//                text8.setText("Total Fat " + total_fat);
//                text9.setText("\tSaturated Fat " + sat_fat + "\t\t\t\t" + sat_fat_percent);
//                text10.setText("\tTrans Fat" + tran_fat);
//                text11.setText("Cholestoral " + chol + "\t\t\t\t\t" + chol_percent);
//                text12.setText("Sodium " + sodium + "\t\t\t\t\t\t" + sodium_percent);
//                text13.setText("Total Carb. " + total_car + "\t\t\t\t\t\t\t" + total_car_percent);
//                text14.setText("\tDietary Fiber " + fiber + "\t\t\t\t\t" + fiber_percent);
//                text15.setText("\tSugar" + sugar);
//                text16.setText("Protein" + protein);
//                text17.setText("Vitamin A-IU " + vit_a);
//                text18.setText("Vitamin B12 " + vit_b12);
//                text19.setText("Iron " + iron);
//                text20.setText("Vitamin B12 " + vit_b12);
//                text21.setText("INGREDIENTS: "+ ingredient);
//                text22.setText("ALLEGENS: " + allergen);
                text.setText(menu);
                text7.setText("Serving Size " + serving + " ea");
                text3.setText("Calories " + cal);
                text4.setText(cal_fat);
                text8.setText("Total Fat " + total_fat);
                text10.setText("     Saturated Fat " + sat_fat);
                text11.setText(sat_fat_percent);
                text12.setText("Cholesterol " + chol);
                text13.setText(chol_percent);
                text14.setText("     Trans Fat " + tran_fat);
                text15.setText("Sodium " + sodium);
                text16.setText(sodium_percent);
                text17.setText("Total Carbohydrate " + total_car);
                text18.setText(total_car_percent);
                text19.setText("     Dietary Fiber " + fiber);
                text20.setText(fiber_percent);
                text21.setText("     Sugars " + sugar);
                text22.setText("Protien " + protein);
                text23.setText("Vitamin A-IU " + vit_a);
                text24.setText("Vitamin B12 " + vit_b12);
                text25.setText("Vitamin C " + vit_c);
                text26.setText("Iron " + iron);
                text27.setText("Sodium " + sodium);
                text28.setText("Dietaty Fiber " + fiber_percent);
                text32.setText(ingredient);
                text34.setText(allergen);
//                menu,serving,cal,cal_fat,total_fat,sat_fat,sat_fat_percent, tran_fat, chol, chol_percent
//                        ,sodium,sodium_percent, total_car,total_car_percent,fiber,fiber_percent,sugar,protein,vit_a,vit_b12,vit_c
//                        ,iron,ingredient, allergen;

            }
        }.execute();

        final SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        Log.i(TAG, "Bad");
                        break;
                    case SmileRating.GOOD:
                        Log.i(TAG, "Good");
                        break;
                    case SmileRating.GREAT:
                        Log.i(TAG, "Great");
                        break;
                    case SmileRating.OKAY:
                        Log.i(TAG, "Okay");
                        break;
                    case SmileRating.TERRIBLE:
                        Log.i(TAG, "Terrible");
                        break;
                }
                level2 = smiley;
            }
        });
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
                Toast.makeText(nutritionActivity.this, "Selected Rating " + level, Toast.LENGTH_SHORT).show();
                level2 = level;
            }
        });
    }

    public void onResume(){
        super.onResume();

        final SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        if (level2 == 1){
            smileRating.setSelectedSmile(BaseRating.TERRIBLE);
        }
        else if (level2 == 2){
            smileRating.setSelectedSmile(BaseRating.BAD);
        }
        else if (level2 == 3){
            smileRating.setSelectedSmile(BaseRating.OKAY);
        }
        else if (level2 == 4){
            smileRating.setSelectedSmile(BaseRating.GOOD);
        }
        else if (level2 == 5){
            smileRating.setSelectedSmile(BaseRating.GREAT);
        }
        else
        {
            smileRating.setSelectedSmile(BaseRating.OKAY);
        }

    }


}
