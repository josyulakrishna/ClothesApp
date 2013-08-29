package rev0.clothesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {	
	 // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "ClothesAppPhotoManager.db";
 
    // Contacts table name
    private static final String TABLE_NAME = "CACLOTHES";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_COLOR = "color";
    private static final String KEY_PATH = "PATH";
	public DataBaseHandler(Context context, String name, CursorFactory factory,int version) {
		super(context, DATABASE_NAME, null , DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	 private static final String DATABASE_CREATE = "create table "
		      + TABLE_NAME + "(" + KEY_ID
		      + " integer primary key autoincrement,"
		      + KEY_BRAND + " text, "
		      +KEY_COLOR+" text,"
		      +KEY_PATH+ " text not null"+");";
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE);
	}

	public boolean addCloth(ClothesDataBase clothesdatabase) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_PATH, clothesdatabase.getPATH());
	    values.put(KEY_COLOR, clothesdatabase.getColor());
	    values.put(KEY_BRAND, clothesdatabase.getBrand()); // Contact Name
	    
	    
	    // Contact Phone Number
	    // Inserting Row
	    if(db.insert(TABLE_NAME, null, values)!=-1){
	    	Log.d("DBH", "insert success");
	    	db.close();
	    	return true;
	    	
	    	}
	    else{
	    	Log.d("DBH", "insert fail");
	    	db.close();
	    	return false;
	    }
	     // Closing database connection
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		   Log.w(DataBaseHandler.class.getName(),
			        "Upgrading database from version " + oldVersion + " to "
			            + newVersion + ", which will destroy all old data");
			    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			    onCreate(db);
			  }
	
	public ClothesDataBase getClothById(int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
	            KEY_BRAND, KEY_COLOR }, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    ClothesDataBase contact = new ClothesDataBase(cursor.getString(1), cursor.getString(2),cursor.getString(3));
	    // return contact
	    return contact;
	}
	
	public int getClothesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
}


