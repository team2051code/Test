// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with tank
 * steering and an Xbox controller.
 */
public class Robot extends TimedRobot {
  private final CANSparkMax m_RightFront = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_LeftFront = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax m_RightBack = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax m_LeftBack = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_IntakeRight = new CANSparkMax(5, MotorType.kBrushed);
  private final CANSparkMax m_IntakeLeft = new CANSparkMax(6, MotorType.kBrushed);

  private final MotorControllerGroup m_Right = new MotorControllerGroup(m_RightBack, m_RightFront);
  private final MotorControllerGroup m_Left = new MotorControllerGroup(m_LeftBack, m_LeftFront);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_Left, m_Right);
  private final XboxController m_driverController = new XboxController(0);
  //private static DoubleSolenoid solnoid;
  //private static Compressor c;
  //private static XboxController but1;


  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_Right.setInverted(true);

/* 
    solnoid = new DoubleSolenoid(4,5);
    c = new Compressor(0, PneumaticsModuleType.REVPH);
    but1 = new JoystickButton(m_driverController, 0);

    c.setClosedLoopControl(false);
  */
  }

  @Override
  public void teleopPeriodic() {
    // Drive with tank drive.
    // That means that the Y axis of the left stick moves the left side
    // of the robot forward and backward, and the Y axis of the right stick
    // moves the right side of the robot forward and backward.
    m_robotDrive.arcadeDrive(-m_driverController.getLeftY()/2, -m_driverController.getLeftX()/2);
    if(m_driverController.getBButtonPressed()){
      m_IntakeLeft.set(1.0);
      m_IntakeRight.set(-1.0);
    }
    if(m_driverController.getBButtonReleased()){
      m_IntakeLeft.set(0);
      m_IntakeRight.set(0);
    }

    if(m_driverController.getAButtonPressed()){
      m_IntakeLeft.set(-1);
      m_IntakeRight.set(1);
    }
    if(m_driverController.getAButtonReleased()){
      m_IntakeLeft.set(0);
      m_IntakeRight.set(0);
    }
  }
}
