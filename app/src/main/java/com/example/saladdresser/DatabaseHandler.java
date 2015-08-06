package com.example.saladdresser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "dressingManager";

	// table names
	private static final String TABLE_DRESSING = "dressing";
	private static final String TABLE_INGRED = "ingred";
	private static final String TABLE_XREF = "dressingredxref";

	// Dressings Table Columns names
	private static final String DRESSING_ID = "id";
	private static final String DRESSING_NAME = "name";
	private static final String DRESSING_INFO = "info";

	// Ingredients Table Columns names
	private static final String INGRED_ID = "id";
	private static final String INGRED_NAME = "name";

	// XREF Table Coumns names
	private static final String XREF_INGRED_ID = "ingred_id";
	private static final String XREF_DRESS_ID = "dressing_id";
	private static final String XREF_PARTS = "parts";
	private static final String XREF_OPTIONAL = "ingred_optional";
	// XREF index names
	private static final String IDX_INGRED = "idx_ingred_id";
	private static final String IDX_DRESSING = "idx_dressing_id";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {


		try {
			db.beginTransaction();
			String CREATE_DRESSING_TABLE = "CREATE TABLE " + TABLE_DRESSING + "("
					+ DRESSING_ID + " INTEGER PRIMARY KEY," + DRESSING_NAME + " TEXT,"
					+ DRESSING_INFO + " TEXT) ;";
			db.execSQL(CREATE_DRESSING_TABLE);

			String CREATE_INGRED_TABLE = "CREATE TABLE " + TABLE_INGRED + "("
					+ INGRED_ID + " INTEGER PRIMARY KEY," + INGRED_NAME + " TEXT" + ")";
			db.execSQL(CREATE_INGRED_TABLE);

			String CREATE_XREF_TABLE = "CREATE TABLE " + TABLE_XREF + "("
					+ XREF_DRESS_ID + " INTEGER NOT NULL," 
					+ XREF_INGRED_ID + " INTEGER NOT NULL," 
					+ XREF_PARTS + " TEXT,"
					+ XREF_OPTIONAL + " INTEGER NOT NULL,"
					+ "PRIMARY KEY (" + XREF_DRESS_ID + "," + XREF_INGRED_ID + ") ,"
					+ "FOREIGN KEY (" + XREF_DRESS_ID + ") "
					+ "REFERENCES " + TABLE_DRESSING + "(" + DRESSING_ID + ") ,"
					+ "FOREIGN KEY (" + XREF_INGRED_ID + ") "
					+ "REFERENCES " + TABLE_INGRED + "(" + INGRED_ID + ") "
					+ ")";

			db.execSQL(CREATE_XREF_TABLE);

			db.setTransactionSuccessful();
		} catch(SQLException e) {
			//TODO
			e.printStackTrace();
		}
		finally {
			db.endTransaction();
		}
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older tables if they exist
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRESSING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGRED);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_XREF);

		// Create tables again
		onCreate(db);
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void createDressingOilandVinegar() {
		String dressingName = "Oil & Vinegar";
		//Ingredient, consists of name, parts, and whether it's optional or not
		String[] ingredOil = new String[3];
		ingredOil[0] = "Olive Oil";
		ingredOil[1] = "2";
		ingredOil[2] = "0";	

		String[] ingredVinegar = new String[3];
		ingredVinegar[0] = "Balsamic Vinegar";
		ingredVinegar[1] = "1";
		ingredVinegar[2] = "0";

		//citrus is optional ingred, not needed to select the dressing
		String[] ingredCitrus = new String[3];
		ingredCitrus[0] = "Some kind of citrus (lemon, orange, etc.)";
		ingredCitrus[1] = "to taste";
		ingredCitrus[2] = "-1";

		ArrayList<String[]> ingreds = new ArrayList<String[]>();
		ingreds.add(ingredOil);
		ingreds.add(ingredVinegar);
		ingreds.add(ingredCitrus);

		//any extra info on dressing
		String dressingInfo = "add salt and pepper to taste";

		addDressing(dressingName, ingreds, dressingInfo);
	}
	
	public void createDressingHoneyMustard() {
		String dressingName = "Honey Mustard";
		//Ingredient, consists of name, parts, and whether it's optional or not
		String[] ingredHoney = new String[3];
		ingredHoney[0] = "Honey";
		ingredHoney[1] = "1";
		ingredHoney[2] = "0";	
		
		String[] ingredMustard = new String[3];
		ingredMustard[0] = "Mustard";
		ingredMustard[1] = "2";
		ingredMustard[2] = "0";	
		
		String[] ingredOil = new String[3];
		ingredOil[0] = "Olive Oil";
		ingredOil[1] = "2";
		ingredOil[2] = "0";	

		String[] ingredVinegar = new String[3];
		ingredVinegar[0] = "Balsamic Vinegar";
		ingredVinegar[1] = "1";
		ingredVinegar[2] = "-1";


		ArrayList<String[]> ingreds = new ArrayList<String[]>();
		ingreds.add(ingredOil);
		ingreds.add(ingredVinegar);
		ingreds.add(ingredMustard);
		ingreds.add(ingredHoney);

		//any extra info on dressing
		String dressingInfo = "add salt and pepper to taste";

		addDressing(dressingName, ingreds, dressingInfo);
	}
	
	public void createDressingOilOnly() {
		String dressingName = "Simple Olive Oil";
		//Ingredient, consists of name, parts, and whether it's optional or not
		String[] ingredOil = new String[3];
		ingredOil[0] = "Olive Oil";
		ingredOil[1] = "1";
		ingredOil[2] = "0";	

		//citrus is optional ingred, not needed to select the dressing
		String[] ingredCitrus = new String[3];
		ingredCitrus[0] = "Some kind of citrus (lemon, orange, etc.)";
		ingredCitrus[1] = "to taste";
		ingredCitrus[2] = "-1";

		ArrayList<String[]> ingreds = new ArrayList<String[]>();
		ingreds.add(ingredOil);
		ingreds.add(ingredCitrus);

		//any extra info on dressing
		String dressingInfo = "add salt and pepper to taste\n"
				+ "let the vegetables shine";

		addDressing(dressingName, ingreds, dressingInfo);
	}
	
	public void createDressingSeasamePeanut() {
		String dressingName = "Seasame and Peanut Dressing";
		//Ingredient, consists of name, parts, and whether it's optional or not
		String[] ingredOil = new String[3];
		ingredOil[0] = "Seasame Oil";
		ingredOil[1] = "1";
		ingredOil[2] = "0";	

		String[] ingredPB = new String[3];
		ingredPB[0] = "Peanut Butter";
		ingredPB[1] = "1";
		ingredPB[2] = "0";

		//citrus is optional ingred, not needed to select the dressing
		String[] ingredCitrus = new String[3];
		ingredCitrus[0] = "Some kind of citrus (lemon, orange, etc.)";
		ingredCitrus[1] = "to taste";
		ingredCitrus[2] = "-1";

		ArrayList<String[]> ingreds = new ArrayList<String[]>();
		ingreds.add(ingredOil);
		ingreds.add(ingredPB);
		ingreds.add(ingredCitrus);

		//any extra info on dressing
		String dressingInfo = "add something spicy if you'd like";

		addDressing(dressingName, ingreds, dressingInfo);
	}
	
	public void createAllDressings() {
		createDressingSeasamePeanut();
		createDressingOilOnly();
		createDressingOilandVinegar();
		createDressingHoneyMustard();
	}
	
	// Adding new dressing
	void addDressing(String dressingName, ArrayList<String[]> ingredList, String dressingInfo) {
		SQLiteDatabase db = this.getWritableDatabase();

		//dressing data
		ContentValues dressingValues = new ContentValues();
		dressingValues.put(DRESSING_NAME, dressingName); // Dressing Name
		dressingValues.put(DRESSING_INFO, dressingInfo); // Dressing Name

		try{
			// Inserting Row
			int dressingID = (int)db.insert(TABLE_DRESSING, null, dressingValues);

			ContentValues ingredValues = new ContentValues();
			ContentValues xrefValues = new ContentValues();
			String[] ingred = null;
			int ingredID = 0;
			//ingreds
			for(int i = 0; i < ingredList.size(); i++) {
				ingred = ingredList.get(i);
				ingredID = getIngredID(ingred[0]);
				//insert ingred, if not already there
				if(ingredID == 0){
					ingredValues.put(INGRED_NAME, ingred[0]);
					ingredID = (int)db.insert(TABLE_INGRED, null, ingredValues);
				}
				xrefValues.put(XREF_DRESS_ID, dressingID);
				xrefValues.put(XREF_INGRED_ID, ingredID);
				xrefValues.put(XREF_PARTS, ingred[1]);
				xrefValues.put(XREF_OPTIONAL, Integer.parseInt(ingred[2]));
				db.insert(TABLE_XREF, null, xrefValues);
			}
		}
		catch(SQLException e) {
			//TODO handle exception
			System.out.println(e.toString());
		}
	}

	// Getting All xref
	public List<String> getAllXREF() {
		List<String> xrefList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT xref.* FROM " + TABLE_XREF + " xref";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				String s = "";
				s =s+cursor.getString(0)+","
				+cursor.getString(1)+","
				+cursor.getString(2)+","
				+cursor.getString(3);
				// Adding ingred to list
				xrefList.add(s);
			} while (cursor.moveToNext());
		}
		for(String xref: xrefList) {
			System.out.println(xref);
		}
		// return ingred list
		return xrefList;
	}
    // Getting Ingred's ID if it is already in the table, otherwise return 0
    public int getDressingID(String dressingName) {
        int dressingID = 0;
        // Select All Query
        String selectQuery = "SELECT dress." + DRESSING_ID
                + " FROM " + TABLE_DRESSING + " dress "
                + " WHERE dress." + DRESSING_NAME + " = '" + dressingName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            dressingID = Integer.parseInt(cursor.getString(0));
            if (cursor.moveToNext()){
                System.out.println("multiple dressing with same name!!!");
            }
        }
        return dressingID;
    }
		
	// Getting Ingred's ID if it is already in the table, otherwise return 0
	public int getIngredID(String ingredName) {
		int ingredID = 0;
		// Select All Query
		String selectQuery = "SELECT ingred." + INGRED_ID 
				+ " FROM " + TABLE_INGRED + " ingred "
				+ " WHERE ingred." + INGRED_NAME + " = '" + ingredName + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			ingredID = Integer.parseInt(cursor.getString(0));
			if (cursor.moveToNext()){
				System.out.println("multiple ingreds with same name!!!");
			}
		}
		return ingredID;
	}
	// Getting All Ingreds
	public List<Ingred> getAllIngreds() {
		List<Ingred> ingredList = new ArrayList<Ingred>();
		// Select All Query
		String selectQuery = "SELECT ingred." + INGRED_ID + ", ingred." + INGRED_NAME 
				+ " FROM " + TABLE_INGRED + " ingred";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Ingred ingred = new Ingred();
				ingred.setID(Integer.parseInt(cursor.getString(0)));
				ingred.setName(cursor.getString(1));
				// Adding ingred to list
				ingredList.add(ingred);
			} while (cursor.moveToNext());
		}
		return ingredList;
	}
	
	public List<Dressing> getDressingsFromIngredList(List<Ingred> ingredsList) {
		List<Dressing> dressings = new ArrayList<Dressing>();
		List<Integer> dressingIDs = new ArrayList<Integer>();

		//get ingred id's as comma separated list for query
		StringBuilder ingreds = new StringBuilder();
		String s;
        for (Ingred i : ingredsList) {
        	s = i.getID()+"";
        	ingreds.append(s);
               if (ingreds.length() > 0)
            	   ingreds.append(",");
        }
        //cut off traling comma,
        String ingredsCSV = ingreds.substring(0, ingreds.length()-1);
		// Get dressings that contain ingreds selected
		String selectQuery = "SELECT distinct dress." + DRESSING_ID + " "
		+ " FROM " 
			+ TABLE_DRESSING + " dress"
			+ " , "
			+ TABLE_XREF + " xref "
		+ " WHERE xref." + XREF_INGRED_ID + " in (" + ingredsCSV + ")"
			+ " AND dress." + DRESSING_ID 
			+ " = xref." + XREF_DRESS_ID ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		s = "";
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Integer dressingID = cursor.getInt(0);
				dressingIDs.add(dressingID);
				s = s + dressingID + ",";
			} while (cursor.moveToNext());
		}
        //cut off traling comma,
		String dressingsCSV = s.substring(0, s.length()-1);
		selectQuery = "SELECT dress." + DRESSING_ID + ", "
					+ " dress." + DRESSING_NAME + ", "
					+ " ingred." + INGRED_ID + ", "
					+ " ingred." + INGRED_NAME + ", "
					+ " xref." + XREF_PARTS + ", "
					+ " xref." + XREF_OPTIONAL + ", "
					+ " dress." + DRESSING_INFO + ""
				+ " FROM " 
				+ TABLE_DRESSING + " dress"
				+ " , "
				+ TABLE_XREF + " xref "
				+ " , "
				+ TABLE_INGRED + " ingred "
				+ " WHERE dress." + DRESSING_ID + " in (" + dressingsCSV + ")" //TODO double check logic
				+ " AND dress." + DRESSING_ID + " = xref." + XREF_DRESS_ID  
				+ " AND ingred." + INGRED_ID + " = xref." + XREF_INGRED_ID 
				+ " ORDER BY dress." + DRESSING_ID + " ASC";

		 cursor = db.rawQuery(selectQuery, null);

		 int dressingID = 0;
		 int tempDressID = 0;
		 boolean hasAllIngreds = true;
		 Dressing dress = null;
		 List<String> ingredIDs = Arrays.asList(ingredsCSV.split(","));
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				dressingID = Integer.parseInt(cursor.getString(0));
				if(dressingID != tempDressID) {
					if(dress != null && hasAllIngreds) {
						dressings.add(dress);
					}
					hasAllIngreds = true;
					tempDressID = dressingID;
					dress = new Dressing(dressingID,cursor.getString(1),cursor.getString(6));
					//they have don't have an ingred
					if(!ingredIDs.contains(cursor.getString(2))){
						//and it is not an optional ingred
						//skip this dressing
						if(cursor.getString(5).equals("0")){
							hasAllIngreds = false;
						}
						//otherwise leave this ingred off the recipe
						else{
							continue;
						}
					}
					dress.addIngred(cursor.getString(3),cursor.getString(4),cursor.getString(5));
				}
				else {
					//they have don't have an ingred
					if(!ingredIDs.contains(cursor.getString(2))){
						//and it is not an optional ingred
						//skip this dressing
						if(cursor.getString(5).equals("0")){
							hasAllIngreds = false;
						}
						//otherwise leave this ingred off the recipe
						else{
							continue;
						}
					}
					dress.addIngred(cursor.getString(3),cursor.getString(4),cursor.getString(5));
				}
			} while (cursor.moveToNext());
		}
		if(hasAllIngreds){
			dressings.add(dress);
		}
		return dressings;
	}

}