/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6417.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import TimFunctions.ActiveWheelAngleCalculation;
import TimFunctions.Deadzone;
import TimFunctions.DrivePowerCalculation;
import TimFunctions.FastestWayToAngleCalculation;
import TimFunctions.FieldOrientedDrive;
import TimFunctions.JoystickAngleCalculation;
import TimFunctions.SwerveCalculation;
import TimFunctions.TalonSteerOutputCalculation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {
	
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	public static SwerveCalculation swerve = new SwerveCalculation(65, 53);
	public static Deadzone deadzone = new Deadzone();
	public static FastestWayToAngleCalculation fastestWayToAngle = new FastestWayToAngleCalculation();
	public static ActiveWheelAngleCalculation activeAngleCalculationFrontLeft = new ActiveWheelAngleCalculation(), activeAngleCalculationFrontRight = new ActiveWheelAngleCalculation(), activeAngleCalculationBackLeft = new ActiveWheelAngleCalculation(), activeAngleCalculationBackRight = new ActiveWheelAngleCalculation();
	public static TalonSteerOutputCalculation talonSteerOutputCalculationFrontLeft = new TalonSteerOutputCalculation(), talonSteerOutputCalculationFrontRight = new TalonSteerOutputCalculation(), talonSteerOutputCalculationBackLeft = new TalonSteerOutputCalculation(), talonSteerOutputCalculationBackRight = new TalonSteerOutputCalculation();
	
	public static AHRS ahrs;
		
	public static final WPI_TalonSRX talonDriveFrontLeft = new WPI_TalonSRX(4);
	public static final WPI_TalonSRX talonDriveFrontRight = new WPI_TalonSRX(5);
	public static final WPI_TalonSRX talonDriveBackLeft = new WPI_TalonSRX(6);
	public static final WPI_TalonSRX talonDriveBackRight = new WPI_TalonSRX(7);
	public static final WPI_TalonSRX talonSteerFrontLeft = new WPI_TalonSRX(0);
	public static final WPI_TalonSRX talonSteerFrontRight = new WPI_TalonSRX(1);
	public static final WPI_TalonSRX talonSteerBackLeft = new WPI_TalonSRX(2);
	public static final WPI_TalonSRX talonSteerBackRight = new WPI_TalonSRX(3);
	
	
	public static final Joystick joystick1 = new Joystick(0);
	public static final JoystickButton buttonEncoderSetZero = new JoystickButton(joystick1, 6);
	public static final JoystickButton buttonFieldAngleSetZero = new JoystickButton(joystick1, 5);
	public static final JoystickButton buttonCalibrateFrontLeft = new JoystickButton(joystick1, 4);
	public static final JoystickButton buttonCalibrateFrontRight = new JoystickButton(joystick1, 3);
	public static final JoystickButton buttonCalibrateBackLeft = new JoystickButton(joystick1, 1);
	public static final JoystickButton buttonCalibrateBackRight = new JoystickButton(joystick1, 2);
	
	public static final double multiplier = 1;
	
	public static final double deadzoneValue = 0.1;
	public static final int driveRotationTickCount = 11564;
	public static final int steerRotationTickCount = 256000;
	
	public static double joystickY = 0, joystickX = 0, joystickZ = 0;
	public static double joystickAngle = 0;
	public static double joystickPower = 0;
	public static boolean buttonEncoderSetZeroState = false, buttonNavXSetZeroState = false, buttonCalibrateFrontLeftState = false, buttonCalibrateFrontRightState = false, buttonCalibrateBackLeftState = false, buttonCalibrateBackRightState = false;
	
	public static double fieldAngle = 0;
	public static double resultingAngle = 0;
	
	public static int[] encoderDriveCount = new int[4];
	public static int[] encoderSteerCount = new int[4];
	
	public static double[] wheelDirection = new double[4];
	public static boolean[] rotationDirection = new boolean[4];
	public static double[] rotationDistance = new double[4];
	
	public static double[] currentWheelAngle = new double[4];
	
	public static double[] swerveDrivePower = new double[4];
	public static double[] swerveSteerAngle = new double[4];
	
	public static double[] talonSteerOutput = new double[4];
	public static double[] talonDriveOutput = new double[4];
	
	@Override
	public void teleopInit() {
		
		try {
	        ahrs = new AHRS(SPI.Port.kMXP); 
	    } catch (RuntimeException ex ) {
	        System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
	    }
		
		talonDriveFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonSteerFrontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonDriveFrontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonSteerFrontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonDriveBackLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonSteerBackLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonDriveBackRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talonSteerBackRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		talonSteerFrontLeft.configOpenloopRamp(0, 0);
		talonSteerFrontLeft.configClosedloopRamp(0, 0);
		talonSteerFrontRight.configOpenloopRamp(0, 0);
		talonSteerFrontRight.configClosedloopRamp(0, 0);
		talonSteerBackLeft.configOpenloopRamp(0, 0);
		talonSteerBackLeft.configClosedloopRamp(0, 0);
		talonSteerBackRight.configOpenloopRamp(0, 0);
		talonSteerBackRight.configClosedloopRamp(0, 0);
		
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		joystickX = Deadzone.getAxis(joystick1.getX(), deadzoneValue);
		joystickY = Deadzone.getAxis(-joystick1.getY(), deadzoneValue);
		joystickZ = Deadzone.getAxis(-joystick1.getZ(), deadzoneValue);
		
		joystickAngle = JoystickAngleCalculation.joystickAngle(joystick1.getX(), -joystick1.getY(), deadzoneValue);
		joystickPower = DrivePowerCalculation.getDrivePower(joystick1.getX(), -joystick1.getY(), deadzoneValue);
		
		fieldAngle = ahrs.getYaw();
		resultingAngle = FieldOrientedDrive.resultingAngle(joystickAngle, fieldAngle);
		
		buttonEncoderSetZeroState = buttonEncoderSetZero.get();
		buttonNavXSetZeroState = buttonFieldAngleSetZero.get();
		buttonCalibrateFrontLeftState = buttonCalibrateFrontLeft.get();
		buttonCalibrateFrontRightState = buttonCalibrateFrontRight.get();
		buttonCalibrateBackLeftState = buttonCalibrateBackLeft.get();
		buttonCalibrateBackRightState = buttonCalibrateBackRight.get();
		
		encoderSteerCount[0] = talonSteerFrontLeft.getSelectedSensorPosition();
		encoderSteerCount[1] = -talonSteerFrontRight.getSelectedSensorPosition();
		encoderSteerCount[2] = -talonSteerBackLeft.getSelectedSensorPosition();
		encoderSteerCount[3] = -talonSteerBackRight.getSelectedSensorPosition();
		encoderDriveCount[0] = talonDriveFrontLeft.getSelectedSensorPosition();
		encoderDriveCount[1] = talonDriveFrontRight.getSelectedSensorPosition();
		encoderDriveCount[2] = talonDriveBackLeft.getSelectedSensorPosition();
		encoderDriveCount[3] = talonDriveBackRight.getSelectedSensorPosition();
		
		swerve.calculateValues(resultingAngle, joystickPower, joystickZ);
		swerveDrivePower = swerve.getDrivePower();
		swerveSteerAngle = swerve.getSteerAngle();
		
		if(buttonCalibrateFrontLeftState == true) {
			
			talonSteerFrontLeft.set(joystick1.getX());
			
		}else if(buttonCalibrateFrontRightState == true) {
			
			talonSteerFrontRight.set(joystick1.getX());
			
		}else if(buttonCalibrateBackLeftState == true) {
			
			talonSteerBackLeft.set(joystick1.getX());
			
		}else if(buttonCalibrateBackRightState == true) {
			
			talonSteerBackRight.set(joystick1.getX());
			
		}else {
						
			currentWheelAngle[0] = activeAngleCalculationFrontLeft.getWheelAngleDegrees(encoderSteerCount[0], steerRotationTickCount);
			currentWheelAngle[1] = activeAngleCalculationFrontRight.getWheelAngleDegrees(encoderSteerCount[1], steerRotationTickCount);
			currentWheelAngle[2] = activeAngleCalculationBackLeft.getWheelAngleDegrees(encoderSteerCount[2], steerRotationTickCount);
			currentWheelAngle[3] = activeAngleCalculationBackRight.getWheelAngleDegrees(encoderSteerCount[3], steerRotationTickCount);
	
			for(int i = 0; i < 4; i++) {
				fastestWayToAngle.calculateFastestWayToAngle(currentWheelAngle[i], swerveSteerAngle[i]);
				wheelDirection[i] = fastestWayToAngle.getWheelDirection();
				rotationDirection[i] = fastestWayToAngle.getRotationDirection();
				rotationDistance[i] = fastestWayToAngle.getRotationDistance();
			}
			
			talonSteerOutput[0] = talonSteerOutputCalculationFrontLeft.getTalonSteerOutput(encoderSteerCount[0], steerRotationTickCount, rotationDistance[0], rotationDirection[0]);
			talonSteerOutput[1] = talonSteerOutputCalculationFrontRight.getTalonSteerOutput(encoderSteerCount[1], steerRotationTickCount, rotationDistance[1], rotationDirection[1]);
			talonSteerOutput[2] = talonSteerOutputCalculationBackLeft.getTalonSteerOutput(encoderSteerCount[2], steerRotationTickCount, rotationDistance[2], rotationDirection[2]);
			talonSteerOutput[3] = talonSteerOutputCalculationBackRight.getTalonSteerOutput(encoderSteerCount[3], steerRotationTickCount, rotationDistance[3], rotationDirection[3]);
	
			for(int i = 0; i < 4; i++) {
				talonDriveOutput[i] = swerveDrivePower[i] * wheelDirection[i] * multiplier;
			}
				
			talonSteerFrontLeft.set(talonSteerOutput[0]);
			talonSteerFrontRight.set(talonSteerOutput[1]);
			talonSteerBackLeft.set(talonSteerOutput[2]);
			talonSteerBackRight.set(talonSteerOutput[3]);
			
			talonDriveFrontLeft.set(-talonDriveOutput[0]);
			talonDriveFrontRight.set(talonDriveOutput[1]);
			talonDriveBackLeft.set(-talonDriveOutput[2]);
			talonDriveBackRight.set(talonDriveOutput[3]);
			
		}
	
		if(buttonEncoderSetZeroState == true) {
			talonDriveFrontLeft.setSelectedSensorPosition(0);
			talonDriveFrontRight.setSelectedSensorPosition(0);
			talonDriveBackLeft.setSelectedSensorPosition(0);
			talonDriveBackRight.setSelectedSensorPosition(0);
			talonSteerFrontLeft.setSelectedSensorPosition(0);
			talonSteerFrontRight.setSelectedSensorPosition(0);
			talonSteerBackLeft.setSelectedSensorPosition(0);
			talonSteerBackRight.setSelectedSensorPosition(0);
		}
		
		if(buttonNavXSetZeroState == true) {
			ahrs.reset();		
		}
	}
}