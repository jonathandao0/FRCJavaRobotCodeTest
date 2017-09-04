package org.usfirst.frc.team4201.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4201.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public Joystick leftJoystick = new Joystick(RobotMap.leftJoystick);
	public Joystick rightJoystick = new Joystick(RobotMap.rightJoystick);
	
	public OI(){	
		//SmartDashboard.putData("DriveWithJoysticks", new DriveWithJoysticks());
        //SmartDashboard.putData("Drive: All Stop", new DriveStop());
		Button[] leftJoyButtons = new Button[12];
		Button[] rightJoyButtons = new Button[12];
		
		
		// Note: Button index starts at 1. Will get console error otherwise.
		for(int i = 1; i < leftJoyButtons.length; i++){
			leftJoyButtons[i] = new JoystickButton(leftJoystick, i);
			rightJoyButtons[i] = new JoystickButton(rightJoystick, i);
		}
		
		leftJoyButtons[6].whenPressed(new SallySweeperOut());
		leftJoyButtons[4].whenPressed(new SallySweeperIn());
			
		rightJoyButtons[2].whileHeld(new FeederIntake());
		rightJoyButtons[4].whenPressed(new FeederDeploy());
		rightJoyButtons[6].whenPressed(new FeederRetract());
		
		rightJoyButtons[3].whenPressed(new PuncherOut());
		rightJoyButtons[5].whenPressed(new PuncherIn());
		
		//rightJoyButtons[3].whenPressed(new ArmTest());
		
		SmartDashboard.putNumber("Set Arm Angle", 0);
		SmartDashboard.putData("Set Arm Angle Button", new SetArmByDashboard());
		SmartDashboard.putData("Zero Arm Angle", new ZeroArmAngle());
		
		if (Robot.smartDashboardDebug) {
        	//setupSmartDashboardDebug();
        }
	}
}
