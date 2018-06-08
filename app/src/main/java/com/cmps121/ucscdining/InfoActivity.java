package com.cmps121.ucscdining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        setContentView(R.layout.info_activity);

        Intent j = getIntent();
        int x = 0;
        int dh = j.getIntExtra("DH", x);

        ImageView view = findViewById(R.id.imageView);
        actionBar.setSubtitle("Hours");

        switch (dh) {
            case (0):
                actionBar.setTitle("College Nine and Ten");
                view.setImageResource(R.drawable.nine_ten_hours);
                break;

            case (1):
                actionBar.setTitle("Cowell and Stevenson");
                view.setImageResource(R.drawable.cowell_hours);
                break;

            case(2):
                actionBar.setTitle("Crown and Merrill");
                view.setImageResource(R.drawable.merrill_hours);
                break;

            case(3):
                actionBar.setTitle("Porter and Kresge");
                view.setImageResource(R.drawable.porter_hours);
                break;

            case(4):
                actionBar.setTitle("Carson and Oakes");
                view.setImageResource(R.drawable.rcc_hours);
                break;

        }
    }
}
