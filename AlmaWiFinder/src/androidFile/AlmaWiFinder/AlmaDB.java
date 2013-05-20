package androidFile.AlmaWiFinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AlmaDB {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_LATITUDINE="latitude"; 
	public static final String KEY_LONGITUDINE ="longitude"; 
	
	   private static final String TAG = "PointDBAdapter";
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;
	    
	    /**
	     * Database creation sql statement
	     */
	    private static final String DATABASE_CREATE ="create table point (_id integer primary key autoincrement, "+ "name text not null, description text, address text not null, latitude text not null, longitude text not null);";

	    private static final String DATABASE_NAME = "AccesPoint";
	    private static final String DATABASE_TABLE = "point";
	    private static final int DATABASE_VERSION = 2;

	    private final Context mCtx;
	    
	    private static class DatabaseHelper extends SQLiteOpenHelper {

	        DatabaseHelper(Context context) {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {

	            db.execSQL(DATABASE_CREATE);
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                    + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS notes");
	            onCreate(db);
	        }
	    }
	    
	   
	    public AlmaDB(Context ctx) {
	        this.mCtx = ctx;
	    }
	    
	    public AlmaDB open() throws SQLException {
	        mDbHelper = new DatabaseHelper(mCtx);
	        mDb = mDbHelper.getWritableDatabase();
	        return this;
	    }
	    
	    public void close() {
	        mDbHelper.close();
	        
	    }
	    

	    public long createPoint(String name, String description, String address, String latitudine, String longitudine) {
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_NAME, name);
	        initialValues.put(KEY_DESCRIPTION, description);
	        initialValues.put(KEY_ADDRESS, address );
	        initialValues.put(KEY_LATITUDINE, latitudine);
	        initialValues.put(KEY_LONGITUDINE, longitudine );
	        
	     
	        
	        return mDb.insert(DATABASE_TABLE, null, initialValues);
	    }
	 
	    public boolean deletePoint(long rowId) {

	        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	    }
	    
	  
	    public Cursor fetchAllPoints() {
	        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
	                KEY_DESCRIPTION,KEY_ADDRESS,KEY_LATITUDINE,KEY_LONGITUDINE}, null, null, null, null, null);
	    }
	   
	    
	 
	    public Cursor fetchPoint(long rowId) throws SQLException {

	        Cursor mCursor =

	                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
	    	                KEY_DESCRIPTION,KEY_ADDRESS,KEY_LATITUDINE,KEY_LONGITUDINE}, KEY_ROWID + "=" + rowId, null,
	                        null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;

	    }
	    
	    
	    public boolean updatePoint(long rowId, String name, String description, String address, String latitudine, String longitudine) {
	        ContentValues args = new ContentValues();
	        args.put(KEY_NAME, name);
	        args.put(KEY_DESCRIPTION, description);
	        args.put(KEY_ADDRESS, address);
	        args.put(KEY_LATITUDINE, latitudine);
	        args.put(KEY_LONGITUDINE, longitudine);
	        
	        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	    }
	   
}
