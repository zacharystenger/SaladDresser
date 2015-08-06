package com.example.saladdresser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayRecipeFragment extends Fragment {
    public final static String EXTRA_RECIPE = "com.example.saladdresser.RECIPE";
 
    private String recipe;

    public DisplayRecipeFragment(){}

    public void setRecipe(String recipe) {
    	this.recipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
	    // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.dressings, container, false);
        View tv = v.findViewById(R.id.dressing);
        ((TextView)tv).setText(recipe);
        return v;
    }
}

