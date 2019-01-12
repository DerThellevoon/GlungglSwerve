package TimFunctions;

public class ActiveWheelAngleCalculation {

	public double wheelRotationsDecimal = 0;
	public int wheelRotations = 0;
	public boolean wheelAngleOperationFlip = false;
	public double wheelAngle = 0;
	
	public ActiveWheelAngleCalculation() {
		
	}
	
	public double getWheelAngleDegrees(int encoderTicks, int encoderTicksPerRotation) {
		
		wheelRotationsDecimal = (double) encoderTicks / (encoderTicksPerRotation / 2);
		
		wheelRotations = (int)Math.floor(wheelRotationsDecimal);
		
		if (wheelRotations%2 == 0) {
			
			wheelAngleOperationFlip = false;
		}else {
			
			wheelAngleOperationFlip = true;
		}
		
		if(wheelAngleOperationFlip == false) {
			
			wheelAngle = (wheelRotationsDecimal - wheelRotations) * 180;
		}else {
			
			wheelAngle = -180 + (wheelRotationsDecimal - wheelRotations) * 180;
		}
		
		return wheelAngle;
	}
}
