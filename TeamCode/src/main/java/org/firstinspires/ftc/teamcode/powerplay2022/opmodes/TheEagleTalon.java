package org.firstinspires.ftc.teamcode.powerplay2022.opmodes;
//All of the editing was done by the Software Team.
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.ebotsenums.BucketState;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Arm;
public class TheEagleTalon {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    Servo leftTalon;
    Servo rightTalon;

   // private BucketState bucketState;
    //private BucketState dumpStartedFrom;
    private StopWatch stopWatchDump;
    private StopWatch stopWatchInput = new StopWatch();
    private LinearOpMode opMode;
    private boolean talonsAreClosed = false;
    private boolean dumpAchieved = false;
    //private EbotsBlinkin ebotsBlinkin;
    private static String logTag = "EBOTS";

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private static TheEagleTalon ebotsBucket = null;
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private TheEagleTalon(LinearOpMode opMode){
        this.opMode = opMode;
        init(opMode);

    }
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Getters & Setters
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setPos(double servoPos){
        leftTalon.setPosition(1-servoPos);
        rightTalon.setPosition(servoPos); //Sean did this
    }

    public double getPos(){
        return ((leftTalon.getPosition()+rightTalon.getPosition())/2);
    }

    //public BucketState getBucketState() {
      //  return bucketState;


    public LinearOpMode getOpMode() {
        return opMode;
    }

    public void setState(BucketState targetState){
    //   0 if(targetState == BucketState.DUMP) {
            // If asking to dump but state was previously collect then toggle state
      //      if (bucketState == BucketState.COLLECT) toggleState();
        //    bucketState = BucketState.DUMP;
          //  setPos(getDumpPositionWithVibrate());
        //} else if(targetState == BucketState.TRAVEL) {
          //  bucketState = BucketState.TRAVEL;
            //setPos(BucketState.TRAVEL.getServoSetting());
      //  } else {
            // if asking to COLLECT but state was previously DUMP, then toggle state
          //  if(bucketState == BucketState.DUMP) {
         //       toggleState();
        //    } else {
       //         bucketState = BucketState.COLLECT;
            //}
            //setPos(BucketState.COLLECT.getServoSetting());
   //     }
    }
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Static Methods
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // No static methods defined
    public static TheEagleTalon getInstance(LinearOpMode opMode){

        if (ebotsBucket == null){
            ebotsBucket = new TheEagleTalon(opMode);
            Log.d(logTag, "Bucket::getInstance --> Bucket instance instantiated because opMode didn't match");
        } else if(ebotsBucket.getOpMode() != opMode){
            ebotsBucket = new TheEagleTalon(opMode);
            Log.d(logTag, "Bucket::getInstance --> Bucket instance instantiated because opMode didn't match");
        } else{
            Log.d(logTag, "Bucket::getInstance --> existing bucket instance provided");
        }

        return ebotsBucket;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Methods
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public boolean isClosed(){
        return(talonsAreClosed);
    }
    public void init(LinearOpMode opMode){
        this.opMode = opMode;
        leftTalon = opMode.hardwareMap.get(Servo.class,"rightTalon");
        rightTalon = opMode.hardwareMap.get(Servo.class,"leftTalon");
     //   bucketState = BucketState.COLLECT;
       // leftTalon.setPosition(bucketState.getServoSetting());
        stopWatchDump = new StopWatch();
        this.setState(BucketState.COLLECT);
    }

    public void handleUserInput(Gamepad gamepad){

        long lockOutLimit = 500;
        boolean isLockedOut = stopWatchInput.getElapsedTimeMillis() <= lockOutLimit;
        if (gamepad.circle && !isLockedOut){
            // if push the circle, toggle between COLLECT and TRAVEL
            toggleState();
            stopWatchInput.reset();
        } else if (gamepad.dpad_left){
            // if dpad left, set the state to dump and start the timer for the bucket waggle
          //  if (bucketState != BucketState.DUMP) {
                stopWatchDump.reset();
            //    dumpStartedFrom = bucketState;  // either collect or travel
                // Log.d("EBOTS", "dumpStartedFrom: " + dumpStartedFrom.name());
        //        bucketState = BucketState.DUMP;
                // Log.d("EBOTS", "dumpStartedFrom: (After set to DUMP)" + dumpStartedFrom.name());

            }
            // the target position varies in time to shake freight out
            //setPos(getDumpPositionWithVibrate());
        if(gamepad.right_bumper) {
            graspTalon();
        }
        else if(gamepad.left_bumper) {
            releaseTalon();
        }
        //} else if (bucketState == BucketState.DUMP) {
            toggleState();
        //}
    }

    public void graspTalon(){
        setPos(0);  //
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                talonsAreClosed = true;
            }
        }, 300);
    }
    //Closes Talon
    public void releaseTalon() {
        setPos(1);
        //Change states of talonsAreClosed after specified time
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                talonsAreClosed = false;
            }
        }, 300);
    }
    //Open Talon
    private void toggleState(){
       // ebotsBlinkin = EbotsBlinkin.getInstance(opMode.hardwareMap);
        //if (bucketState == BucketState.COLLECT){
            // if bucket at Collect, move to Travel
          //  bucketState = BucketState.TRAVEL;
          //  ebotsBlinkin.lightsOff();
            dumpAchieved = false;
        //} else if(bucketState == BucketState.TRAVEL && (arm.getArmState() == Arm.ArmState.AT_LEVEL_1)) {
            // only toggle to collect mode when at Level_1
          //  bucketState = BucketState.COLLECT;
            //ebotsBlinkin.lightsOn();
            dumpAchieved = false;
        //} else if(bucketState == BucketState.TRAVEL && !(arm.getArmState() == Arm.ArmState.AT_LEVEL_1)) {
            // if requesting to go to collect but not at Level1 then deny bucket movement
           // Log.d("EBOTS", "Bucket tilt to Collect DENIED!!!");
        //} else if (bucketState == BucketState.DUMP && dumpAchieved){
            // if state was DUMP and dump achieved them return arm to level 1
          //  bucketState = BucketState.TRAVEL;
        //} else {
            // otherwise set the bucket state to TRAVEL
            //bucketState = BucketState.TRAVEL;
        //}

        //setPos(bucketState.getServoSetting());
    }

    private double getDumpPositionWithVibrate() {
        double targetServoSetting = BucketState.DUMP.getServoSetting();
        double settingReduction = 0.1;  // amount to reduce setting
        // first allow bucket to dump or duration dumpTime
        //long dumpTime = (dumpStartedFrom == BucketState.COLLECT) ? 450 : 200;
        // vibrate by frequency
        long frequencyMillis = 100;

        long currentTime = stopWatchDump.getElapsedTimeMillis();
        //dumpAchieved = currentTime > dumpTime;

        //long cycleTime = currentTime - dumpTime;

        // on odd number cycles, move to a lesser dump angle
        //int currentCycle = (int) Math.floor(cycleTime / frequencyMillis);

        //boolean cycleIsOdd = currentCycle % 2 == 1;
        //if (currentTime > dumpTime && !cycleIsOdd){
        //   targetServoSetting += settingReduction;
        //  }

        //return targetServoSetting;
        return 0.0;
    }
}
