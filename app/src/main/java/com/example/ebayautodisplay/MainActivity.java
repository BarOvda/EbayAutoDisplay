package com.example.ebayautodisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.location.Location;
import android.os.Bundle;
import com.example.ebayautodisplayllibrary.EbayDisplayFragment;

public class MainActivity extends AppCompatActivity {
    ResultsFragment resultsFragment;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        resultsFragment = new ResultsFragment();

        searchFragment = new SearchFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                searchFragment).commit();
    }
    @Override
    public void onBackPressed(){

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                searchFragment).commit();
    }
}