package org.firstinspires.ftc.teamcode.powerplay2022.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ebotsenums.StartingSide;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsImu;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonState;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;

import java.util.ArrayList;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class StateMoveToHubX2022 implements EbotsAutonState {

    StopWatch stopWatch = new StopWatch();
    EbotsAutonOpMode autonOpMode;

    private String name = this.getClass().getSimpleName();

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private ArrayList<DcMotorEx> motors = new ArrayList<>();
    private double speed;
    private long driveTime;


    public StateMoveToHubX2022(EbotsAutonOpMode autonOpMode){
        this.autonOpMode = autonOpMode;
        HardwareMap hardwareMap = autonOpMode.hardwareMap;
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        motors.add(frontLeft);
        motors.add(frontRight);
        motors.add(backRight);
        motors.add(backLeft);

        stopWatch.reset();
        //updateTelemetry();

        if(autonOpMode.getStartingSide() == StartingSide.CAROUSEL){
            driveTime = 850;
            speed = 0.2;
        } else {
            driveTime = 200;
            speed = -0.2;
        }
    }
    //private void updateTelemetry(){
        //ebotsimu = EbotsImu.getInstance(hardwareMap);
        //telemetry.addData("The robot is fine", false);
        //telemetry.update();
    //}

    @Override
    public boolean shouldExit() {

        boolean shouldExit = false;

        if(stopWatch.getElapsedTimeMillis() >= driveTime){
            shouldExit = true;
        }
        return shouldExit | !autonOpMode.opModeIsActive();

    }

    @Override
    public void performStateActions() {

        for(DcMotorEx motor: motors) {
            motor.setPower(speed);
        }
    }

    @Override
    public void performTransitionalActions() {
            for(DcMotorEx motor: motors) {
                motor.setPower(0.0);
            }

    }
}
