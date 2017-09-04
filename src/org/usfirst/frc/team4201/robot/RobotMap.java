package org.usfirst.frc.team4201.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int leftJoystick = 0;
	public static final int rightJoystick = 1;
	
	public static final int PCMOne = 0;
	public static final int PCMTwo = 21;
	
	public static final int driveTrainRightRear = 1;
	public static final int driveTrainRightCenter = 2;
	public static final int driveTrainRightFront = 3;
	public static final int driveTrainLeftFront = 10;
	public static final int driveTrainLeftCenter = 9; 	// 11	
	public static final int driveTrainLeftRear = 11; 	// 12
	
	public static final int shooterOne = 6;
	public static final int shooterTwo = 5;
	public static final int servoLeft = 0;
	public static final int servoRight = 1;
	public static final int ballLimitSwitchOne = 8;
	public static final int ballLimitSwitchTwo = 9;
	public static final int feederMotorOne = 7;
	public static final int feederMotorTwo = 13;
	public static final int feederSolenoidOne = 2;
	public static final int feederSolenoidTwo = 3;
	
	public static final int piBarOne = 4;
	public static final int piBarTwo = 8;
	public static final int puncher = 5; //
	
	public static final int sallySweeper = 7;
}
