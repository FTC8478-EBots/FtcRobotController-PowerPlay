package org.firstinspires.ftc.teamcode.powerplay2022.opmodes;
//All of the editing was done by the Software Team.

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ebotsenums.BucketState;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsBlinkin;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.EbotsMotionController;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.FieldOrientedVelocityControl;

public class TheEagleEye {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
  FieldOrientedVelocityControl fieldOrientedVelocityControl;

   // private BucketState bucketState;
    //private BucketState dumpStartedFrom;
    private StopWatch stopWatchDump;
    private StopWatch stopWatchInput = new StopWatch();
    private LinearOpMode opMode;
    private boolean talonsAreClosed = false;
    private boolean dumpAchieved = false;
    private EbotsBlinkin ebotsBlinkin;
    private static String logTag = "EBOTS";
    private DistanceSensor distanceSensor;
    private int red = 550;
    private int yellow = 160;
    private int green = 130;
    private int indigo = -1;
    private int scanSpeed = 350;
    private int driveSpeed = -400;
    private Gamepad gamepad;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private static TheEagleEye theEagleEye = null;
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private TheEagleEye(LinearOpMode opMode){
        this.opMode = opMode;
        init(opMode);

    }
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Getters & Setters
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public LinearOpMode getOpMode() {
        return opMode;
    }

    public void setState(BucketState targetState){

    }
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Static Methods
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // No static methods defined
    public static TheEagleEye getInstance(LinearOpMode opMode){

        if (theEagleEye == null){
            theEagleEye = new TheEagleEye(opMode);
            Log.d(logTag, "Bucket::getInstance --> Bucket instance instantiated because opMode didn't match");
        } else if(theEagleEye.getOpMode() != opMode){
            theEagleEye = new TheEagleEye(opMode);
            Log.d(logTag, "Bucket::getInstance --> Bucket instance instantiated because opMode didn't match");
        } else{
            Log.d(logTag, "Bucket::getInstance --> existing bucket instance provided");
        }

        return theEagleEye;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Methods
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public void init(LinearOpMode opMode){
        this.opMode = opMode;

        stopWatchDump = new StopWatch();
        //ebotsBlinkin = opMode.hardwareMap.get(EbotsBlinkin.class,"ebotsBlinkin");
        distanceSensor = opMode.hardwareMap.get(DistanceSensor.class,"distanceSensor");
        this.setState(BucketState.COLLECT);
        fieldOrientedVelocityControl = (FieldOrientedVelocityControl) EbotsMotionController.get(FieldOrientedVelocityControl.class, opMode);
    }

    public boolean handleUserInput(Gamepad gamepad){
        this.gamepad = gamepad;
        if(gamepad.left_bumper) {
            scanRight();
            displayDistance(getDistance());

            return true;
        }
        else if(gamepad.right_bumper) {
            scanLeft();
            displayDistance(getDistance());

            return true;
        }
        else {fieldOrientedVelocityControl.ignoreController = false;}

        displayDistance(getDistance());
        return false;
    }
    private double getDistance(){
        return distanceSensor.getDistance(DistanceUnit.MM);
    }
    private void scanRight(){
       fieldOrientedVelocityControl.ignoreController = true;
       if(getDistance() >= red){
           fieldOrientedVelocityControl.spin(scanSpeed);
       }
       else {alignTalonDistance();}
    }
    private void scanLeft(){
        fieldOrientedVelocityControl.ignoreController = true;
        if(getDistance() >= red){
            fieldOrientedVelocityControl.spin(-scanSpeed);
        }
        else {alignTalonDistance();}

    }
     private void alignTalonDistance (){
        if(getDistance() >= yellow){
            fieldOrientedVelocityControl.driveForward(driveSpeed);
        }
        else if(getDistance() <= green) {
            fieldOrientedVelocityControl.driveForward(-driveSpeed);
        }
        else fieldOrientedVelocityControl.driveForward(0);
     }
    private void displayDistance(double dist){
        //dist is distance to pole in mm
        opMode.telemetry.addData("Distance to pole",dist);
        if (dist > red) {
            opMode.telemetry.addLine("RED");
        }
        else if (dist > yellow) {
            opMode.telemetry.addLine("YELLOW");
        }
        else if (dist > green) {
            opMode.telemetry.addLine("GREEN");
            gamepad.rumble(300);
        }
        else if (dist > indigo) {
            opMode.telemetry.addLine("INDIGO");
        }
    }
}
