package com.example.saladdresser;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PossibleDressingsFragment extends ListFragment {
	//MyCustomAdapter dataAdapter = null;
    public final static String EXTRA_RECIPE = "com.example.saladdresser.RECIPE";
    public final static String EXTRA_DRESSING_LIST = "com.example.saladdresser.DRESSING_LIST";

    OnDressingSelectedListener listener;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnDressingSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onDressingSelected(int position);
    }

    private String[] dressings;

    public PossibleDressingsFragment(){}

    public void setDressingsArray(String[] dressingArr) {
        this.dressings = dressingArr;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout = android.R.layout.simple_list_item_activated_1;
        // Create an array adapter for the list view
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, dressings));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            listener = (OnDressingSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        listener.onDressingSelected(position);
        
        // Set the item as checked to be highlighted when in two-pane layout
        l.setItemChecked(position, true);
    }
}

