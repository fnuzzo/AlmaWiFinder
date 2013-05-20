package androidFile.AlmaWiFinder;
import java.util.Comparator;

// Classe che implementa l'interfaccia Comparator 

public class AlmaPointComparator implements Comparator <DataSet> {

	public int compare(DataSet  point1, DataSet point2){

// Criterio ordinamento
		
		double dist1 =point1.getDistance();

		double dist2 = point2.getDistance();

		if( dist1 > dist2 )

		return 1;

		else if( dist1 < dist2 )

		return -1;

		else

		return 0;

		}

	

}// Class AlmaPointComparator 

