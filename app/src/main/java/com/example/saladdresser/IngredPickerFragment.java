package com.example.saladdresser;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.saladdresser.MainActivity.MyCustomAdapter;


public class IngredPickerFragment extends Fragment {
    public final static String EXTRA_RECIPE = "com.example.saladdresser.RECIPE";
	MyCustomAdapter dataAdapter;
    int mCurrentPosition = -1;
    ArrayList<Ingred> ingreds = null;
    
    private OnButtonPressedListener listener;
    
    public interface OnButtonPressedListener {
        public void onIngredsChoosen(ArrayList<Ingred> selectedIngreds);
    }
    public IngredPickerFragment(){}

    public void setIngredList(ArrayList ingredList){
        this.ingreds = ingredList;
    }

    public void setDataAdapter(MyCustomAdapter dataAdapter){
        this.dataAdapter = dataAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingred_picker, container, false);
    }
    
    @Override
    public void onStart() {
        super.onStart();
    	displayListView(ingreds);
    	checkButtonClick();
    }
    
    @Override
    public void onAttach(Activity activity) {
      super.onAttach(activity);
      if (activity instanceof OnButtonPressedListener) {
        listener = (OnButtonPressedListener) activity;
      } else {
        throw new ClassCastException(activity.toString()
            + " must implemenet IngredPickerFragment.OnButtonPressedListener");
      }
    }
    
    public void updateDetail(ArrayList<Ingred> selectedIngreds) {
      listener.onIngredsChoosen(selectedIngreds);
    }
    
	private void displayListView(ArrayList<Ingred> ingredList) {
		ListView listView = (ListView) getActivity().findViewById(R.id.listView1);
		// Assign adapter to ListView
    	FragmentActivity activity = getActivity();
    	listView.setAdapter(dataAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Ingred ingred = (Ingred) parent.getItemAtPosition(position);
				/*Toast.makeText(getActivity().getApplicationContext(),
						"Clicked on Row: " + ingred.getName(),
						Toast.LENGTH_LONG).show();*/
			}
		});

	}    
	
	private void checkButtonClick() {
		Button myButton = (Button) getActivity().findViewById(R.id.findSelected);
		myButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuffer responseText = new StringBuffer();
				String tempDressingID = "";
				ArrayList<Ingred> selectedIngreds = new ArrayList<Ingred>();
				String dressings = "";
				for(Ingred ingred: dataAdapter.getIngredList()) {
					if(ingred.isSelected()) {
						selectedIngreds.add(ingred);
						responseText.append(ingred.getName());
					}
				}
				//Toast.makeText(getActivity().getApplicationContext(),
				//				responseText, Toast.LENGTH_LONG).show();
				updateDetail(selectedIngreds);
			}
		});
	}
}
