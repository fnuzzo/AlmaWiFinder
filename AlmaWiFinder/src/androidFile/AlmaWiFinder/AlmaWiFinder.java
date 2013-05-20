package androidFile.AlmaWiFinder;

import android.app.TabActivity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;

 public class AlmaWiFinder extends TabActivity{
	
	private final String DEBUG = "AlmaFinderDEBUG";
	
	protected LocationManager myLocationManager = null;
    protected Location myLocation = null; 
    Criteria criteria;
    String latLongString=null; 
    	
  
    
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 
		 myLocation=getLocation(); // crea un oggetto in base alle coordinate geografiche
	
	     final TabHost tabHost = getTabHost();
	     
	     // tab List
	     tabHost.addTab(tabHost.newTabSpec("tab1")
	                .setIndicator("Points List",getResources().getDrawable(R.drawable.lista))
	                .setContent(new Intent(this, AlmaList.class)
	                .putExtra("MyLatitudine",myLocation.getLatitude())
	                .putExtra("MyLongitudine", myLocation.getLongitude())));
	     
	     			//montagnola coordinate per test e presentazione
	                // solo per test e presentazione!!!!
	               // .putExtra("MyLatitudine",44.502168)
	               // .putExtra("MyLongitudine", 11.346946)));

	     // Tab get Kml   
	     tabHost.addTab(tabHost.newTabSpec("tab2")
	                .setIndicator("Get kml",getResources().getDrawable(R.drawable.download))
	                .setContent(new Intent(this, AlmaDownload.class)));
     }

		// ricava la posizione GPS
		public Location getLocation(){
			
			myLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			String provider = myLocationManager.getBestProvider(criteria, true);
			myLocation = myLocationManager.getLastKnownLocation(provider);
			return myLocation;
		}
		
 }		
 
		 /*
		  * public String getStringLocation(){
		 
		getLocation();
		
		String latLongString=null;
		 if (myLocation != null) {
		     double lat = myLocation.getLatitude();
		     double lng = myLocation.getLongitude();
		     latLongString = "Lat:" + lat + "\nLong:" + lng;
		 }else 
			 latLongString="Criteria Localizzazione Fallita"; 
		return latLongString;
	}

        this.myLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        this.myLocation = myLocationManager.getLastKnownLocation("gps");
        
        if (myLocation != null) {
		     double lat = myLocation.getLatitude();
		     double lng = myLocation.getLongitude();
		     latLongString = "Lat:" + lat + "\nLong:" + lng;
		 }else 
			 latLongString="Localizzazione Fallita"; 
			 */
