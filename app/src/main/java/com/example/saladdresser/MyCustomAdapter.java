package com.example.saladdresser;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class MyCustomAdapter extends ArrayAdapter<Ingred> {
    Context context;
	private ArrayList<Ingred> ingredList;

	public MyCustomAdapter(Context context, int textViewResourceId, 
			ArrayList<Ingred> ingredList) {
		super(context, textViewResourceId, ingredList);
        this.context = context;
		this.ingredList = new ArrayList<Ingred>();
		this.ingredList.addAll(ingredList);
	}

	private class ViewHolder {
		TextView code;
		CheckBox name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Log.v("ConvertView", String.valueOf(position));

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.ingred_info, null);

			holder = new ViewHolder();
			holder.code = (TextView) convertView.findViewById(R.id.code);
			holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
			convertView.setTag(holder);

			holder.name.setOnClickListener( new View.OnClickListener() {  
				public void onClick(View v) {  
					CheckBox cb = (CheckBox) v ;  
					Ingred ingred = (Ingred) cb.getTag();  
					Toast.makeText(context.getApplicationContext(),
							"Clicked on Checkbox: " + cb.getText() +
							" is " + cb.isChecked(), 
							Toast.LENGTH_LONG).show();
					ingred.setSelected(cb.isChecked());
				}  
			});  
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		Ingred ingred = getIngredList().get(position);
		holder.code.setText(" (" +  ingred.getID() + ")");
		holder.name.setText(ingred.getName());
		holder.name.setChecked(ingred.isSelected());
		holder.name.setTag(ingred);

		return convertView;

	}

	public ArrayList<Ingred> getIngredList() {
		return ingredList;
	}

	public void setIngredList(ArrayList<Ingred> ingredList) {
		this.ingredList = ingredList;
	}

}

