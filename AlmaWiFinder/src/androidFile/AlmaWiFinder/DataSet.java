package androidFile.AlmaWiFinder;

import android.util.Log;

public class DataSet {
	//DEBUG TAG
	private final String DEBUG = "AlmaFinderDEBUG";
	
    private String name=null;
    private	String description=" ";
    private	String address=null;
    private	String coordinates=null;
   // Location location;
    double latitudine=0;
    double longitudine=0;
    double distance=0;
    String distanceStr=null;
    private String latid=null;
    private String longit=null;
    
    
    // Getter & Setter della classe  dataSet
	
	 public void setDistance(double distance) {
	        this.distance = distance;
	    }
	    
	   public double getDistance() {
	    	return distance;     
	    }
  
	   public void setDistanceStr(String distance) {
	        this.distanceStr = distance;
	    }
	    
	   public String getDistanceStr() {
	    	return distanceStr;     
	    }
	   
	   
	   public double getLatitudine() {
	    	return latitudine;     
	    }
	   
	   public double getLongitudine() {
	    	return longitudine;     
	    }
	   
	   public String getLatitudeString() {
	    	return latid;     
	    }
	   
	   public String getLongitudineString() {
	    	return longit;     
	    }
	   
	   
	   public void setLatitudine(String latitudine) {
	        this.latitudine = Double.parseDouble(latitudine);
	    }
	   public void setLongitudine(String longitudine) {
	        this.longitudine = Double.parseDouble(longitudine);
	    }
	
    
    public void setname(String name) {
        this.name = name;
    }
    
    
   public String getname() {
    	return name;     
    }
  
   public void setdescription(String description){
	     this.description=description;
	 }
   
   public String getdescription(){
        return description;
     }
   
   public void setAddress(String address){
	   this.address=address;
	 }
   
   public String getAddress(){
      return address;
     }

   public void setcoordinates(String coordinates){
	   longit = coordinates.substring(coordinates.indexOf(0) + 1,coordinates.indexOf(","));
	   latid = coordinates.substring(coordinates.indexOf(",") + 1,coordinates.lastIndexOf(","));

       this.coordinates=coordinates;
     }
   
   public String getcoordinates(){
    	return coordinates;
     }
}