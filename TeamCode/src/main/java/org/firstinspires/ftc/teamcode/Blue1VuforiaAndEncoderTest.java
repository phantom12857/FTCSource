
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

    VuforiaLocalizer vuforia;

    /* Declare OpMode members. */
    PhantomConfig robot   = new PhantomConfig();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();
 

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                                  (WHEEL_DIAMETER_INCHES * 3.1415);


    @Override

    public void runOpMode() {
        robot.runEncoder();

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

        waitForStart();

        relicTrackables.activate();
        int keyDistance = 0;


        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 5 && keyDistance == 0) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();
            }
            if (vuMark == RelicRecoveryVuMark.LEFT) {

                //DISTANCE FOR LEFT COLUMN
                keyDistance = 30;

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {

                //DISTANCE FOR CENTER COLUMN
                keyDistance = 40;

            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {

                //DISTANCE FOR RIGHT COLUMN
                keyDistance = 50;

            }

        }

        //STRAIGHT (distance with key column)
        robot.encoderDrive(robot.FORWARD_SPEED, keyDistance, keyDistance, 0);
        telemetry.addData("Forward", keyDistance);
        telemetry.update();

        //left
        robot.encoderDrive(robot.TURN_SPEED, -12, 12, 0);
        telemetry.addData("Right Turn", "Leg 2: %2.5f S Elapsed", runtime.seconds());
        telemetry.update();

        //straight
        robot.encoderDrive(robot.FORWARD_SPEED, 24, 24, 0);
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
        robot.stopRobot();
        robot.leftClaw.setPosition(0.0);
        robot.rightClaw.setPosition(0.0);
        telemetry.addData("In Safe Zone", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
