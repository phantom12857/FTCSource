package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="TestAutonomousOld", group= "Auto")
//@Disabled2
public class AutoRed2 extends LinearOpMode {

    /* Declare OpMode members. */
    PhantomConfig        robot   = new PhantomConfig();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    static final double     TURN_SPEED1 = 0.2;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);


        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        waitForStart();

        // Step 1:  Drive backward for 3 seconds
        robot.leftDrive.setPower(-FORWARD_SPEED);
        robot.rightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        // Step 2: Slightly turn left for 5 seconds
        robot.leftDrive.setPower(TURN_SPEED1);
        robot.rightDrive.setPower(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 5.0)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        // Step 3: Go forward for 4 seconds
        robot.leftDrive.setPower(TURN_SPEED);
        robot.rightDrive.setPower(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 4.0)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
}

