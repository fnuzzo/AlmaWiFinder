package androidFile.AlmaWiFinder;

import com.google.android.maps.MapActivity;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;



public class AlmaView extends MapActivity {
GeoPoint p;
GeoPoint myp;
private final String DEBUG = "AlmaFinderDEBUG";

	private MapView myMapView; 
	TextView tv;
	String str;
	String name_it;
	String description_it;
	String address_it;
	String nameView =null;
	//CharSequence cs;
	 double latitudine;
     double longitudine;
     double mylatitudine;
     double mylongitudine;
     String distance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view);
		
		
		Bundle extras = getIntent().getExtras();
		name_it = getString(R.string.styled_textName);
		description_it = getString(R.string.styled_textDesc);
		address_it = getString(R.string.styled_textAddress);
		
		 if (extras != null) {
		    	
		        String name = extras.getString("Name");
		        String description = extras.getString("Description");
		        String address = extras.getString("Address");
		        latitudine = extras.getDouble("Latitudine");
		        longitudine = extras.getDouble("Longitudine");
		        mylatitudine = extras.getDouble("MyLatitudine");
		        mylongitudine = extras.getDouble("MyLongitudine");
		        distance =extras.getString("distance");
		        if (name != null) {
		        	
		        	tv=(TextView)findViewById(R.id.name);
		        	if(name.contains("ALMAWIFI:")){
		        		nameView = name.substring(9, name.length());
		        		tv.setText(name_it+nameView);
		        		}
		        	else
		        	tv.setText(" "+name);
		       
		        	}
		        if (description!= null) {
		        	tv=(TextView)findViewById(R.id.description);
		           tv.setText(description_it+description);
		        	}
		        if (address!= null) {
		        	tv=(TextView)findViewById(R.id.address);
		           tv.setText(address_it+address);
		        	}
		        Log.e("DEBUG"," mylatitdine "+latitudine+" mylongitudine "+longitudine);
		        	tv=(TextView)findViewById(R.id.distance);
		           tv.setText("Approximate distance: "+distance);
		         
			        
		           myMapView = (MapView) findViewById(R.id.mapSpace);
		 
		        p = new GeoPoint((int) ( latitudine * 1000000),(int) (longitudine * 1000000));
		        myp= new GeoPoint((int) ( mylatitudine* 1000000),(int) ( mylongitudine* 1000000));
		        // Get the controller, that is used for translation and zooming
		        MapController mc = myMapView.getController();
		         // Translate to the Statue of Liberty
		        MyLocationOverlay myLocationOverlay = new MyLocationOverlay();

		        List<Overlay> list = myMapView.getOverlays();
		        list.add(myLocationOverlay); 
		        mc.animateTo(p);
		        mc.setCenter(p) ;
		         // Zoom Very close
		          mc.setZoom(20);
		        
		        
		    }
		 
		 Button closeButton = (Button) findViewById(R.id.list);
		 closeButton.setOnClickListener(new OnClickListener(){ 
			 public void onClick(View arg0) { 
				 finish();
				 }
			 });
		 Button mapButton = (Button) findViewById(R.id.gomaps);
		 mapButton.setOnClickListener(new OnClickListener(){ 
			 public void onClick(View arg0) { 
				 Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr="+mylatitudine+","+mylongitudine+"&daddr="+latitudine+","+longitudine+"&hl=it");  
				 Intent it = new Intent(Intent.ACTION_VIEW, uri);  
				 startActivity(it);  

			 }
			 });

      }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	class MyLocationOverlay extends Overlay {
    	@Override
    	public boolean draw(Canvas canvas, MapView mapView ,boolean shadow, long when) {

    	super.draw(canvas, mapView, shadow);
    	
    	Point pointA = new Point();
    	mapView.getProjection().toPixels(p, pointA);
    	
    	Point pointB = new Point();
    	mapView.getProjection().toPixels(myp, pointB);
    	
    	Paint pointP = new Paint(); 	
    	pointP.setColor(Color.RED);
    	pointP.setStyle(Style.FILL);
    	
    	Paint point = new Paint(); 	
    	point.setColor(Color.GREEN);
    	point.setStyle(Style.FILL);
    	
    	Paint linea = new Paint(); 	
    	linea.setColor(Color.RED);
    	linea.setStyle(Style.FILL);
    	linea.setStrokeWidth(2);
    	
    	Paint pointCircle = new Paint(); 
    	pointCircle.setColor(Color.BLACK);
    	pointCircle.setStyle(Style.STROKE);
    	pointCircle.setStrokeWidth(1);
    	
    	
    	Paint circles = new Paint();
    	circles.setColor(Color.GREEN);
    	circles.setStyle(Style.STROKE);
    	circles.setStrokeWidth(2);
    	
    	//Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.star_big_on);
    	//canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
    	canvas.drawText("  Here I am…", pointB.x, pointB.y, linea);
    	
    	canvas.drawCircle(pointA.x, pointA.y, 20, circles);
    	canvas.drawCircle(pointA.x, pointA.y, 15, circles);
    	canvas.drawCircle(pointA.x, pointA.y,10, circles);
    	canvas.drawCircle(pointA.x, pointA.y,4, point);
    	canvas.drawCircle(pointA.x, pointA.y,4, pointCircle);
    	canvas.drawCircle(pointB.x, pointB.y,4, pointP);
    	canvas.drawCircle(pointB.x, pointB.y,4, pointCircle);
    	canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, linea);
    	return true;
}    	
    }	
}
