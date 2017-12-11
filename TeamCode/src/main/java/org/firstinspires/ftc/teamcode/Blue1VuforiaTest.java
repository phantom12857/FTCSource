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

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Blue 1: vuMark", group="Pushbot")
//@Disabled
public class Blue1VuforiaTest extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    /* Declare OpMode members. */
    PhantomConfig robot   = new PhantomConfig();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
    @Override
    public void runOpMode() {

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

                //time/distance for left column
                keyColumn = 1;

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {

                //time/distance for center column
                keyColumn = 2;

            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {

                //time/distance for right column
                keyColumn = 3;

            }

        }

        //STRAIGHT (distance with key column)
        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        runtime.reset();//
        while (opModeIsActive() && runtime.seconds() < keyColumn) {
            telemetry.addData("Forward", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //left
        robot.leftMotor.setPower(-TURN_SPEED);
        robot.rightMotor.setPower(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Right Turn", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //straight
        robot.rightMotor.setPower(FORWARD_SPEED);
        robot.leftMotor.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.45)){
            telemetry.addData("Forward", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

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
}
