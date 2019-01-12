package TimFunctions;

public class FieldOrientedDrive {

	public static double resultingAngle(double joystickAngle, double fieldAngle) {
		
		if(joystickAngle + fieldAngle > 180) {
			return joystickAngle - fieldAngle - 360;
		}else if(joystickAngle + fieldAngle < -180) {
			return joystickAngle - fieldAngle + 360;
		}else {
			return joystickAngle - fieldAngle;
		}
	}
}
