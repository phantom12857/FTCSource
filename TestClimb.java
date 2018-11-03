package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TestAutonomousOld", group= "Auto")
//@Disabled
public class TestClimb extends LinearOpMode {

    /* Declare OpMode members. */
    EncoderDriveConfig       robot   = new EncoderDriveConfig();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    static final double     CLIMB_SPEED = 0.45;
    @Override
    public void runOpMode() {
        
        robot.init(hardwareMap);
        
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        
        waitForStart();
        
        //Move motor forward
        robot.climbDrive.setPower(CLIMB_SPEED);
        while (runtime.seconds() < 3.0) {
            telemetry.addData("Path", "Leg 1: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        sleep(2500);

        robot.climbDrive.setPower(-CLIMB_SPEED);
        while (runtime.seconds() < 3.0) {
            telemetry.addData("Path", "Leg 1: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }
        
        
    }
}

