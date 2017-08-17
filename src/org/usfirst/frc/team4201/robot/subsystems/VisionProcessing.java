package org.usfirst.frc.team4201.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionProcessing extends Subsystem{
	NetworkTable table;
	NetworkTable grip_table;
	boolean targetObtained;
	
	double[] networkTablesDefault = new double[] { -1.0 };
	
	
	public VisionProcessing(){
		table = NetworkTable.getTable("GRIP/myContoursReport");
		grip_table = NetworkTable.getTable("GRIP");
		targetObtained = false;
	}
	
	public boolean findTarget() {
		return targetObtained;
	}
	
	public void updateDashboard() {
		SmartDashboard.putNumber("centerX", table.getNumber("centerX", -1.0));
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
