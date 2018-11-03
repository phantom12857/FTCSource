package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name= "TeleOpOld", group= "TeleOp")
//Disabled
public class TeleOpOld extends OpMode {

    /* Declare OpMode members. */
    PhantomConfig robot       = new PhantomConfig(); // use the class created to define a Pushbot's hardware
    // could also use  class.b
    // sets rate to move servo

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
        telemetry.addData("Say", "Hello Driver");    //
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
    }
    @Override
    public void loop() {
        double leftDrive;
        double rightDrive;

        leftDrive = -gamepad1.left_stick_y;
        rightDrive = -gamepad1.right_stick_y;

        robot.leftDrive.setPower(leftDrive);
        robot.rightDrive.setPower(rightDrive);

        // Send telemetry message to signify robot running;
        telemetry.addData("left",  "%.2f", leftDrive);
        telemetry.addData("right", "%.2f", rightDrive);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}

