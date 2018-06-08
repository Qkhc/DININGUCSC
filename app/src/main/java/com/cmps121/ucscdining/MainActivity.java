package com.cmps121.ucscdining;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] diningHalls = {"Nine and Ten", "Cowell/Stevenson", "Crown/Merrill", "Porter/Kresge Dining Hall", "Rachel Carson/Oakes Dining Hall"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET},
                99);
    }

    protected void onResume(){
        super.onResume();

        ListView list = findViewById(R.id.listOfDH);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, diningHalls);
        list.setAdapter(adapter);


        // Set an OnItemClickListener for each of the list items
        final Context context = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Create an Intent to reference our new activity, then call startActivity
                // to transition into the new Activity.
                Intent detailIntent = new Intent(context, MenuActivity.class);

                detailIntent.putExtra("DH", position);
                startActivity(detailIntent);
            }
        });






    }

}
