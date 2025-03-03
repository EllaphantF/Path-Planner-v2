package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.auto.*;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    //Autoooooooo
    // Makes robotcontainer funky, private final PathPlannerAutos pathPlannerAutos;

    /* Controllers */
    private final Joystick driver = new Joystick(0);

    //private static AutoModes mModes = new AutoModes();
    //private AutoModeSelector mAutoModeSelector = new AutoModeSelector();

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    //private final JoystickButton reset = new JoystickButton(driver, XboxController.Button.kX.value);

    /* Subsystems */
        SwerveSubsystem s_Swerve = new SwerveSubsystem();
        SuperStructure s_SuperStructure = new SuperStructure();
        EndEffector s_EndEffector = new EndEffector();
        Arm s_Arm = new Arm();
        ArmStates s_ArmStates = new ArmStates();
        Constants s_Constants = new Constants();
        Vision s_Vision = new Vision();
    
    public static String alliance = "BLUE";
    
    enum Alliance {
      RED, BLUE
    }

    public static SendableChooser<Alliance> mAlliance;

    private final SendableChooser<Command> autoChooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        /* Subsystems

        already initialized above, but just in case
        
        SwerveSubsystem s_Swerve = new SwerveSubsystem();
        SuperStructure s_SuperStructure = new SuperStructure();
        EndEffector s_EndEffector = new EndEffector();
        Arm s_Arm = new Arm();
        ArmStates s_ArmStates = new ArmStates();
        Constants s_Constants = new Constants();
        SwerveModule s_SwerveModule = new SwerveModule();
        Vision s_Vision = new Vision(); */

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> driver.getRawAxis(translationAxis), 
                () -> driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis),
                () -> robotCentric.getAsBoolean()
            )
        );


        // Configure the button bindings
        configureButtonBindings();

        mAlliance = new SendableChooser<>();
        mAlliance.setDefaultOption("Blue", Alliance.BLUE);
        mAlliance.addOption("Red", Alliance.RED);
        SmartDashboard.putData("Alliance Color Selection", mAlliance);

        autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be `Commands.none()`
        SmartDashboard.putData("Auto Mode", autoChooser);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */

    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
       return autoChooser.getSelected(); //think it's right one but not sure
    }
}