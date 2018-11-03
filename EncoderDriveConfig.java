package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderDriveConfig {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor climbDrive = null;
    public Servo Marker = null;
    public static final double MID_SERVO = 0.5;
    public static final double CLIMB_UP_POWER = 0.8;
    public static final double CLIMB_DOWN_POWER = -0.8;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public EncoderDriveConfig(){

    }

    public void init (HardwareMap awhMap){

        hwMap = awhMap;

        //Motor Configurations
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        climbDrive = hwMap.get(DcMotor.class, "climb_drive");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        climbDrive.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbDrive.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        climbDrive.setPower(0);

        Marker = hwMap.get(Servo.class, "marker_servo");

        Marker.setPosition(MID_SERVO);



    }

}
