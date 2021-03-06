package org.usfirst.frc.team4201.robot.commands;

import org.usfirst.frc.team4201.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SallySweeperOut extends Command {

	/**
	 * Actuates the Sally Sweeper Out
	 */
    public SallySweeperOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.sallySweeper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!Robot.sallySweeper.isSallySweeperOut())
    		Robot.sallySweeper.sallySweeperOut();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
