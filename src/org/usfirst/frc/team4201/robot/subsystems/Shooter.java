package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem{
	
	CANTalon[] shooterMotors = new CANTalon[]{
		new CANTalon(RobotMap.shooterOne),	
		new CANTalon(RobotMap.shooterTwo),
	};
	
	Servo[] servos = new Servo[]{
		new Servo(RobotMap.servoLeft),
		new Servo(RobotMap.servoRight),
	};
	
	DigitalInput[] ballStop = new DigitalInput[]{
		new DigitalInput(RobotMap.ballLimitSwitchOne),
		new DigitalInput(RobotMap.ballLimitSwitchTwo)
	};
	Counter ballLimitSwitchDetector = new Counter(ballStop[0]);

	CANTalon[] feederMotors = new CANTalon[]{
		new CANTalon(RobotMap.feederMotorOne),
		new CANTalon(RobotMap.feederMotorTwo)
	};
	
	Solenoid[] feederSolenoids = new Solenoid[]{
		new Solenoid(21, RobotMap.feederSolenoidOne),
		new Solenoid(21, RobotMap.feederSolenoidTwo)
	};
	
	public Shooter(){
		super();
		shooterMotors[0].changeControlMode(TalonControlMode.Speed);
		shooterMotors[1].changeControlMode(TalonControlMode.Follower);
        shooterMotors[1].set(shooterMotors[0].getDeviceID());
		feederMotors[0].changeControlMode(TalonControlMode.PercentVbus);
		feederMotors[1].changeControlMode(TalonControlMode.PercentVbus);
	}
	
	/**
	 * 
	 * @param speed positionChange / 10 ms
	 */
	public void shooterSetOutput(double speed){
		shooterMotors[0].set(speed);
	}
	
	public void shooterDisable(){
		shooterMotors[0].set(0);
		servos[0].setAngle(0);
		servos[1].setAngle(0);
	}
	
	public void shooterSetAngle(double angle){
		servos[0].setAngle(angle);
		servos[1].setAngle(-angle);
	}
	
	public void feederIntake(){
		feederMotors[0].set(1);
		feederMotors[1].set(1);
	}
	
	public void feederIntakeReverse(){
		feederMotors[0].set(-1);
		feederMotors[1].set(-1);
	}
	
	public void feederDisable(){
		feederMotors[0].set(0);
		feederMotors[1].set(0);
	}
	
	public void feederDeploy(){
		feederSolenoids[0].set(true);
		feederSolenoids[1].set(true);
	}
	
	public void feederRetract(){
		feederSolenoids[0].set(false);
		feederSolenoids[1].set(false);
	}
	
	public boolean getFeederSolenoidStatus(){
		if(feederSolenoids[0].get() == feederSolenoids[1].get())
			return feederSolenoids[0].get();
		else {
			// Error: ?
			throw new IllegalArgumentException("Error: Solenoids are out of sync.");
		}
	}
	
	public void initializeBallLimitSwitchDetector(){
		ballLimitSwitchDetector.reset();
	}
	
	public boolean isBallSet(){
		return ballLimitSwitchDetector.get() > 0;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
