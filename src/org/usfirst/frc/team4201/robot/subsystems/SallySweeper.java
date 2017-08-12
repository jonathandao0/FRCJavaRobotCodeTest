package org.usfirst.frc.team4201.robot.subsystems;

import org.usfirst.frc.team4201.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SallySweeper extends Subsystem{
	Solenoid sallySweeper = new Solenoid(RobotMap.PCMTwo, RobotMap.sallySweeper);
	
	public SallySweeper(){
		super();
	}
	
	public void sallySweeperOut(){
		sallySweeper.set(true);
	}
	
	public void sallySweeperIn(){
		sallySweeper.set(false);
	}
	
	public boolean isSallySweeperOut(){
		return sallySweeper.get();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		//setDefaultCommand();
	}
}
