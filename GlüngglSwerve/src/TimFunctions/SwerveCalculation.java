package TimFunctions;

public class SwerveCalculation {

	private double[] rotationAngle = new double[4];
	
	private double[] a1 = new double[4];
	private double[] a2 = new double[4];
	private double[] a = new double[4];
	private double[] b1 = new double[4];
	private double[] b2 = new double[4];
	private double[] b = new double[4];
	private double[] drivePowerRaw = new double[4];
	private double drivePowerRawMax = 0;
	private double drivePowerRawMin = 0;
	
	private double[] drivePower = new double[4];
	private double[] steerAngle = new double[4];
	
	
	public SwerveCalculation(double robotLength, double robotWidth) {
		
		rotationAngle[0] = -1 * (90 + Math.toDegrees(Math.atan(robotWidth / robotLength)));
		rotationAngle[1] = -1 * (90 - Math.toDegrees(Math.atan(robotWidth / robotLength)));
		rotationAngle[2] = 90 + Math.toDegrees(Math.atan(robotWidth / robotLength));
		rotationAngle[3] = 90 - Math.toDegrees(Math.atan(robotWidth / robotLength));
	}
	
	
	public void calculateValues(double robotAngle, double robotPower, double turnPower) {
		
		drivePowerRawMax = 0;
		drivePowerRawMin = 0;
		
		for(int i = 0; i < 4; i++) {
			
			a1[i] = turnPower * Math.sin(Math.toRadians(rotationAngle[i]));
			a2[i] = robotPower * Math.sin(Math.toRadians(robotAngle));
			a[i] = a1[i] + a2[i];
			b1[i] = turnPower * Math.cos(Math.toRadians(rotationAngle[i]));
			b2[i] = robotPower * Math.cos(Math.toRadians(robotAngle));
			b[i] = b1[i] + b2[i];
			
			if(a[i] == 0 && b[i] == 0) {
				steerAngle[i] = 0;
			}else {
				steerAngle[i] = Math.toDegrees(Math.atan(a[i] / b[i]));
			}
			
			if(steerAngle[i] == 0) {
				drivePowerRaw[i] = b[i] / Math.cos(Math.toRadians(steerAngle[i]));
			}else {
				drivePowerRaw[i] = a[i] / Math.sin(Math.toRadians(steerAngle[i]));
			}
			
			if(drivePowerRaw[i] > drivePowerRawMax) {
				drivePowerRawMax = drivePowerRaw[i];
			}
			
			if(drivePowerRaw[i] < drivePowerRawMin) {
				drivePowerRawMin = drivePowerRaw[i];
			}
		}
		
		if(drivePowerRawMax < drivePowerRawMin * -1) {
			drivePowerRawMax = drivePowerRawMin;
		}
		
		for(int i = 0; i < 4; i++) {
			
			if(drivePowerRawMax > 1) {
				drivePower[i] = MapFunction.map(drivePowerRaw[i], 0, drivePowerRawMax, 0, 1);
			}else if(drivePowerRawMax < -1) {
				drivePower[i] = MapFunction.map(drivePowerRaw[i], 0, drivePowerRawMax, 0, -1);
			}else {
				drivePower[i] = drivePowerRaw[i];
			}
		}
		
	}


	public double[] getDrivePower() {
		return drivePower;
	}

	public double[] getSteerAngle() {
		return steerAngle;
	}
}
