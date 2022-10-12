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
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ebotsenums.BucketState;
import org.firstinspires.ftc.teamcode.ebotsenums.RobotSide;
//import org.firstinspires.ftc.teamcode.ebotssensors.EbotsWebcam;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsImu;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.ebotsutil.UtilFuncs;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Arm;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Bucket;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Carousel;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Intake;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.EbotsMotionController;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.FieldOrientedDrive;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.FieldOrientedVelocityControl;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.MecanumDrive;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.opencvpipelines.FreightDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp
public class EbotsTeleOp2022 extends LinearOpMode {

    private EbotsMotionController motionController;
    private StopWatch lockoutStopWatch = new StopWatch();
    private StopWatch endGameStopWatch = new StopWatch();
    private Telemetry.Item zeroHeadingItem = null;

    private boolean endGameRumbleIssued;
    private OpenCvCamera camera;
    private String logTag = "EBOTS";
    private EbotsImu ebotsimu;

    // private DcMotorEx motor;        // the motor that controls the mecanum wheel
   // DcMotorEx motor1 = hardwareMap.get(DcMotorEx.class, "frontLeft");




    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {
        endGameRumbleIssued = false;
        //motor1.setPower(.25);
        ebotsimu = EbotsImu.getInstance(hardwareMap);
        ebotsimu.initEbotsImu(hardwareMap);


        motionController = EbotsMotionController.get(FieldOrientedVelocityControl.class, this);
        //EbotsWebcam bucketWebCam = new EbotsWebcam(hardwareMap, "bucketCam", RobotSide.FRONT, 0,-3.25f, 9.0f);

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

