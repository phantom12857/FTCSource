package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PhantomConfig {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;


    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public PhantomConfig(){

    }

    public void init (HardwareMap awhMap){

        hwMap = awhMap;

        leftDrive = hwMap.get(DcMotor.class, "left_drive");

        rightDrive = hwMap.get(DcMotor.class, "right_drive");


        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        leftDrive.setPower(0);

        rightDrive.setPower(0);


    }

}
