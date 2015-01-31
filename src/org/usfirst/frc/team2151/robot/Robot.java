package org.usfirst.frc.team2151.robot;


import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
//to change the motor power: find gamePad.getRawAxis and edit the multiplying value.
//do this for all getRawAxis values. Be consistent!

public class Robot extends SampleRobot {
    RobotDrive Tier1;//The first set of Talons.
    RobotDrive Tier2;//The second set of Talons.
    Joystick gamePad; //our gamepad! wooo.
    Relay relayArms; //our arms lifting mech
    Relay relayGrab; //the grabby bit
    DigitalInput limitSwitch1;
    DigitalInput limitSwitch2;
    
    public Robot() {
        Tier1 = new RobotDrive(0, 2);
        Tier1.setExpiration(0.1);
        Tier2 = new RobotDrive(1, 3);
        Tier2.setExpiration(0.1);
        relayArms = new Relay(0);
        gamePad = new Joystick(0); //init code
        limitSwitch1 = new DigitalInput(0);
        limitSwitch2 = new DigitalInput(1);
        
        
    }

    
      public void operatorControl() {
        Tier1.setSafetyEnabled(true);
        Tier2.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
        	boolean buttonLower = gamePad.getRawButton(6); //for lowering the arms
        	boolean buttonRaise = gamePad.getRawButton(5); //or raising them    	
        	double leftSide = gamePad.getRawAxis(1) * .5; //50% power
        	double rightSide = gamePad.getRawAxis(2) * .5; //otherwise we move insanely too fast
            	
        	/*
        	if (leftSide < 0)
            	leftSide = leftSide * leftSide * -1; //Exponential increases, we need If/Then logic here
            else
           		leftSide = leftSide *leftSide;
            if (rightSide < 0)
           		rightSide = rightSide * rightSide * -1; //Multiply by -1 to make the value negative 
            else            //if the input was negative, since squaring
            	rightSide = rightSide * rightSide;      //a negative will always make a positive and
        	
        	Above is a motor curve logic bit. Uncomment if you wish to test it.
        	*/
        	
        	if (limitSwitch1.get()){
        		System.out.println("Switch 1 pressed.");
        	}
        	if (limitSwitch2.get()){
        		System.out.println("Switch 2 pressed.");
        	}
        	while (buttonLower) { //entering loops for raising and lowering arms
        		relayArms.set(Relay.Value.kForward);
        		leftSide = gamePad.getRawAxis(1) * .50;
            	rightSide = gamePad.getRawAxis(2) * .50;
            	Tier1.arcadeDrive(-leftSide, -rightSide);
            	Tier2.arcadeDrive(-leftSide, -rightSide);
            	buttonLower = gamePad.getRawButton(6);
            	Timer.delay(0.001);
        	}
        	
        	while (buttonRaise) {
        		relayArms.set(Relay.Value.kReverse); //or raise them
            	leftSide = gamePad.getRawAxis(1) * .50; 
            	rightSide = gamePad.getRawAxis(2) * .50;
            	Tier1.arcadeDrive(-leftSide, -rightSide); 
            	Tier2.arcadeDrive(-leftSide, -rightSide);
            	buttonRaise = gamePad.getRawButton(5); 
            	Timer.delay(0.001);
        	}
			
        	
        	
        	relayArms.set(Relay.Value.kOff); //we were missing these commands and got very confused when the relays stuck on
        	Tier1.arcadeDrive(-leftSide, -rightSide); //negated because it's backwards
        	Tier2.arcadeDrive(-leftSide, -rightSide);
            Timer.delay(0.001);		//1ms delay for very fast updating (now watch as we run out of memory)
        }
    }
}
   


	