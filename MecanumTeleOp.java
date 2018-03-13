package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.internal.ftdi.eeprom.FT_EE_245R_Ctrl;

import static java.lang.Thread.sleep;


/**
 * This file provides basic TeleOp driving for a Pushbot robot.
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

@TeleOp(name="MecanumTeleOp", group="Pushbot")
//@Disabled
public class MecanumTeleOp extends OpMode {

    /* Declare OpMode members. */
    MecanumConfig robot = new MecanumConfig(); // use the class created to define a Pushbot's hardware
    // could also use HardwarePushbotMatrix class.
    // sets rate to move servo


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override

    public void init() {
        telemetry.addData("Say", "Init Done");    //
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        /////////////////////////
        //      GAMEPAD 1     //
        ////////////////////////

        double forward_backward;
        double sideways;
        double spin;
        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        forward_backward = -gamepad1.right_stick_y;
        sideways = -gamepad1.right_stick_x;
        spin = -gamepad1.left_stick_x;
        //forward/backwards
        robot.ForwardLeft.setPower(forward_backward);
        robot.ForwardRight.setPower(forward_backward);
        robot.BackwardLeft.setPower(forward_backward);
        robot.BackwardRight.setPower(forward_backward);
        //sideways
        robot.ForwardLeft.setPower(sideways);
        robot.ForwardRight.setPower(-sideways);
        robot.BackwardLeft.setPower(-sideways);
        robot.BackwardRight.setPower(sideways);
        //spin
        robot.ForwardLeft.setPower(spin);
        robot.ForwardRight.setPower(-spin);
        robot.BackwardLeft.setPower(spin);
        robot.BackwardRight.setPower(-spin);
        //angles
        if (gamepad1.dpad_up) { //angle up right
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(0.0);
            robot.BackwardLeft.setPower(0.0);
            robot.BackwardRight.setPower(1.0);
        } else if (gamepad1.dpad_down) {//angle down left
            robot.ForwardLeft.setPower(-1.0);
            robot.ForwardRight.setPower(0.0);
            robot.BackwardLeft.setPower(0.0);
            robot.BackwardRight.setPower(-1.0);
        } else if (gamepad1.dpad_left) {//angle down right
            robot.ForwardLeft.setPower(0.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(0.0);
        } else if (gamepad1.dpad_right) {//angle up left
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(1.0);
        }

        /////////////////////////
        //      GAMEPAD 2      //
        /////////////////////////

        if (gamepad2.y) { //forward
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(1.0);
            telemetry.addData("Forward", "FT_EE_245R_Ctrl");
        } else if (gamepad2.a) {  //backward
            robot.ForwardLeft.setPower(-1.0);
            robot.ForwardRight.setPower(-1.0);
            robot.BackwardLeft.setPower(-1.0);
            robot.BackwardRight.setPower(-1.0);
            telemetry.addData("Backward", "FT_EE_245R_Ctrl");
        } else if (gamepad2.b) {  //sideways right
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(-1.0);
            robot.BackwardLeft.setPower(-1.0);
            robot.BackwardRight.setPower(1.0);
            telemetry.addData("Sideways Right", "FT_EE_245R_Ctrl");
        } else if (gamepad2.x) {  //sideways left
            robot.ForwardLeft.setPower(-1.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(-1.0);
            telemetry.addData("Sideways Left", "FT_EE_245R_Ctrl");
        } else if (gamepad2.right_bumper) { //spin right
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(-1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(-1.0);
            telemetry.addData("Spin Right", "FT_EE_245R_Ctrl");
        } else if (gamepad2.left_bumper) { //spin left
            robot.ForwardLeft.setPower(-1.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(-1.0);
            robot.BackwardRight.setPower(1.0);
            telemetry.addData("Spin Left", "FT_EE_245R_Ctrl");
        } else if (gamepad2.dpad_up) { //angle up right
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(0.0);
            robot.BackwardLeft.setPower(0.0);
            robot.BackwardRight.setPower(1.0);
            telemetry.addData("Angle Up Right", "FT_EE_245R_Ctrl");
        } else if (gamepad2.dpad_down) {//angle down left
            robot.ForwardLeft.setPower(-1.0);
            robot.ForwardRight.setPower(0.0);
            robot.BackwardLeft.setPower(0.0);
            robot.BackwardRight.setPower(-1.0);
            telemetry.addData("Angle Down Left", "FT_EE_245R_Ctrl");
        } else if (gamepad2.dpad_left) {//angle down right
            robot.ForwardLeft.setPower(0.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(0.0);
            telemetry.addData("Angle Down Right", "FT_EE_245R_Ctrl");
        } else if (gamepad2.dpad_right) {//angle up left
            robot.ForwardLeft.setPower(1.0);
            robot.ForwardRight.setPower(1.0);
            robot.BackwardLeft.setPower(1.0);
            robot.BackwardRight.setPower(1.0);
            telemetry.addData("Angle Up Left", "FT_EE_245R_Ctrl");
        }


    }
    @Override
    public void stop() {
        telemetry.addData("DID IT WORK???????????", "FT_EE_245R_Ctrl");
    }

}
