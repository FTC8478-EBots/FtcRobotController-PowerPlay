package org.firstinspires.ftc.teamcode.powerplay2022.opmodes;

import android.annotation.SuppressLint;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ebotsenums.BucketState;
import org.firstinspires.ftc.teamcode.ebotsenums.RobotSide;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsImu;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.ebotsutil.UtilFuncs;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Arm;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.EbotsMotionController;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.FieldOrientedDrive;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.FieldOrientedVelocityControl;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.MecanumDrive;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.opencvpipelines.FreightDetector;

@TeleOp
public class EbotsTeleOp2022 extends LinearOpMode {

    private Elevator elevator;
    private TheEagleTalon theEagleTalon;

    private EbotsMotionController motionController;
    private StopWatch lockoutStopWatch = new StopWatch();
    private StopWatch endGameStopWatch = new StopWatch();
    private Telemetry.Item zeroHeadingItem = null;

    private boolean endGameRumbleIssued;
    private String logTag = "EBOTS";
    private EbotsImu ebotsimu;




    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {
        endGameRumbleIssued = false;
        elevator = Elevator.getInstance(this);
        theEagleTalon = TheEagleTalon.getInstance(this);
        //motor1.setPower(.25);
        ebotsimu = EbotsImu.getInstance(hardwareMap);
        ebotsimu.initEbotsImu(hardwareMap);
        //UtilFuncs.initManips(elevator,null,this);
        elevator.init(this);
        theEagleTalon.init(this);


        motionController = EbotsMotionController.get(FieldOrientedVelocityControl.class, this);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        Log.d(logTag, "cameraMonitorViewId set");


        Log.d(logTag, "Camera for Freight Detector Instantiated");


        while (! this.isStarted()){
            this.handleUserInput(gamepad1);

            updateTelemetry();
        }

        waitForStart();
        endGameStopWatch.reset();

        while (opModeIsActive()){

            rumbleIfEndGame();
            this.handleUserInput(gamepad1);
            motionController.handleUserInput(gamepad1);
            elevator.handleUserInput(gamepad2);
            theEagleTalon.handleUserInput(gamepad2);


            updateTelemetry();
        }


    }

    private void rumbleIfEndGame() {
        if (endGameStopWatch.getElapsedTimeSeconds() >= 89 && !endGameRumbleIssued){
            gamepad1.rumble(1000);
            gamepad2.rumble(1000);
            endGameRumbleIssued = true;
        }
    }


    private void updateTelemetry() {
        String twoDecimals = "%.2f";
        Telemetry.Item zeroHeadingLine = null;
        telemetry.addData("Motion Controller", motionController.getName());
        if(motionController instanceof FieldOrientedVelocityControl){
            ((FieldOrientedVelocityControl) motionController).addVelocitiesToTelemetry(telemetry);
        }

        telemetry.update();
    }

    private void handleUserInput(Gamepad gamepad){
        boolean lockoutActive = lockoutStopWatch.getElapsedTimeMillis() < 600;

        if (lockoutActive){
            return;
        }


        if(gamepad.left_bumper && gamepad.right_stick_button){
            if (motionController instanceof MecanumDrive){
                motionController = EbotsMotionController.get(FieldOrientedVelocityControl.class, this);
            } else if (motionController instanceof FieldOrientedVelocityControl){
                motionController = EbotsMotionController.get(FieldOrientedDrive.class, this);
            } else if (motionController instanceof FieldOrientedDrive){
                motionController = EbotsMotionController.get(MecanumDrive.class, this);
            }

            gamepad.rumble(1.0, 1.0, 400);  // 200 mSec burst on left motor.
            lockoutStopWatch.reset();
        }

    }
}