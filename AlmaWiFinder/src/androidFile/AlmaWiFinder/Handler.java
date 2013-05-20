package androidFile.AlmaWiFinder;



import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler{

	private DataSet myDataSet;
	
    public ArrayList<DataSet> almaPoint;
    
    private boolean in_folder = false;
    private boolean in_placemark = false;
    private boolean in_name = false;
    private boolean in_description= false;
    private boolean in_Address=false;
    private boolean in_coordinate=false;
	private final String MY_DEBUG_TAG = "AlmaFinderDEBUG";
    
   public ArrayList<DataSet> getAlmaPoint(){
	   return this.almaPoint;
	   
	
	   
	   
   }

    public DataSet getParsedData() {
         return this.myDataSet;
    }

    // Metodi LETTURA XML
  
    @Override
    public void startDocument() throws SAXException {
        //creo oggetto DataSet 
    	this.myDataSet = new DataSet(); 
         // creo ArrayList almaPont
         almaPoint = new ArrayList();  
    }

    @Override
    public void endDocument() throws SAXException {
         // Nothing to do
    }

  
    	//Lettura XML
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    	
    	 if (localName.equals("Folder")) {
              this.in_folder = true;     
         }else if (localName.equals("Placemark")) {
              this.in_placemark = true;
              myDataSet= new DataSet();
         }else if (localName.equals("name")) {
              this.in_name = true;
         }else if (localName.equals("description"))  {
        	  this.in_description= true;
         }else if(localName.equals("address"))  {
        	 this.in_Address= true;
         }else if (localName.equals("coordinates"))
       	  	this.in_coordinate=true;	   
      
    }//startElement

  
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
         if (localName.equals("Folder")) {
        	 	this.in_folder = false;
         }else if (localName.equals("Placemark")) {
              	this.in_placemark = false;
              	
   //Aggiungo all'ArrayList point un nuovo punto	
      almaPoint.add(myDataSet);
              	
         }else if (localName.equals("name")) {
              this.in_name = false;
         }else if (localName.equals("description"))  {
           this.in_description= false;
         }else if(localName.equals("address"))  {
           this.in_Address= false;
         }else if(localName.equals("coordinates"))   {
           this.in_coordinate=false;
         }
         
    }//endElement
    
   // prelievo il contenuto dei Tag
    @Override
   public void characters(char ch[], int start, int length) {
         if(this.in_name){
         myDataSet.setname(new String(ch, start, length));
         }
         if(this.in_description){
         myDataSet.setdescription(new String(ch, start, length));
         }
         if(this.in_Address){
           myDataSet.setAddress(new String(ch, start, length));
         }
         if(this.in_coordinate){
           myDataSet.setcoordinates(new String(ch, start, length));
         }  
     
   }//characters
   
}//Class Handler
