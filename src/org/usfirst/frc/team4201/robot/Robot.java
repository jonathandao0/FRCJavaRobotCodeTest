
package org.usfirst.frc.team4201.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.usfirst.frc.team4201.robot.commands.*;
import org.usfirst.frc.team4201.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Joystick leftJoystick;
	Joystick rightJoystick;

	public static DriveTrain driveTrain;
	public static SallySweeper sallySweeper;
	public static Shooter shooter;
	public static Arm arm;
	public static PowerDistributionPanel PDP;
	public static Dashboard DS;
	public static OI oi;
		
	Command autonomousCommand;
	SendableChooser<Command> autonomousModes;
	
	NetworkTable table;
	public static FileWriter debugStream;
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	int autoLoopCounter = 0;
	Timer stopwatch;

	public static boolean smartDashboardDebug = false;
	public Robot(){
		//table = new NetworkTables();	
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {		
		driveTrain = new DriveTrain();
		sallySweeper = new SallySweeper();
		shooter = new Shooter();
		arm = new Arm();
		PDP = new PowerDistributionPanel();
		
		DS = new Dashboard();
		
		oi = new OI();
		autonomousModes = new SendableChooser<Command>();
		
		// Set this as default due to issues with dashboard
		//autonomousModes.addDefault("Do Nothing", new AutoDoNothing());
		autonomousModes.addDefault("Drive Forward", new AutoDriveForward());
		//autonomousModes.addObject(name, object);
		
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData("Auto Mode Select", autonomousModes);
		SmartDashboard.putData(driveTrain);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		writeLog("autoInit");
		
		autonomousCommand = autonomousModes.getSelected();
		if(autonomousCommand != null)
			autonomousCommand.start();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		arm.updateSmartDashboard();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		writeLog("teleopInit");
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		arm.updateSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public static void writeLog(String message) {
		if (debugStream == null) return;
		try {
			debugStream.write(format.format(new Date()) + ": " + message + "\n");
			debugStream.flush();
		} catch (IOException e) {
		}
	}
}
