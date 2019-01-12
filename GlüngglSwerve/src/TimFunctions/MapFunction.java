//Zusammenfassung
//	- Funktion, welche den Wert x skaliert mithilfe des Inputsbereiches und des Outputsbereiches.
//	- Beispiel: Beim Inputbereich von -1 bis 1 und dem Outputbereich von 0 bis 1 resultiert ein Input
//		von 0 in einem Output von 0.5.
//	- Funktioniert auch mit negativen und Dezimalzahlen.

package TimFunctions;

public class MapFunction {
	
	public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
		
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin; // Mathematische Berechnung, welche die oben beschriebene Funktion ausfuehrt.
		
	}
	
}