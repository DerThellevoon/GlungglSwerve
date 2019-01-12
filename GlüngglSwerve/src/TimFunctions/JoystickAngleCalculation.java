package TimFunctions;

public class JoystickAngleCalculation {

	public static double joystickAngle(double xAxis, double yAxis, double deadzoneValue) {
		
		if(xAxis <= deadzoneValue && xAxis >= -deadzoneValue && yAxis <= deadzoneValue && yAxis >= -deadzoneValue) {
			return 0;
		}else if(yAxis < 0) {
			if(xAxis < 0) {
				return 180 * Math.atan(xAxis/yAxis) / Math.PI - 180;
			}else {
				return 180 * Math.atan(xAxis/yAxis) / Math.PI + 180;
			}
		}else {
			return 180 * Math.atan(xAxis/yAxis) / Math.PI;
		}
	}
	
	public static boolean insideDeadzone(double xAxis, double yAxis, double deadzoneValue) {
		
		if(xAxis <= deadzoneValue && xAxis >= -deadzoneValue && yAxis <= deadzoneValue && yAxis >= -deadzoneValue) {
			return true;
		}else {
			return false;
		}
	}
}