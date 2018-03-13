package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumConfig {
    public DcMotor ForwardLeft = null;
    public DcMotor ForwardRight = null;
    public DcMotor BackwardLeft = null;
    public DcMotor BackwardRight = null;
    public ColorSensor sensorColor = null;
    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();{
}

public MecanumConfig () {

}

public void init(HardwareMap ahwMap) {
    ForwardLeft = hwMap.dcMotor.get("left_front");
    ForwardRight = hwMap.dcMotor.get("right_front");
    BackwardLeft = hwMap.dcMotor.get("left_back");
    BackwardRight = hwMap.dcMotor.get("right_back");

    ForwardLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
    ForwardRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
    BackwardLeft.setDirection(DcMotor.Direction.FORWARD);
    BackwardRight.setDirection(DcMotor.Direction.REVERSE);

    ForwardLeft.setPower(0.0);
    ForwardRight.setPower(0.0);
    BackwardLeft.setPower(0.0);
    BackwardRight.setPower(0.0);

    ForwardLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    ForwardRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BackwardLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    BackwardRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



}

    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
