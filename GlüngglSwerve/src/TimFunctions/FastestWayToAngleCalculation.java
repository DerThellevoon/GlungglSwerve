package TimFunctions;

public class FastestWayToAngleCalculation {

	private double wheelAngleForward = 0;
	private double wheelAngleBackward = 0;
	private double desiredAngleForward = 0;
	private double desiredAngleBackward = 0;
	private double distanceNoReverseForward = 0;
	private double distanceNoReverseBackward = 0;
	private double distanceReverseForward = 0;
	private double distanceReverseBackward = 0;
	private boolean distanceDirection = false;
	private double distanceClockwiseForward = 0;
	private double distanceClockwiseBackward = 0;
	private double distanceCounterClockwiseForward = 0;
	private double distanceCounterClockwiseBackward = 0;
	private double wheelDirection = 0;
	private boolean rotationDirection = false;
	private double rotationDistance = 0;
	
	public FastestWayToAngleCalculation() {
		
	}
	
	public void calculateFastestWayToAngle(double currentAngle, double desiredAngle) {
		
		wheelAngleForward = currentAngle;
		desiredAngleForward = desiredAngle;
		
		if(wheelAngleForward + 180 > 180) {
			wheelAngleBackward = wheelAngleForward - 180;
		}else {
			wheelAngleBackward = wheelAngleForward + 180;
		}
		
		if(desiredAngleForward + 180 > 180) {
			desiredAngleBackward = desiredAngleForward - 180;
		}else {
			desiredAngleBackward = desiredAngleForward + 180;
		}
		
		if(wheelAngleForward - desiredAngleForward < 0) {
			distanceNoReverseForward = desiredAngleForward - wheelAngleForward;
		}else {
			distanceNoReverseForward = wheelAngleForward - desiredAngleForward;
		}
		
		if(wheelAngleBackward - desiredAngleBackward < 0) {
			distanceNoReverseBackward = desiredAngleBackward - wheelAngleBackward;
		}else {
			distanceNoReverseBackward = wheelAngleBackward - desiredAngleBackward;
		}
		
		if(wheelAngleBackward - desiredAngleForward < 0) {
			distanceReverseForward = desiredAngleForward - wheelAngleBackward;
		}else {
			distanceReverseForward = wheelAngleBackward - desiredAngleForward;
		}
		
		if(wheelAngleForward - desiredAngleBackward < 0) {
			distanceReverseBackward = desiredAngleBackward - wheelAngleForward;
		}else {
			distanceReverseBackward = wheelAngleForward - desiredAngleBackward;
		}
		
		if(distanceNoReverseForward <= distanceNoReverseBackward && distanceReverseForward <= distanceReverseBackward) {
			distanceDirection = true;
		}else {
			distanceDirection = false;
		}
		
		if(distanceDirection == true) {
			if(distanceNoReverseForward < distanceReverseForward) {
				wheelDirection = 1;
			}else {
				wheelDirection = -1;
			}
		}else {
			if(distanceNoReverseBackward <= distanceReverseBackward) {
				wheelDirection = 1;
			}else {
				wheelDirection = -1;
			}
		}
		
		if(desiredAngleForward < wheelAngleForward) {
			distanceClockwiseForward = desiredAngleForward + 360 - wheelAngleForward;
		}else {
			distanceClockwiseForward = desiredAngleForward - wheelAngleForward;
		}
		
		if(desiredAngleBackward < wheelAngleForward) {
			distanceClockwiseBackward = desiredAngleBackward + 360 - wheelAngleForward;
		}else {
			distanceClockwiseBackward = desiredAngleBackward - wheelAngleForward;
		}
		
		if(wheelAngleForward < desiredAngleForward) {
			distanceCounterClockwiseForward = wheelAngleForward + 360 - desiredAngleForward;
		}else {
			distanceCounterClockwiseForward = wheelAngleForward - desiredAngleForward;
		}
		
		if(wheelAngleForward < desiredAngleBackward) {
			distanceCounterClockwiseBackward = wheelAngleForward + 360 - desiredAngleBackward;
		}else {
			distanceCounterClockwiseBackward = wheelAngleForward - desiredAngleBackward;
		}
		
		if(distanceClockwiseForward < distanceCounterClockwiseForward && distanceClockwiseForward < distanceCounterClockwiseBackward) {
				rotationDirection = true;		
		}else {
			if(distanceClockwiseBackward < distanceCounterClockwiseForward && distanceClockwiseBackward < distanceCounterClockwiseBackward) {
				rotationDirection = true;
			}else {
				rotationDirection = false;
			}
		}
		
		if(wheelDirection == 1) {
			if(distanceDirection == true) {
				rotationDistance = distanceNoReverseForward;
			}else {
				rotationDistance = distanceNoReverseBackward;
			}
		}else {
			if(distanceDirection == true) {
				rotationDistance = distanceReverseForward;
			}else {
				rotationDistance = distanceReverseBackward;
			}
		}
	}
	
	public double getWheelDirection() {
		return wheelDirection;
	}

	public boolean getRotationDirection() {
		return rotationDirection;
	}

	public double getRotationDistance() {
		return rotationDistance;
	}
}