/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="PhantomTeleOp", group="Pushbot")
//@Disabled
public class PhantomTeleOp extends OpMode
{

    /* Declare OpMode members. */
    org.firstinspires.ftc.teamcode.PhantomConfig robot       = new PhantomConfig();
//Encoder related
private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 0.5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 0.8188976 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    //Encoder related

    double          clawOffset  = 0.0 ;
    // Servo mid position
    final double    CLAW_SPEED  = 0.1 ;
    double clawOffset1 = 0.0;
    final double CLAW_SPEED1 = 0.1;
    // sets rate to move servo
 boolean opModeIsActive = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override

    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Init Done");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        opModeIsActive = true;
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left;
        double right;
        double up;
        double down;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);

        // attachments
        // Use gamepad left & right Bumpers to open and close the claw
        if(gamepad2.a){
            clawOffset1 += CLAW_SPEED1;
        }
        if(gamepad2.b){
            clawOffset1 -= CLAW_SPEED1;
        }
        if (gamepad2.right_bumper) {
            clawOffset += CLAW_SPEED;

        }
        else if (gamepad2.left_bumper) {
            clawOffset -= CLAW_SPEED;
        }
        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.4, 0.4);
        double rightposition = robot.MID_SERVO - clawOffset;
        double leftposition =  robot.MID_SERVO + clawOffset;
        clawOffset1 = Range.clip(clawOffset, -0.4, 0.4);
        double rightposition1 = robot.MID_SERVO - clawOffset1;
        double leftposition1 = robot.MID_SERVO - clawOffset1;

        robot.leftClaw.setPosition(leftposition);
        robot.rightClaw.setPosition(rightposition);

     //Use gamepad2 right stick up and down for moving the arm up and down
        if( gamepad2.dpad_up)
            encoderDrive(DRIVE_SPEED,  7,5.0);  // S1: Forward 47 Inches with 5 Sec timeout
            //robot.armMotor.setPower(robot.ARM_UP_POWER);
        else
            robot.armMotor.setPower(0.0);
        if(gamepad2.dpad_down)
            encoderDrive(DRIVE_SPEED, -7, 5.0);
        else
            robot.armMotor.setPower(0.0);
        if(gamepad2.dpad_left)
            encoderDrive(DRIVE_SPEED, 3.5,5.0);
        else
            robot.armMotor.setPower(0.0);
        if(gamepad2.dpad_right)
            encoderDrive(DRIVE_SPEED, -3.5, 5.0);
        else
            robot.armMotor.setPower(0.0);
            // Send telemetry message to signify robot running;
        telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("leftpos",  "%.2f", leftposition);
        telemetry.addData("rightpos", "%.2f", rightposition);
    }

    public void encoderDrive(double speed,
                             double displacement,
                             double timeoutS) {
        int newdisplacement;
      //  int newRightTarget;

        // Ensure that the opmode is still active
       if (opModeIsActive) {

            // Determine new target position, and pass to motor controller
            newdisplacement = robot.armMotor.getCurrentPosition() + (int)(displacement * COUNTS_PER_INCH);
          //  newRightTarget = robot.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.armMotor.setTargetPosition(newdisplacement);
          //  robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          //  robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.armMotor.setPower(Math.abs(speed));
         //   robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (//opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.armMotor.isBusy() )) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d", newdisplacement);
                telemetry.addData("Path2",  "Running at %7d ",
                        robot.armMotor.getCurrentPosition());
                       // robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.armMotor.setPower(0);
           // robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           // robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
       }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        opModeIsActive = false;
    }

}
