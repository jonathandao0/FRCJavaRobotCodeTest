package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;
import org.usfirst.frc.team4201.robot.commands.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	
	CANTalon[] leftMotors = new CANTalon[]{
		new CANTalon(RobotMap.driveTrainLeftFront),
		new CANTalon(RobotMap.driveTrainLeftCenter),
		new CANTalon(RobotMap.driveTrainLeftRear)		
	};
	CANTalon[] rightMotors = new CANTalon[]{
		new CANTalon(RobotMap.driveTrainRightFront),
		new CANTalon(RobotMap.driveTrainRightCenter),
		new CANTalon(RobotMap.driveTrainRightRear)		
	};
	
	RobotDrive robotDrive = new RobotDrive(leftMotors[0], leftMotors[1], rightMotors[0], rightMotors[1]);
	double leftJoyValue, prevLeftJoyValue = 0, rightJoyValue, prevRightJoyValue = 0, joyDelta, prevJoyDelta = 0;
	public DriveTrain(){
		super();
		leftMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		leftMotors[1].changeControlMode(TalonControlMode.PercentVbus);
		leftMotors[2].changeControlMode(TalonControlMode.Speed);
		rightMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		rightMotors[1].changeControlMode(TalonControlMode.PercentVbus);
		rightMotors[2].changeControlMode(TalonControlMode.Speed);
		
		// Set values for encoders (?)
		/*
		for(int i = 0; i < 3; i++){
			leftMotors[i].configNominalOutputVoltage(+0.0f, -0.0f);
			leftMotors[i].configPeakOutputVoltage(+12.0f, -12.0f);
			leftMotors[i].setVoltageRampRate(40);
			rightMotors[i].configNominalOutputVoltage(+0.0f, -0.0f); 
			rightMotors[i].configPeakOutputVoltage(+12.0f, -12.0f);  
			rightMotors[i].setVoltageRampRate(40);                   
		} //*/
		
        LiveWindow.addActuator("DriveTrain", "DriveMotorLeftFront", leftMotors[0]);
        LiveWindow.addActuator("DriveTrain", "DriveMotorLeftCenter", leftMotors[1]);
        LiveWindow.addActuator("DriveTrain", "DriveMotorLeftRear", leftMotors[2]);
        LiveWindow.addActuator("DriveTrain", "DriveMotorRightFront", rightMotors[0]);
        LiveWindow.addActuator("DriveTrain", "DriveMotorRightCenter", rightMotors[1]);
        LiveWindow.addActuator("DriveTrain", "DriveMotorRightRear", rightMotors[2]);
	}
	
	public void setDriveControlByPower() {
		// Initialize Motors for control by values.
		leftMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		leftMotors[1].changeControlMode(TalonControlMode.PercentVbus);
		rightMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		rightMotors[1].changeControlMode(TalonControlMode.PercentVbus);
        leftMotors[0].configPeakOutputVoltage(+12.0f, -12.0f);
        leftMotors[1].configPeakOutputVoltage(+12.0f, -12.0f);
        rightMotors[0].configPeakOutputVoltage(+12.0f, -12.0f);
        rightMotors[1].configPeakOutputVoltage(+12.0f, -12.0f);
        
        robotDrive.setSafetyEnabled(true);
    }
	
	public void driveStraight(double speed) {
		// Dives the robot in a straight line
		setDriveControlByPower();
		if(Math.abs(speed) > 1){
			Robot.writeLog("Error: Speed magnitude set to value > 1. Speed set to 0 instead.");
			stop();
		}
		else
			robotDrive.drive(speed, 0);
	}
	
	public void driveTurn(double speed, double curve) {
		// Turns the robot with a given speed and curve
		setDriveControlByPower();
		if(Math.abs(speed) > 1){
			Robot.writeLog("Error: Speed magnitude set to value > 1. Speed set to 0 instead.");
			stop();
		}
		else
			robotDrive.drive(speed, curve);
	}
	
	public void stop() {
		// Stops the Drive Train
		setDriveControlByPower();
		robotDrive.drive(0, 0);
	}
	
	public void driveWithJoysticks(Joystick leftStick, Joystick rightStick) {
		// Set the motors for control with Joysticks (Tank Drive)
		leftMotors[0].clearStickyFaults();
		leftMotors[1].clearStickyFaults();
        rightMotors[0].clearStickyFaults();
        rightMotors[1].clearStickyFaults();
        
        // ??? Motors are inverted?
        robotDrive.setInvertedMotor(MotorType.kFrontLeft, false);
        robotDrive.setInvertedMotor(MotorType.kFrontRight, false);
        robotDrive.setInvertedMotor(MotorType.kRearLeft, false);
        robotDrive.setInvertedMotor(MotorType.kRearRight, false);
    	robotDrive.tankDrive(Math.pow(leftStick.getAxis(AxisType.kY), 3), Math.pow(rightStick.getAxis(AxisType.kY), 3));
        //robotDrive.tankDrive(leftStick, rightStick);
    }
	
	public void sampleJoystickAxis() {
		
		
	}
	
	public void driveWithAutoPilot(Joystick leftStick, Joystick rightStick){
		double leftValue = Math.pow(leftStick.getAxis(AxisType.kY), 3);
		double rightValue = Math.pow(rightStick.getAxis(AxisType.kY), 3);
		
		if(Math.abs(leftStick.getAxis(AxisType.kY)) > 40 || Math.abs(rightStick.getAxis(AxisType.kY)) > 40) {
			
		} else {
			leftValue = Math.pow(leftStick.getAxis(AxisType.kY), 3);
			rightValue = Math.pow(rightStick.getAxis(AxisType.kY), 3);
		}
		
		robotDrive.tankDrive(leftValue, rightValue);
	}
	
	double rightAxistAdjustment(double axisValue){
		double rightOutput = axisValue;
		//if(axisValue < -0.05 && axisValue > -0.95)
			//rightOutput =;
		///else if(axisValue > 0.05 && axisValue < 0.95)
		
		return rightOutput;
			
	}
	
	double lefttAxistAdjustment(double axisValue){
		double leftOutput = axisValue;
		
		return leftOutput;
	}

	public void driveWithAdjustment(Joystick leftStick, Joystick rightStick){
		double leftAxis = Math.pow(leftStick.getAxis(AxisType.kY), 3);
		double rightAxis = Math.pow(rightStick.getAxis(AxisType.kY), 3);
		int state = 0, state1 = 0, state2 = 0;
		
		if(rightAxis <  -0.95)
			state1 = 1;
		else if(rightAxis <= -0.05)
			state1 = 2;
		else if(rightAxis <= 0.05)
			state1 = 3;
		else if(rightAxis <= 0.95)
			state1 = 4;
		else
			state1 = 5;
		
		if(leftAxis <  -0.95)
			state2 = 1;
		else if(leftAxis <= -0.05)
			state2 = 2;
		else if(leftAxis <= 0.05)
			state2 = 3;
		else if(leftAxis <= 0.95)
			state2 = 4;
		else
			state2 = 5;
		
	if(state1 != 0 || state2 != 0)
		state = state1 + (state2 * 5 - 1);
		
		switch(state){
			case 7:
				//???
				break;
			case 9:
				//???
				break;
			case 17:
				//???
				break;
			case 19:
				//???
				break;
			case 6:
			case 16:
			case 18:
				//leftAxis = decreaseLeftOutpu();
				break;
			case 8:
			case 10:
			case 20:
				//leftAxis = increaseLeftOutpu();
				break;
			case 2:
			case 4:
			case 14:
				//rightAxis = decreaseRightOutput()
				break;
			case 12:
			case 22:
			case 24:
				//rightAxis = increaseRightOutput()
				break;
			case 1:
			case 3:
			case 5:
			case 11:
			case 13:
			case 15:
			case 21:
			case 23:
			case 25:
			default:
				break;
		}
		
		robotDrive.tankDrive(leftAxis, rightAxis);
	}
	
	public void differentialDrive(Joystick leftstick, Joystick rightStick) {
		if()
		
	}
	
	public void differentialAlgorithm(Joystick joystick) {
		if(joystick.)
		
	}
	
	public void updateDashboard(){
		if(prevLeftJoyValue != leftJoyValue)
			prevLeftJoyValue = leftJoyValue;
		if(prevRightJoyValue != rightJoyValue)
			prevRightJoyValue = rightJoyValue;
		
		joyDelta = leftJoyValue - rightJoyValue;
		
		if(prevJoyDelta != joyDelta)
			prevJoyDelta = joyDelta;
		
		SmartDashboard.putNumber("prevLeftJoyValue", prevLeftJoyValue);
		SmartDashboard.putNumber("leftJoyValue", leftJoyValue);
		SmartDashboard.putNumber("prevRightJoyValue", prevRightJoyValue);
		SmartDashboard.putNumber("rightJoyValue", rightJoyValue);
		SmartDashboard.putNumber("prevJoyDelta", prevJoyDelta);
		SmartDashboard.putNumber("joyDelta", joyDelta);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// Default to Joystick Control
		setDefaultCommand(new DriveWithJoysticks());
	}
}
