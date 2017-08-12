package org.usfirst.frc.team4201.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends Subsystem{
	
	public Dashboard(){
		
	}
	
	public void writeLog(String message){
		SmartDashboard.putString("Console", message);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
