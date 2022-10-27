package org.firstinspires.ftc.teamcode.powerplay2022.opmodes;
//Sean's baby no1
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ebotsenums.BucketState;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsBlinkin;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Bucket;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.FieldOrientedVelocityControl;

public class Elevator {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Class Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private TheEagleTalon theEagleTalon;
    private DcMotorEx armMotor;
    private DigitalChannel zeroLimitSwitch;
    private boolean isZeroed = false;
    private StopWatch stopWatchInput = new StopWatch();
    private boolean wasAtLevelOne = false;
    private boolean isAtLevelOne = false;
    private static Elevator armInstance = null;
    private Level targetLevel;
    HardwareMap hardwareMap;
    private boolean rotateToCollectWhenReturnToBottom = false;

    private LinearOpMode opMode;
    private ArmState armState;

    private static String logTag = "EBOTS";

    public enum Level {
        ONE(0),
        CONE_1(0),
        CONE_2(-100),
        CONE_3(-200),
        CONE_4(-300),
        CONE_5(-400),
        ONE_FIVE(-100),
        TWO(-550),
        THREE(-950),
        FOUR(-1300);

        private int encoderPosition;

        Level(int pos) {
            this.encoderPosition = pos;
        }
        public Level next() {
            final Level[] values = values();
            return values[(ordinal()+1) % values.length];
        }
        public Level prev() {
            final Level[] values = values();
            return values[(ordinal()-1 + values.length) % values.length];
        }
        public int getEncoderPosition() {
            return encoderPosition;
        }
    }

    public enum ArmState {
        AT_LEVEL_1,
        CONE_1,
        CONE_2,
        CONE_3,
        CONE_4,
        CONE_5,
        AT_LEVEL_1_5,
        AT_LEVEL_2,
        AT_LEVEL_3,
        AT_LEVEL_4,
        MOVING_UP,
        MOVING_DOWN,
        JUST_DUMPED,
        INITIALIZED,
        NEW
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private Elevator(LinearOpMode opMode) {
        Log.d(logTag, "Instantiating arm...");
        armState = ArmState.NEW;
        this.init(opMode);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Getters & Setters
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public double getSpeed() {
        return armMotor.getPower();
    }

    public boolean isAtBottom() {
        return true;//!zeroLimitSwitch.getState();
    }

    public int getPosition() {
        return armMotor.getCurrentPosition();
    }

    public boolean isInitialized() {
        return armState == ArmState.INITIALIZED;
    }

    public ArmState getArmState() {
        updateArmState();
        return armState;
    }

    public void setFlagToRotateAtBottom() {
        this.rotateToCollectWhenReturnToBottom = true;
    }

    public LinearOpMode getOpMode() {
        return opMode;
    }

    private void setTargetLevel(Level level) {
        int targetPosition = level.getEncoderPosition();
        int currentPosition = armMotor.getCurrentPosition();

        boolean travelingDown = (targetPosition < currentPosition);
        double targetPower = 1;
        if (travelingDown) {
            armState = ArmState.MOVING_DOWN;
            targetPower = 0.5;
        } else {
            armState = ArmState.MOVING_UP;
        }

        armMotor.setTargetPosition(targetPosition);
        armMotor.setPower(targetPower);
        targetLevel = level;
        armMotor.setTargetPosition(level.getEncoderPosition());
    }

    public boolean shouldBucketCollect() {
        // this is intended to move the bucket to Collect position just after dumping
        boolean returnFlag = false;
        updateArmState();
        isAtLevelOne = (armState == ArmState.AT_LEVEL_1);
        if (!wasAtLevelOne && isAtLevelOne) {
            // If just arrived at level One and
            if (rotateToCollectWhenReturnToBottom) {
                returnFlag = true;
                rotateToCollectWhenReturnToBottom = false;
            }
            wasAtLevelOne = true;
        }
        return returnFlag;
    }

    public boolean isAtTargetLevel() {
        boolean verdict = false;
        int error = armMotor.getTargetPosition() - armMotor.getCurrentPosition();
        if (Math.abs(error) <= 5) {
            verdict = true;
        }
        return verdict;
    }

    private void updateArmState() {
        if (isAtTargetLevel()) {
            if (targetLevel == Level.ONE) {
                armState = ArmState.AT_LEVEL_1;
            } else if (targetLevel == Level.ONE_FIVE){
                armState = ArmState.AT_LEVEL_1_5;
            } else if (targetLevel == Level.TWO) {
                armState = ArmState.AT_LEVEL_2;
            } else if (targetLevel == Level.THREE) {
                armState = ArmState.AT_LEVEL_3;
            } else if (targetLevel == Level.FOUR) {
                armState = ArmState.AT_LEVEL_4;
            }
        }
    }

    public boolean getIsZeroed() {
        return isZeroed;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Static Methods
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    //Return the instance if not present
    public static Elevator getInstance(LinearOpMode opMode) {
        if (armInstance == null) {
            Log.d(logTag, "Arm::getInstance --> Arm is null, about to create a new instance...");
            armInstance = new Elevator(opMode);
            Log.d(logTag, "Arm::getInstance --> New arm created");

        } else if (armInstance.getOpMode() != opMode) {
            Log.d(logTag, "Arm::getInstance --> opMode is stale, about to create a new instance...");
            armInstance = new Elevator(opMode);
            Log.d(logTag, "Arm::getInstance --> New arm instance created because opMode didn't match");
        } else {
            Log.d(logTag, "Arm::getInstance --> Existing instance of arm provided");
        }
        return armInstance;
    }


    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Methods
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void updateTelemetry() {
        telemetry.addData("Elevator Position", armMotor.getCurrentPosition());
    }

    public void init(LinearOpMode opMode) {
        telemetry = opMode.telemetry;
        Log.d(logTag, "Inside Arm::init...");
        this.opMode = opMode;
        theEagleTalon = TheEagleTalon.getInstance(opMode);
        this.hardwareMap = this.opMode.hardwareMap;
        isZeroed = false;
        this.armMotor = this.hardwareMap.get(DcMotorEx.class, "elevatorMotor");
        //this.zeroLimitSwitch = this.hardwareMap.get(DigitalChannel.class, "zeroLimitSwitch");

        if (!isAtBottom()) {
            Log.d(logTag, "Limit switch was not engaged, preparing to move are to zeroArmHeight");
            zeroArmHeight();
        } else {
            Log.d(logTag, "Limit switch was already engaged, about to performZeroActions...");
            performZeroActions();
        }
        armMotor.setTargetPosition(0);

        if (isAtBottom()) armState = ArmState.INITIALIZED;

        // These lines are added because limit switch is not working properly
//        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        isZeroed = true;
//        armState = ArmState.AT_LEVEL_1;
//        targetLevel = Level.ONE;
//        wasAtLevelOne = true;
        //****************************************
    }

    public void zeroArmHeight() {
        Log.d(logTag, "Entering zeroArmHeight");

        isZeroed = false;
        if (isAtBottom()) {
            Log.d(logTag, "Arm is at bottom...");
            performZeroActions();
            return;
        }

        Log.d(logTag, "Preparing to rotate bucket to travel position...");
        rotateBucketToTravelPosition();

        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        Log.d(logTag, "Preparing to move arm...");
        StopWatch stopWatchZero = new StopWatch();
        long timeLimit = 600;
        boolean isTimedOut = stopWatchZero.getElapsedTimeMillis() >= timeLimit;

        // the flags used to determine if the bucket needs to rotate must be managed relative to whether this is during init
        // duringInit, the bucket status is unreliable.  Assume the bucket is not in Travel position
        // and the opMode monitor exit conditions are a function of whether in init or not
        boolean duringInit = !opMode.opModeIsActive();
        boolean exitRequested = opModeExitRequested(duringInit);

        while (!isAtBottom() && !isTimedOut && !exitRequested) {
            armMotor.setPower(-0.25);
            isTimedOut = stopWatchZero.getElapsedTimeMillis() >= timeLimit;
        }
        if (isTimedOut) Log.d(logTag, "Exited movement because timed out");
        if (isAtBottom()) Log.d(logTag, "Exited movement because reached bottom");
        if (exitRequested) Log.d(logTag, "Exited because opMode requested exit");
        // stop the motor
        armMotor.setPower(0.0);

        if (isAtBottom()) {
            performZeroActions();
        } else {
            Log.d(logTag, "!!! Zero operation NOT successful !!!");
            int currentPosition = armMotor.getCurrentPosition();
            armMotor.setTargetPosition(currentPosition);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    private void performZeroActions() {
        Log.d(logTag, "Entering Arm::performZeroActions...");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        isZeroed = true;
        opMode.gamepad2.rumble(350);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armState = ArmState.AT_LEVEL_1;
        targetLevel = Level.ONE;
        wasAtLevelOne = true;
        Log.d(logTag, "Exiting Arm::performZeroActions");
    }

    public void moveToLevel(Level level) {
        Log.d(logTag, "Enter move to level");

        //rotateBucketToTravelPosition();
        //Bucket bucket = Bucket.getInstance(opMode);
        //boolean bucketInTravelPosition = bucket.getBucketState() == BucketState.TRAVEL;
        //if (!isZeroed | !bucketInTravelPosition) return;

        setTargetLevel(level);
//        if(level != Level.ONE) wasAtLevelOne = false;
        wasAtLevelOne = false;
    }

    private void rotateBucketToTravelPosition() {
        //Bucket bucket = Bucket.getInstance(opMode);
        //Log.d(logTag, "Bucket position: " + bucket.getBucketState());
        //EbotsBlinkin ebotsBlinkin = EbotsBlinkin.getInstance(opMode.hardwareMap);
        //ebotsBlinkin.lightsOff();
        //long rotateTime = 400L;
        //StopWatch stopWatch = new StopWatch();
//        boolean bucketInTravelPosition = bucket.getBucketState() == BucketState.TRAVEL;
//        if (!bucketInTravelPosition) bucket.setState(BucketState.TRAVEL);

        // the flags used to determine if the bucket needs to rotate must be managed relative to whether this is during init
        // duringInit, the bucket status is unreliable.  Assume the bucket is not in Travel position
        // and the opMode monitor exit conditions are a function of whether in init or not

        // Note, during init, opoModeIsActive evaluates to false
        //boolean duringInit = !opMode.opModeIsActive();
        //boolean exitRequested = opModeExitRequested(duringInit);

        //boolean bucketInTravelPosition = duringInit ? false : bucket.getBucketState() == BucketState.TRAVEL;
        //if (!bucketInTravelPosition) bucket.setState(BucketState.TRAVEL);
        //while (!exitRequested && !bucketInTravelPosition) {
        //bucketInTravelPosition = stopWatch.getElapsedTimeMillis() > rotateTime;
    }

    /**
     * Returns whether the user has requested exit and is conditional based on if opMode is started
     *
     * @param duringInit: whether the opModeIsActive or not.  It's not active during init
     * @return if exit is requested
     */
    private boolean opModeExitRequested(boolean duringInit) {
        boolean exitRequested;
        if (duringInit) {
            exitRequested = opMode.isStarted() | opMode.isStopRequested();
        } else {
            exitRequested = !opMode.opModeIsActive();
        }
        return exitRequested;
    }

    @Deprecated
    public void moveToLevelAuton(Level level) {
        Log.d(logTag, "Enter move to level auton");

        rotateBucketToTravelPosition();
        //Bucket bucket = Bucket.(opMode);

        int targetPosition = level.getEncoderPosition();
        int currentPosition = armMotor.getCurrentPosition();


        //boolean bucketInTravelPosition = bucket.getBucketState() == BucketState.TRAVEL;
        //if (!isZeroed | !bucketInTravelPosition) return;

        setTargetLevel(level);
//        if(level != Level.ONE) wasAtLevelOne = false;
        wasAtLevelOne = false;

//        boolean travelingDown = (targetPosition < currentPosition);
//        if (travelingDown) {
//            armState = ArmState.MOVING_DOWN;
//        } else {
//            armState = ArmState.MOVING_UP;
//        }
//
//        double targetPower = travelingDown ? 0.25 : 0.5;
//        armMotor.setTargetPosition(targetPosition);
//        armMotor.setPower(targetPower);

    }

    public void handleUserInput(Gamepad gamepad) {
        updateTelemetry();
        if (stopWatchInput.getElapsedTimeMillis() < 500) return;
        if (gamepad.left_bumper && gamepad.right_stick_button) {
            Log.d(logTag, "Captured input to zeroArmHeight");
            zeroArmHeight();
            stopWatchInput.reset();
        } else if (gamepad.square) {
            moveToLevel(Level.TWO);
            stopWatchInput.reset();
        } else if (gamepad.cross) {
            moveToLevel(Level.ONE);
            stopWatchInput.reset();
        } else if (gamepad.triangle) {
            moveToLevel(Level.THREE);
            stopWatchInput.reset();
        } else if (gamepad.circle) {
            moveToLevel(Level.FOUR);
            stopWatchInput.reset();
        } else if (gamepad.dpad_left) {
            armMotor.setPower(0);
        } else if (gamepad.dpad_right) {
            armMotor.setPower(1);
        } else if (gamepad.dpad_up) {
            moveToLevel(targetLevel.next());
        } else if (gamepad.dpad_down) {
            moveToLevel(targetLevel.prev());
        }
        if (targetLevel == Level.ONE & theEagleTalon.isClosed()) {
            moveToLevel(Level.ONE_FIVE);
        }else if (targetLevel == Level.ONE_FIVE & !theEagleTalon.isClosed()){
            moveToLevel(Level.ONE);
        }

    }
}