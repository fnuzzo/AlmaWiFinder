package androidFile.AlmaWiFinder;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AlmaList extends ListActivity implements AdapterView.OnItemSelectedListener{
	
	private final String DEBUG = "AlmaFinderDEBUG";
	
	  double latitudine=0.0;
	  double longitudine=0.0;
	protected LocationManager myLocationManager = null;
    protected Location myLocation = null; 
    Criteria criteria;
    String latLongString=null; 
    double distance;
    String formattedDistance;
    String dist;
	 public ArrayList<DataSet> almaPointOrdinated;
	 public ArrayList<String> listItems = new ArrayList<String>();
	 
	 private AlmaDB mDbHelper;
	 private Cursor pCursor;
	 DataSet dt;
	 Animation anim = null;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Bundle extras = getIntent().getExtras();
	        if (extras != null) {
		        latitudine = extras.getDouble("MyLatitudine");
		        longitudine = extras.getDouble("MyLongitudine");
		        }
	
	        anim = AnimationUtils.loadAnimation( this, R.anim.magnify );
	        ListView v = getListView();		// set up item selection listener
			v.setOnItemSelectedListener(this);
	        
			sortArray();
	        
			ArrayAdapter itemsAdapter = new ArrayAdapter( this, R.layout.row, listItems );
			setListAdapter( itemsAdapter );
			if(listItems.size()==0){
				new AlertDialog.Builder(AlmaList.this)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	
                    }
                })
                .create().show();	
				
			}
			
	   }
	 
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id){
	     super.onListItemClick(l, v, position, id);
	     
	     // Get the item that was clicked
	     	DataSet dt = almaPointOrdinated.get(position);
	     	
	     		try{ 	 
	     			Intent intent = new Intent();
	     				intent.setClass(AlmaList.this, AlmaView.class);
	     				intent.putExtra("Name", dt.getname());
	     				intent.putExtra("Address", dt.getAddress());
	     				intent.putExtra("Description", dt.getdescription());
	     				intent.putExtra("Latitudine", dt.getLatitudine());
	     				intent.putExtra("Longitudine", dt.getLongitudine());
	     				intent.putExtra("MyLatitudine", latitudine );
	     				intent.putExtra("MyLongitudine",longitudine);
	     				intent.putExtra("distance" , dt.distanceStr);
	     				startActivity(intent);
	     				
	     			} catch (Exception e) {
	     					Log.e("DEBUG","exception "+e);
	     					} 
	     			v.startAnimation( anim );
	     }
	    
	    
	    
	public void sortArray(){
		
		  almaPointOrdinated = new ArrayList<DataSet>();
 
	   	 try{
	   		 mDbHelper = new AlmaDB(this);
	   		 mDbHelper.open();
	   		

	   	 pCursor = mDbHelper.fetchAllPoints();
	     startManagingCursor(pCursor);
	   		
		 for(int i=0; i<pCursor.getCount();i++){ 
			
			 pCursor.moveToPosition(i);
      			dt =new DataSet();
      			dt.setname(pCursor.getString(pCursor.getColumnIndexOrThrow(AlmaDB.KEY_NAME)));
      			dt.setAddress(pCursor.getString(pCursor.getColumnIndexOrThrow(AlmaDB.KEY_ADDRESS)));
      			dt.setdescription(pCursor.getString(pCursor.getColumnIndexOrThrow(AlmaDB.KEY_DESCRIPTION)));
      			dt.setLatitudine(pCursor.getString(pCursor.getColumnIndexOrThrow(AlmaDB.KEY_LATITUDINE)));
      			dt.setLongitudine(pCursor.getString(pCursor.getColumnIndexOrThrow(AlmaDB.KEY_LONGITUDINE)));
		
			 	float results[] = new float[3];
		   	   	
			 	Location.distanceBetween(latitudine,longitudine, dt.getLatitudine(), dt.getLongitudine(), results);
			 	dt.setDistance(results[0]);
			 	if(!almaPointOrdinated.contains(dt))
			 	almaPointOrdinated.add(dt);
		 
		}
	   	 }catch(Exception e){
   			 Log.e("DEBUG"," error "+e);
   		 	}
		 
		Collections.sort(almaPointOrdinated, new AlmaPointComparator());
		Iterator<DataSet> it = almaPointOrdinated.iterator();

      	while(it.hasNext()) {
      		DataSet dts=it.next();
 	   		final DecimalFormat df = new DecimalFormat("####0.000");
 	   		distance=(dts.getDistance()/1000);
            formattedDistance = df.format(distance);
            String scala;
            if(formattedDistance.substring(0, 1).equals("0"))
            	scala="m";
            else 
            	scala="km";	
            
            String dist = formattedDistance+" "+scala;
            dts.setDistanceStr(dist);
 	   		listItems.add(dts.getname()+" ("+dist+")");
      		}
      
	}
		  
		
		// Metodi non implementati
		  public void onItemSelected(AdapterView parent, View v, int position, long id) {}
		  public void onNothingSelected(AdapterView parent) {}
	

}// Class AlmaList

