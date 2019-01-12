package TimFunctions;

public class TalonSteerOutputCalculation {
	
	public double rampValue = 0;
	public int desiredEncoderTicks = 0;
	public int encoderTicksDifference = 0;
	public double talonOutput = 0;

	public TalonSteerOutputCalculation() {
		
	}
	
	public double getTalonSteerOutput(int encoderTicks, int encoderTicksPerRotation, double rotationDistance, boolean rotationDirection) {
		
		rampValue = encoderTicksPerRotation / 360 * 20;
		
		if(rotationDistance != 0) {
			if(rotationDirection == true) {
				
				desiredEncoderTicks = (int) (encoderTicks + Math.round(encoderTicksPerRotation / 360 * rotationDistance));
				encoderTicksDifference = desiredEncoderTicks - encoderTicks;
				
				if(encoderTicksDifference < rampValue) {
					
					talonOutput = MapFunction.map(encoderTicksDifference, 0, rampValue, 0, 1);
				}else {
					
					talonOutput = 1;
				}
			}else {
				
				desiredEncoderTicks = (int) (encoderTicks - Math.round(encoderTicksPerRotation / 360 * rotationDistance));
				encoderTicksDifference = desiredEncoderTicks - encoderTicks;

				if(encoderTicksDifference > -rampValue) {
					
					talonOutput = MapFunction.map(encoderTicksDifference, 0, -rampValue, 0, -1);
				}else {
					
					talonOutput = -1;
				}
			}						
		}else {
			talonOutput = 0;
		}
		
		return talonOutput;
	}
}
