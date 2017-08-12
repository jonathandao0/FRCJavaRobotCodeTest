package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.Robot;
import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem{
	
	double minPosition = 0;
	double minAngle = 105;
	double maxAngle = 0;
	double anglesPerPos = 32.2667;
	double angleTolerance = 1;
	
	CANTalon[] armMotors = new CANTalon[] {
		new CANTalon(RobotMap.piBarOne),
		new CANTalon(RobotMap.piBarTwo)
	};
	
	Solenoid puncher = new Solenoid(RobotMap.PCMTwo, RobotMap.puncher);
	
	public Arm(){
		super();
		//armMotors[0].setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		armMotors[0].changeControlMode(TalonControlMode.Position);
		armMotors[0].configPeakOutputVoltage(+5.0f, -5.0f);	// need to verify
		armMotors[1].changeControlMode(TalonControlMode.Follower);
		armMotors[1].set(armMotors[0].getDeviceID());
	}
	
	public double convertPosToAngle(double position){
		return minAngle + (position/anglesPerPos);
	}
	
	public void setArmAngleByDashboard(double angle){
		double position = -((minAngle - angle) * anglesPerPos);
		armMotors[0].clearIAccum();
		armMotors[0].set(position);
		armMotors[0].enableControl();
	}
	
	public void zeroArmAngle(){
		armMotors[0].clearIAccum();
		armMotors[0].setPosition(0);
		armMotors[0].enableControl();
	}
	
	public int index = 0;
	double[] test = new double[9999];
	
	public boolean armMoveIsFinished(){
		test[index] = Math.abs(convertPosToAngle(armMotors[0].get()) - SmartDashboard.getNumber("Set Arm Angle", 105));
		if(test[index] <= angleTolerance){
			String message = "Angles(" + index + "):\n";
			for(int i = 0; i < index; i++)
				message += test[i] + "\n";
			Robot.DS.writeLog(message);
			return true;
		}
		index++;
		return false;
	}
	
	public void armTest(){
		armMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		armMotors[0].set(0.1);	
	}
	
	public void extendPuncher(){
		puncher.set(true);
	}
	
	public void retractPuncher(){
		puncher.set(false);
	}
	
	public boolean getPuncherStatus(){
		return puncher.get();
	}
	
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Arm Position", armMotors[0].get());
		SmartDashboard.putNumber("Arm Angle", convertPosToAngle(armMotors[0].get()));
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
