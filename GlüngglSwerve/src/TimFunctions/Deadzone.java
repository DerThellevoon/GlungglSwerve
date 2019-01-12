// Zusammenfassung
//	- Funktion, welche alle Inputwerte im Bereich von -inputDeadzone bis +inputDeadzone auf 0 setzt und
//		der verbliebene Bereich (1 - inputDeadzone) wieder auf 0 bis 1 skaliert.
//	- Beispiel: inputDeadzone ist 0.5.
//		Alle Werte von -0.5 bis 0.5 resultieren in einem Output von 0.
//		Ein Wert von 0.6 wird resultiert NICHT in einem Output von 0.6, 
//		sondern wird skaliert vom Bereich 0.5 bis 1 zum Bereich 0 bis 1 und resultiert deshalb in
//		einem Output von 0.2.
//	- Funktioniert mit negativen und Dezimalzahlen.

package TimFunctions;

public class Deadzone {
	
	public static double getAxis(double inputAxis, double inputDeadzone) {
		
		double outputAxis;
		
		if(inputAxis > inputDeadzone) { // Test ob der Wert positiv ist und groesser als der Deadzonewert
			outputAxis = MapFunction.map(inputAxis, inputDeadzone, 1, 0, 1);
		}else if(inputAxis < -inputDeadzone) {
			outputAxis = MapFunction.map(inputAxis, -inputDeadzone, -1, 0, -1);
		}else {
			outputAxis = 0;
		}
		
		return outputAxis;
	}
}
