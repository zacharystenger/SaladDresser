package com.example.saladdresser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;


public class MainActivity extends ActionBarActivity 
implements IngredPickerFragment.OnButtonPressedListener
, PossibleDressingsFragment.OnDressingSelectedListener{

	public final static String EXTRA_RECIPE = "com.example.saladdresser.RECIPE";

    private final static int INGRED_LIST_PAGE_NUM = 0;
    private final static int DRESSING_LIST_PAGE_NUM = 1;
    private final static int RECIPE_PAGE_NUM = 2;

	MyCustomAdapter dataAdapter = null;
	// Database Helper
	DatabaseHandler dbHandler;
	SQLiteDatabase db;
	ArrayList<Ingred> ingredList;
	String[] recipes;


	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 */
	private ViewPager myViewPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private MyFragmentAdapter myFragmentPagerAdapter;
	private List<Fragment> arrFragment ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


        // if we're being restored from a previous state,
        // then we don't need to do anything and should return
        if (savedInstanceState != null) {
            return;
        }

		dbHandler = new DatabaseHandler(getApplicationContext());
		//TODO, this currently re-builds the DB everytime and it shouldn't
		db = dbHandler.getWritableDatabase();
		dbHandler.onUpgrade(db,1,2);
        dbHandler.createAllDressings();

		ingredList = new ArrayList<Ingred>(dbHandler.getAllIngreds());
		dataAdapter = new MyCustomAdapter(getApplicationContext(),
				R.layout.ingred_info, ingredList);


		// Create a new Fragment to be placed in the activity layout
		IngredPickerFragment ingredFragment = new IngredPickerFragment();
        ingredFragment.setIngredList(new ArrayList(dbHandler.getAllIngreds()));
        ingredFragment.setDataAdapter(dataAdapter);

		arrFragment = new ArrayList<Fragment>();
		arrFragment.add(ingredFragment);

		//now we create an adapter between our Fragments and the ViewPager
		myFragmentPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager(),ingredFragment);
		myViewPager = (ViewPager) findViewById(R.id.pager);
		myViewPager.setAdapter(myFragmentPagerAdapter);
		myViewPager.setCurrentItem(INGRED_LIST_PAGE_NUM);
	}

	@Override
	public void onBackPressed() {
		if (myViewPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			myViewPager.setCurrentItem(myViewPager.getCurrentItem() - 1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		return true;
	}
	@Override
	public void onIngredsChoosen(ArrayList<Ingred> selectedIngreds) {
		StringBuffer responseText = new StringBuffer();
		List<Dressing> dressingList = dbHandler.getDressingsFromIngredList(selectedIngreds);
		String[] dressingArr = new String[dressingList.size()];
		recipes = new String[dressingList.size()];
		int i = 0;
		for(Dressing dressing: dressingList) {
			dressingArr[i] = dressing.getName();
			responseText.append("Dressing: ");
			responseText.append(dressing.getName() + ": \nIngredients:\n");

			for(String[] ingred: dressing.getIngreds()) {
				if(isInteger(ingred[1])) {
					responseText.append(ingred[1] + "part");
                    if(Integer.parseInt(ingred[1]) > 1) {
                        responseText.append("s");
                    }
					responseText.append(" "+ingred[0] + "\n");
				}
				else{
					responseText.append(ingred[0] + " " + ingred[1] + "\n");
				}
			}
			responseText.append(dressing.getInfo() + "\n");
			recipes[i] = responseText.toString();
			responseText.delete(0,responseText.length());
			i++;
		}
		// Create fragment and give it an argument for the selected article
		Bundle args = new Bundle();
		args.putStringArray(PossibleDressingsFragment.EXTRA_DRESSING_LIST, dressingArr);
		myFragmentPagerAdapter.setDressings(dressingArr);
		myFragmentPagerAdapter.notifyDataSetChanged();
		myViewPager.setCurrentItem(DRESSING_LIST_PAGE_NUM);
	}

	@Override
	public void onDressingSelected(int position) {
		Bundle args = new Bundle();
		args.putString(DisplayRecipeFragment.EXTRA_RECIPE, recipes[position]);
		myFragmentPagerAdapter.setRecipe(recipes[position]);
		myFragmentPagerAdapter.notifyDataSetChanged();
		myViewPager.setCurrentItem(RECIPE_PAGE_NUM);
	}

	public class MyCustomAdapter extends ArrayAdapter<Ingred> {
		private ArrayList<Ingred> ingredList;

		public MyCustomAdapter(Context context, int textViewResourceId, 
				ArrayList<Ingred> ingredList) {
			super(context, textViewResourceId, ingredList);
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
				LayoutInflater vi = (LayoutInflater)getSystemService(
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
						Toast.makeText(getApplicationContext(),
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

    //TODO, handle swipes when dressing list & recipe are blank
	public class MyFragmentAdapter extends FragmentStatePagerAdapter {
		private static final int NUM_FRAGMENTS = 3;
		private List<Fragment> mFragments;
		private Fragment ingredFrag;
		private String[] dressings;
		private String recipe = "";
		
		public MyFragmentAdapter(FragmentManager fm, Fragment ingredFrag) {
			super(fm);
			this.ingredFrag = ingredFrag;
			this.dressings = new String[3];
			this.dressings[0] = "";
			this.dressings[1] = "";
			this.dressings[2] = "";
		}

		@Override
		public Fragment getItem(int i) {
			if(i == INGRED_LIST_PAGE_NUM) {
				return ingredFrag;
			}
			else if(i == DRESSING_LIST_PAGE_NUM) {
                PossibleDressingsFragment dressingsFrag = new PossibleDressingsFragment();
                dressingsFrag.setDressingsArray(this.dressings);
				return dressingsFrag;
			}
			else if( i == RECIPE_PAGE_NUM) {
                DisplayRecipeFragment recipeFrag = new DisplayRecipeFragment();
                recipeFrag.setRecipe(this.recipe);
				return recipeFrag;
			}
			else{
				return null;//TODO
			}
		}
		
		@Override
		public int getItemPosition(Object item) {
			return POSITION_NONE;
		}
		
		@Override
		public int getCount() {
			return NUM_FRAGMENTS;
		}

		public void setDressings(String[] dressings) {
			this.dressings = dressings;
		}

		public void setRecipe(String recipe) {
			this.recipe = recipe;
		}
	}
}
