package com.example.ebayautodisplay;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ebayautodisplayllibrary.EbayDisplayFragment;


public class SearchFragment extends Fragment {

    Button searchButton;
    CheckBox locationCheckBox;
    EditText keyWordEditText;
    EditText categoryIdEditText;
    ResultsFragment resultsFragment;
    int categoryId;
    String keyWord;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search,container,false);


        this.searchButton = view.findViewById(R.id.search_button);
        this.locationCheckBox= view.findViewById(R.id.location_check_box);
        this.keyWordEditText= view.findViewById(R.id.text_word_edit_text);
        this.categoryIdEditText= view.findViewById(R.id.category_edit_text);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord =  keyWordEditText.getText().toString().trim();
                String categoryIdString = categoryIdEditText.getText().toString().trim();

                if (TextUtils.isEmpty(keyWord)) {
                    keyWordEditText.setError("Please enter a Key Word");
                    return;
                }
                if (TextUtils.isEmpty(categoryIdString)){
                    categoryId = -1;
                }else{
                    categoryId = Integer.parseInt(categoryIdString);
                }
                resultsFragment = new ResultsFragment(keyWord,categoryId,locationCheckBox.isChecked());

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        resultsFragment).commit();
            }
        });
        return  view;

    }


}