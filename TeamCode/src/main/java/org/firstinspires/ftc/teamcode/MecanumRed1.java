package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Red 1: Auto Drive", group="Pushbot")
//@Disabled

public class MecanumRed1 extends LinearOpMode {

    /* Declare OpMode members. */
    MecanumConfig robot   = new MecanumConfig();   // Use a Pushbot's hardware

    private ElapsedTime     runtime = new ElapsedTime();

    static final double     FORWARD_SPEED = 0.25;
    static final double     TURN_SPEED    = 0.20;

    double robotWidth = 14.0;
    double turnCircumference = robotWidth * 3.14159265359;
    double rightAngleTurn    = turnCircumference / 4;
    int COUNTS_PER_MOTOR_ROTATION = 1440;
    double WHEEL_DIAMETER_INCHES = 4;
    double COUNTS_PER_INCH = COUNTS_PER_MOTOR_ROTATION / (WHEEL_DIAMETER_INCHES * 3.14159);

    @Override
//37.0
    public void runOpMode() {

        robot.init(hardwareMap);


        sleep(1000);
        encoderDrive(1,37,37,37,37,3);
        robot.ForwardLeft.setPower(1.0);
        robot.ForwardRight.setPower(-1.0);
        robot.BackwardLeft.setPower(1.0);
        robot.BackwardRight.setPower(-1.0);
        sleep(1250);
        encoderDrive(1,10,10,10,10,2);

        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        waitForStart();

            telemetry.addData("In Safe Zone", "Complete");
            telemetry.addData("rm current position", "running at %7d", robot.ForwardLeft.getCurrentPosition());
            telemetry.addData("lm current position", "running at %7d", robot.ForwardRight.getCurrentPosition());
            telemetry.addData("rm current position", "running at %7d", robot.BackwardLeft.getCurrentPosition());
            telemetry.addData("lm current position", "running at %7d", robot.BackwardRight.getCurrentPosition());
            telemetry.update();
            sleep(1000);
        }

    public void encoderDrive(double speed,
                             double ForwardLeftInches, double ForwardRightInches,double BackwardLeftInches, double BackwardRightInches,
                             double timeoutS) {
        int newForwardLeftTarget;
        int newForwardRightTarget;
        int newBackwardLeftTarget;
        int newBackwardRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            newForwardLeftTarget = robot.ForwardLeft.getCurrentPosition() + (int)(ForwardLeftInches * COUNTS_PER_INCH);
            newForwardRightTarget = robot.ForwardRight.getCurrentPosition() + (int)(ForwardRightInches * COUNTS_PER_INCH);
            newBackwardLeftTarget = robot.BackwardLeft.getCurrentPosition() + (int)(BackwardLeftInches * COUNTS_PER_INCH);
            newBackwardRightTarget = robot.BackwardRight.getCurrentPosition() + (int)(BackwardRightInches * COUNTS_PER_INCH);
            
            robot.ForwardLeft.setTargetPosition(newForwardLeftTarget);
            robot.ForwardRight.setTargetPosition(newForwardRightTarget);
            robot.BackwardLeft.setTargetPosition(newBackwardLeftTarget);
            robot.BackwardRight.setTargetPosition(newBackwardRightTarget);

            // Turn On RUN_TO_POSITION
            robot.ForwardLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.ForwardRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackwardLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BackwardRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            ElapsedTime runtime = new ElapsedTime();
            runtime.reset();
            robot.ForwardLeft.setPower(Math.abs(speed));
            robot.ForwardRight.setPower(Math.abs(speed));
            robot.BackwardLeft.setPower(Math.abs(speed));
            robot.BackwardRight.setPower(Math.abs(speed));

            runtime.reset();

            while (opModeIsActive() && robot.ForwardLeft.isBusy() && robot.ForwardRight.isBusy() && robot.BackwardLeft.isBusy() && robot.BackwardRight.isBusy() && runtime.seconds() < timeoutS) {}

            // Stop all motion;
            robot.ForwardLeft.setPower(0);
            robot.ForwardRight.setPower(0);
            robot.BackwardLeft.setPower(0);
            robot.BackwardRight.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.ForwardLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.ForwardRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BackwardLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.BackwardRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            sleep(250);   // optional pause after each move

        }
    }
}

