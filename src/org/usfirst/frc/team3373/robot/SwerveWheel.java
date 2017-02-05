package org.usfirst.frc.team3373.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import java.util.*;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveWheel {
	
	CANTalon rotateMotor;
	Talon driveMotor;
	private double targetAngle;
	private double speed;
	private static int encoderUnitsPerRotation = 855;//Maximum units, not actually the range of units... 855 is the range. Maybe change it?
	private double speedModifier = .5;
	private int encOffset;
	double rotateAngle;
	
/*	public static void main(String [] args){

		targetAngle = 10;
		System.out.println("Test 1: " + calculateTargetEncoder());
		targetAngle = 20;
		System.out.println("Test 2: " + calculateTargetEncoder());
		targetAngle = 30;
		System.out.println("Test 3: " + calculateTargetEncoder());
		targetAngle = 40;
		System.out.println("Test 4: " + calculateTargetEncoder());
		targetAngle = 50;
		System.out.println("Test 5: " + calculateTargetEncoder());
		targetAngle = 60;
		System.out.println("Test 6: " + calculateTargetEncoder());
		targetAngle = 70;
		System.out.println("Test 7: " + calculateTargetEncoder());
		targetAngle = 80;
		System.out.println("Test 8: " + calculateTargetEncoder());
		targetAngle = 90;
		System.out.println("Test 9: " + calculateTargetEncoder());
		targetAngle = 100;
		System.out.println("Test 10: " + calculateTargetEncoder());
		targetAngle = 110;
		System.out.println("Test 11: " + calculateTargetEncoder());
		targetAngle = 120;
		System.out.println("Test 12: " + calculateTargetEncoder());
		targetAngle = 130;
		System.out.println("Test 13: " + calculateTargetEncoder());
		targetAngle = 140;
		System.out.println("Test 14: " + calculateTargetEncoder());
		targetAngle = 150;
		System.out.println("Test 15: " + calculateTargetEncoder());
		targetAngle = 160;
		System.out.println("Test 16: " + calculateTargetEncoder());
		targetAngle = 170;
		System.out.println("Test 17: " + calculateTargetEncoder());
		targetAngle = 180;
		System.out.println("Test 18: " + calculateTargetEncoder());
		targetAngle = 190;
		System.out.println("Test 19: " + calculateTargetEncoder());
		targetAngle = 200;
		System.out.println("Test 20: " + calculateTargetEncoder());
		targetAngle = 210;
		System.out.println("Test 21: " + calculateTargetEncoder());
		targetAngle = 220;
		System.out.println("Test 22: " + calculateTargetEncoder());
	}*/
	public SwerveWheel(int driveMotorChannel, int rotateMotorID, double p, double i, double d, double rotateAng, int distanceFromZero, int encoderOffset){
	//pushing
		rotateMotor = new CANTalon(rotateMotorID);
		driveMotor = new Talon(driveMotorChannel);
		encOffset = encoderOffset;
		
		rotateMotor.setPID(p, i, d);
		rotateMotor.changeControlMode(TalonControlMode.Position);
		rotateMotor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogEncoder);
		rotateMotor.enableLimitSwitch(false, false);
		rotateMotor.enableBrakeMode(true); 
		rotateAngle = rotateAng;
		
	}
	public int calculateTargetAngle(double x, double y){
		int angle = (int) Math.toDegrees(Math.atan2(y,-x));
		if(angle <= 0){
			angle += 360;
		}
		return angle;
		
	}
	
	public int calculateTargetEncoder(){ //Actually used! This method calculates the target encoder position, fed directly into the set() method in rotate(), accounting for offset as a result of the encoder zero value.
		double targetEnc = targetAngle;
		targetEnc = targetEnc/360;
		targetEnc = targetEnc * encoderUnitsPerRotation;
		System.out.println("TargetEnc 1 " + targetEnc);
			targetEnc += encOffset;
			System.out.println("TargetEnc 2 " + targetEnc);
			targetEnc = targetEnc % 870;
			System.out.println("TargetEnc 3 " + targetEnc);
		if(targetEnc < 15){
			targetEnc = 15;
		}
		System.out.println("Target Encoder: " + targetEnc);
		int finalityal = (int) targetEnc;
		return finalityal;
	}
	
	public void setTargetAngle(double TA){
		targetAngle = TA;
	}
	public double getTargetAngle() {
		return targetAngle;
	}
	public void setSpeed(double speedIn){
		speed = speedIn;
	}
	public double getSpeed(){
		return speed;
	}
	public int angleToEncoderUnit(double angle) {

		double deltaEncoder;
		deltaEncoder = angle * (encoderUnitsPerRotation / 360.0) + 15;
		System.out.println((int) deltaEncoder);
		System.out.println("Analog" + rotateMotor.getAnalogInRaw());

		return (int) deltaEncoder;
	}
	public int encoderUnitToAngle(int encoderValue) {
		double angle = 0;
		if (encoderValue >= 0) {
			angle = (encoderValue * (360.0 / encoderUnitsPerRotation));
			System.out.println("angle it thinks its at" + " " + angle);
			angle = angle % 360;
			System.out.println("mod 360 angle it thinks its at" + " " + angle);
		} else if (encoderValue < 0) {
			angle = (encoderValue * (360.0 / encoderUnitsPerRotation));
			System.out.println("angle it thinks its at" + " " + angle);
			angle = angle % 360 + 360;
			System.out.println("mod 360 angle it thinks its at" + " " + angle);
		}
		return (int) angle;// (angle+2*(90-angle));
	}
	public void setSpeedModifier(double speed) {
		speedModifier = speed;
	}
	public void disable() {
		rotateMotor.set(0);
	}
	public double getCurrentAngle(){
		double currentAngle = (rotateMotor.getAnalogInRaw() - encOffset);
		if(currentAngle <= 0){
			currentAngle += 870;
		}
		currentAngle = currentAngle / (encoderUnitsPerRotation / 360.0);
		return currentAngle;
	}
	public double calculateDriveSpeed(double x, double y){
		double speed = 0;
		double square = (x*x) + (y*y);
		speed = Math.sqrt(square);
		return speed;
	}
	public void drive(double x, double y) {
		/*
		 * if(Math.abs(targetAngle-currentAngle) > 2)
		 * driveMotor.set(speed*speedModifier); else{
		 * driveMotor.set(-speed*speedModifier); }
		 */
		SmartDashboard.putNumber("Speed: " + this.toString(), speed * speedModifier);
		driveMotor.set(calculateDriveSpeed(x, y) * speedModifier);// *directionalModifier
	}
	public void driveWheel() {
		driveMotor.set(speed * speedModifier);// *directionalModifier
	}
	public int encoderCheck(int targetEncoder){
		int target = 0;
		target = targetEncoder;
		System.out.println("TargetEnc 1 " + target);
		//	target += encOffset;
			System.out.println("TargetEnc 2 " + target);
			target = target % 870;
			System.out.println("TargetEnc 3 " + target);
		if(target < 15){
			target = 15;
		}
		return target;
	}
	public void rotate(){
		System.out.println("Target Angle: " + targetAngle);
		System.out.println("Current Angle: " + this.getCurrentAngle());
		rotateMotor.changeControlMode(TalonControlMode.Position);
		int encoderTarget = angleToEncoderUnit(getDeltaTheta()) + rotateMotor.getAnalogInRaw();
		System.out.println("DSOREGJSORJGOAEJFOAEOJWEORWEJWOEJROWJOOOOOOOOOOOOOO BOI: " + encoderTarget + this.toString());
		encoderTarget = encoderCheck(encoderTarget);
		System.out.println("ENCODONGLEDLOLOLO TagGET: " + encoderTarget);
		rotateMotor.set(encoderTarget);
	}
	public double getDeltaTheta() {
		System.out.println("getDeltaTheta");
		double deltaTheta = getTargetAngle() - getCurrentAngle();

		while ((deltaTheta < -90) || (deltaTheta > 90)) {
			if (deltaTheta > 90) {
				deltaTheta -= 180;
				speed *= -1;
			} else if (deltaTheta < -90) {
				deltaTheta += 180;
				speed *= -1;
			}
		}

		// if (deltaTheta >= 1 || deltaTheta <= -1){
		return deltaTheta;
		// } else {
		// return 0;
		// }

	}
	public double getRAngle(){
		return rotateAngle;
	}
}
