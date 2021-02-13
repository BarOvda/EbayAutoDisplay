package com.example.ebayautodisplay;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ebayautodisplayllibrary.EbayDisplayFragment;


public class ResultsFragment extends Fragment {
    public final static String EBAY_APP_ID = "BarOvda-Games4Yo-PRD-9c8ec878c-ae291bb6";
    String keyWord;
    int categoryId;
    boolean useLocation;
    public ResultsFragment(){};
    public ResultsFragment( String keyWord, int categoryId, boolean useLocation ){
        this.keyWord = keyWord;
        this.categoryId = categoryId;
        this.useLocation = useLocation;
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_results,container,false);

        Location mLocation = new Location("");
        //Example location - USA California
        mLocation.setLatitude(33.665354);
        mLocation.setLongitude(-117.199018);

        EbayDisplayFragment ebayDisplayFragment = view.findViewById(R.id.ebay_element);
        if(useLocation&&categoryId!=-1) {
            ebayDisplayFragment.initEbayAutoDisplay(EBAY_APP_ID, keyWord, categoryId, mLocation);
        }else if(categoryId!=-1){
            ebayDisplayFragment.initEbayAutoDisplay(EBAY_APP_ID, keyWord, categoryId);
        }else if(useLocation){
            ebayDisplayFragment.initEbayAutoDisplay(EBAY_APP_ID, keyWord,mLocation);
        }else{
            ebayDisplayFragment.initEbayAutoDisplay(EBAY_APP_ID, keyWord);
        }

        return  view;

    }
}