package com.example.ebayautodisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import com.example.ebayautodisplayllibrary.EbayDisplayFragment;

public class MainActivity extends AppCompatActivity {
    public final static String EBAY_APP_ID = "BarOvda-Games4Yo-PRD-9c8ec878c-ae291bb6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Location mLocation = new Location("");
        mLocation.setLatitude(33.665354);
        mLocation.setLongitude(-117.199018);
        EbayDisplayFragment ebayDisplayFragment = findViewById(R.id.ebay_element);
        ebayDisplayFragment.initEbayAutoDisplay(EBAY_APP_ID,"Apple iPhone 10",9355);
    }
}