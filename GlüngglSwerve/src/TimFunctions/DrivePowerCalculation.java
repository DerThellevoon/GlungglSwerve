package TimFunctions;

public class DrivePowerCalculation {
	
	public static double getDrivePower(double xAxis, double yAxis, double deadzoneValue) {
		
		double drivePower = 0;
		
		if(xAxis <= deadzoneValue && xAxis >= -deadzoneValue && yAxis <= deadzoneValue && yAxis >= -deadzoneValue) {
		
			return 0;
		}else {
			
			drivePower = Math.sqrt(Math.pow(xAxis, 2) + Math.pow(yAxis, 2));
			
			if(drivePower > 1) {
				return 1;
			}else {
				return drivePower;
			}
		}	
	}
}
