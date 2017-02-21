package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	SuperJoystick tester;
	SwerveControl swerve;

	int testTimer;
	int angle;
	boolean firstRun = true;
	boolean secondRun = false;
	boolean thirdRun = false;
	boolean fourthRun = false;
	
	int LX = 0;
	int LY = 1;
	int Ltrigger = 2;
	int Rtrigger = 3;
	int RX = 4;
	int RY = 5;
	
	double robotWidth = 21.125;// TODO CALIBRATE check
	double robotLength = 33.5;// TODO CALIBRATE check
	
	int LBdriveChannel = 1;
	int LBrotateID = 2;
	int LBencOffset = 775;
	int LBEncMin = 11;
	int LBEncMax = 873;

	int LFdriveChannel = 4;
	int LFrotateID = 3;
	int LFencOffset = 729;
	int LFEncMin = 15;
	int LFEncMax = 894;
	
	int RBdriveChannel = 8;
	int RBrotateID = 7;
	int RBencOffset = 252;
	int RBEncMin = 12;
	int RBEncMax = 897;
	
	int RFdriveChannel = 6;
	int RFrotateID = 5;
	int RFencOffset = 208;
	int RFEncMin = 13;
	int RFEncMax = 962;
	
	

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		tester = new SuperJoystick(0);
		//LB, LF, RB, RF
		swerve = new SwerveControl(LBdriveChannel, LBrotateID, LBencOffset, LBEncMin, LBEncMax, LFdriveChannel, LFrotateID, LFencOffset, LFEncMin, LFEncMax, RBdriveChannel, RBrotateID, RBencOffset, RBEncMin, RBEncMax, RFdriveChannel, RFrotateID, RFencOffset, RFEncMin, RFEncMax, robotWidth, robotLength);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//swerve.drive(-tester.getRawAxis(LX), -tester.getRawAxis(LY));
		swerve.calculateSwerveControl(-tester.getRawAxis(LY), tester.getRawAxis(LX), tester.getRawAxis(RX));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testInit(){
		testTimer = 0;
		angle = -360;
	}
	public void testPeriodic() {
		testTimer++;

			if(testTimer%50 == 0){
				angle += 10;
			}
			swerve.RFWheel.setTargetAngle(angle);
			swerve.RFWheel.rotate();
			swerve.RBWheel.setTargetAngle(angle);
			swerve.RBWheel.rotate();
			swerve.LFWheel.setTargetAngle(angle);
			swerve.LFWheel.rotate();
			swerve.LBWheel.setTargetAngle(angle);
			swerve.LBWheel.rotate();
			
		
	
	
	
	
	System.out.println("Test Timer: " + testTimer);
	
	}
}

