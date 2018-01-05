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

@Autonomous(name="Blue 1: Auto Drive By Time", group="Pushbot")
//@Disabled
public class Blue1PhantomAuto extends LinearOpMode {

    /* Declare OpMode members. */
    PhantomConfig robot   = new PhantomConfig();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    @Override

    public void runOpMode() {
        robot.init(hardwareMap);
        sleep(1000);
        // Move the arm up
        robot.armMotor.setPower(.45);
        sleep(1250);
        //Stop arm movement
        robot.armMotor.setPower(0);
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        waitForStart();
        robot.jewellArm.setPosition(0.5);
        //Leg1 Go forward
        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.3)) {
            telemetry.addData("Forward", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        //Leg2 Turn Left
        robot.leftMotor.setPower(-TURN_SPEED);
        robot.rightMotor.setPower(+TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.85)) {
            telemetry.addData("Left Turn", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        //Leg 3 Move forward
        robot.rightMotor.setPower(FORWARD_SPEED);
        robot.leftMotor.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.45)) {
            telemetry.addData("Forward", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        sleep(1500);
        //Leg 4 Open the claw to drop the block off
        robot.rightClaw.setPosition(.2);
        robot.leftClaw.setPosition(0.8);
        sleep(1500);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)){
            telemetry.addData("Claw Open", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        //Leg 5 Go backward/forward to push the block in
        double repeatspeed = .15;
        for(int i = 0 ; i <=3; i++) {
            robot.rightMotor.setPower(-repeatspeed);
            robot.leftMotor.setPower(-repeatspeed);
            repeatspeed = -(0.05+repeatspeed);
            sleep(1250);
        }
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
            robot.leftClaw.setPosition(0.0);
            robot.rightClaw.setPosition(0.0);
            telemetry.addData("In Safe Zone", "Complete");
            telemetry.update();
            sleep(1000);
        }
    }
}

