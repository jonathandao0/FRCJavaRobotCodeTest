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
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// Default to Joystick Control
		setDefaultCommand(new DriveWithJoysticks());
	}
}
