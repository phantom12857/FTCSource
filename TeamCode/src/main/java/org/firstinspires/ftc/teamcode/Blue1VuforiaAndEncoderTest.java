/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Blue 1: vuMark & Encoders", group="Pushbot")
//@Disabled
public class Blue1VuforiaAndEncoderTest extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    /* Declare OpMode members. */
    PhantomConfig robot   = new PhantomConfig();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();
 

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                                  (WHEEL_DIAMETER_INCHES * 3.1415);

    
    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override

    public void runOpMode() {

        //SET MODE TO BRAKE ON STOP
        //robot.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AfE9DxH/////AAAAGcrngPAJ7E4TqYn3uMgUx3V6/3WIihTT/o/YLswntRi2cjsYSm/m3Z/qHvzyYz/qVSPSte7YI3PotVYJmXcFJ55W6oBZjfs2fL+IY5qg9Pr7VdFNSLLPkWfiUo1fPBO+Bj6b/3Mx6jJEZKBBN/+hFl7us/SLamHLzFHmqIrpXVimSaFRjLkWaYXvQSftZejBnvWSshdUeYD6zpwRhp4fzndH5BYk1Hg8vcEKN+066Wtd9B5TTOebK03VgZpKKVEhqcBLKWPnTKU1E233UCUJVypKFzG7Ua7xJ6AZSYFQ4mXEZSOYUnbqD4p1JQAfc9AixrI41EYwmONeit5La52mPF7nGT1RImiuSsEEPyiG/CWi\n";

        //CAMERA DIRECTION
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        robot.init(hardwareMap);
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        robot.leftClaw.setPosition(-0.1);
        robot.rightClaw.setPosition(-0.1);

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftMotor.getCurrentPosition();
        robot.rightMotor.getCurrentPosition();
        waitForStart();

        relicTrackables.activate();
        int keyColumn = 0;


        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 5 && keyColumn == 0) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();
            }
            if (vuMark == RelicRecoveryVuMark.LEFT) {

                keyColumn = 1;

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {

                keyColumn = 2;

            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {

                keyColumn = 3;

            }

        }

        final int columnWidth = 24;
        final int baseDistance = 36;
        final int mainDistance = baseDistance + columnWidth * keyColumn;

        //STRAIGHT (distance with key column)
        encoderDrive(FORWARD_SPEED, mainDistance, mainDistance, 0.5);
        telemetry.addData("Forward", "Leg 1: %2.5f S Elapsed", runtime.seconds());
        telemetry.update();

        //left
        encoderDrive(FORWARD_SPEED, -12, 12, 0.5);
        telemetry.addData("Right Turn", "Leg 2: %2.5f S Elapsed", runtime.seconds());
        telemetry.update();

        //straight
        encoderDrive(FORWARD_SPEED, 24, 24, 0.5);
        telemetry.addData("Forward", "Leg 4: %2.5f S Elapsed", runtime.seconds());
        telemetry.update();

        //claw open
        robot.leftClaw.setPosition(-0.4);
        robot.rightClaw.setPosition(0.4);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() <1)){
            telemetry.addData("Claw Open", "Leg 5: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //stop
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        robot.leftClaw.setPosition(0.0);
        robot.rightClaw.setPosition(0.0);
        telemetry.addData("In Safe Zone", "Complete");
        telemetry.update();
        sleep(1000);
    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.leftMotor.setTargetPosition(newLeftTarget);
            robot.rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
