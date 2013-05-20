package androidFile.AlmaWiFinder;

import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.AlertDialog;



public class AlmaDownload extends ListActivity implements AdapterView.OnItemSelectedListener {
	
    
	private final String DEBUG = "AlmaFinderDEBUG";
	private AlmaDB mDbHelper;
	ArrayList<DataSet> almaPoint;
    private String[] distrect = {"Bologna", "Cesena", "Forli", "Ravenna", "Rimini"};
    Animation anim = null;
    AlertDialog alert;
    ProgressDialog mDialog2;
    URL url=null; 
    ArrayAdapter itemsAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anim = AnimationUtils.loadAnimation( this, R.anim.magnify );
        ListView v = getListView();		// set up item selection listener
		v.setOnItemSelectedListener(this);
		itemsAdapter = new ArrayAdapter( this, R.layout.row, distrect);
		setListAdapter( itemsAdapter );
		
		/* 
		 ProgressDialog dialog = new ProgressDialog(this);
         dialog.setMessage("Please wait while loading...");
         dialog.setIndeterminate(true);
         dialog.setCancelable(true);
         dialog.show();
         */
		}
 
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
     super.onListItemClick(l, v, position, id);
 	
      readXML(distrect[position]);
       v.startAnimation( anim );
       
     }
    

    public void readXML(String urlD){
    	
    	try {
    		
    		 
  		 if(urlD.equals("Bologna")){
  			url = new URL("http://www.unibo.it/Portale/Ateneo/Strutture/Strutture+di+servizio/80080/AlmaWIFI/Mappa.htm?format=kml");
  		 }else if(urlD.equals("Cesena")){
  			url = new URL("http://www.polocesena.unibo.it/Polo+Cesena/UniboMappe/Alma_WiFi.htm?format=kml");
  		 } else if(urlD.equals("Forli")){
  			url = new URL("http://www.poloforli.unibo.it/Polo+Forli/UniboMappe/Alma_WiFi.htm?format=kml");
  		 }else if(urlD.equals("Ravenna")){
   			url = new URL("http://www.poloravenna.unibo.it/Polo+Ravenna/UniboMappe/Alma_WiFi.htm?format=kml");
   		 }else if(urlD.equals("Rimini")){
   			url = new URL("http://www.polorimini.unibo.it/Polo+Rimini/UniboMappe/Alma_WiFi.html?format=kml");
   		 }
  		 
  		mDbHelper = new AlmaDB(this);
  		mDbHelper.open();
  		
            // a SAXParser from the SAXPArserFactory. 
           SAXParserFactory spf = SAXParserFactory.newInstance();
           SAXParser sp = spf.newSAXParser();
           // Get the XMLReader of the SAXParser created. 
           XMLReader xr = sp.getXMLReader();
          //  Create a new ContentHandler and apply it to the XML-Reader
           Handler myHandler = new Handler();
           xr.setContentHandler(myHandler);
          //  Parse the xml-data from our URL. 
           xr.parse(new InputSource(url.openStream()));
        //    Parsing has finished. 
           DataSet dataSet = myHandler.getParsedData();
          
           almaPoint=myHandler.getAlmaPoint();
           
           Iterator<DataSet> it = almaPoint.iterator();
    
                
           
   	while(it.hasNext()) {
      		DataSet dt= it.next();
      		if(pointExist(dt.getname())==-1)
      			mDbHelper.createPoint(dt.getname(), dt.getdescription(), dt.getAddress(), dt.getLatitudeString(), dt.getLongitudineString());
      		else
      			mDbHelper.updatePoint(pointExist(dt.getname()), dt.getname(), dt.getdescription(), dt.getAddress(), dt.getLatitudeString(), dt.getLongitudineString());
 
   	}
   	
   	Intent intent = new Intent();
		intent.setClass(AlmaDownload.this, AlmaWiFinder.class);
		finish();
		startActivity(intent);
   	
   	mDbHelper.close();
       } catch (Exception e) {
    	   Log.e("DEBUG"," error "+e);
  
      }
       
   } 
    
    
    private int pointExist(String name){
   	  int exist =-1;
   	  Cursor cursor = mDbHelper.fetchAllPoints();
   	  
   	  for(int i=0; i<cursor.getCount();i++){ 
   		  cursor.moveToPosition(i); 
   		  String rip = cursor.getString(cursor.getColumnIndexOrThrow(AlmaDB.KEY_NAME));
   		  if(rip.equals(name))
   			  exist = i;
   		  
   	  		}
   	  return exist;
     }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
 
    }
    