package org.firstinspires.ftc.teamcode.powerplay2022.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.powerplay2022.routines.RoutineBlueLeft;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsImu;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonState;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.OpenCVPipelines.ConeDetector;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateCloseTalon;
import org.openftc.easyopencv.OpenCvCamera;

@Autonomous(preselectTeleOp = "EbotsTeleOp2022")
public class AutonOpMode2022 extends EbotsAutonOpMode {

    String logTag = "EBOTS";
    int statesCreated = 0;
    private EbotsAutonState currentState;
    private EbotsImu ebotsimu;
    private OpenCvCamera camera;
    private boolean stateComplete = false;
    private boolean allStatesCompleted = false;
    private ConeDetector leftConeDetector;
    private ConeDetector rightConeDetector;

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize
        leftConeDetector = new ConeDetector();
        leftConeDetector.startCamera(hardwareMap,"webcamLeft");
        rightConeDetector = new ConeDetector();
        rightConeDetector.startCamera(hardwareMap,"webcamRight");
        initAutonOpMode();
        Log.d(logTag, "About to start State Machine...");
        // Execute the pre-match state machine
        // Requires that the opMode is Started and the state is flagged as completed, which ensures transitional actions happen
        while (!isStarted() && !stateComplete | !isStopRequested()) {
//            Log.d(logTag, this.getClass().getSimpleName() + " : " + this.opModeIsActive());
            transitionToNextState();
            executeStateMachine();
        }
        //this.itinerary.add(StateMoveForward.class);
        //this.itinerary.addAll(new RoutinePark(coneDetector.getParkingSpace()).getRoutineItinerary());//Somehow got this(;))))
        this.itinerary.addAll(new RoutineBlueLeft(findParkingSpace()).getRoutineItinerary());
      //  this.itinerary.addAll(new RoutinePark(coneDetector.getParkingSpace()).getRoutineItinerary());//Somehow got this(;))))
        //this.itinerary.addAll(new RoutineCenterPost().getRoutineItinerary());
        //itinerary.add(StateCalibratingImu.class);
        //itinerary.add(StateConfigureRoutine.class);
        //while (true & ! isStarted()) {
            telemetry.addData("Parking Space", findParkingSpace());
            telemetry.update();
       // }
        waitForStart();
        updateTelemetry();
        // Execute the rest of the autonStates
        while (opModeIsActive()) {
            transitionToNextState();
            updateTelemetry();
            executeStateMachine();
            //Identify current position. Predefined/measure?
            //Read cone for parking
            //Get and place cones

            //Go park
        }

        // Cleanup the resources from Vuforia
//        navigatorVuforia.deactivateTargets();

    }
    public int findParkingSpace() {
         if(leftConeDetector.getParkingSpace() == -1) {
             if (rightConeDetector.getParkingSpace() == -1) {
                 return 2;
             } else {
                 return rightConeDetector.getParkingSpace();
             }
         }
         else {
             return leftConeDetector.getParkingSpace();
         }
    }
    @Override
    public void initAutonOpMode() {
        telemetry.addData("Initializing AutonOpMode ", this.getClass().getSimpleName());
        telemetry.addLine("Please hold................");
        telemetry.update();

        EbotsImu ebotsImu = EbotsImu.getInstance(hardwareMap);
        ebotsImu.initEbotsImu(hardwareMap);
        // must initialize the following
        // touchSensors for configuration
        // imu and zeroHeadingDeg (start with assumption that starting pose is Blue Carousel)
        // currentPose
        // front web cam
        // initialize Navigator(s) (optional) and Arbitrator if more than 1
        // motion controller

        // TODO: figure out if changing these values is beneficial
        // frontWebcam = new EbotsWebcam(hardwareMap, "Webcam 1", RobotSide.FRONT, 0,-3.25f, 9.0f);

        // initialize Navigator(s) (optional) and Arbitrator if more than 1
//        navigatorVuforia = new NavigatorVuforia(frontWebcam, hardwareMap);

        // motion controller
        // this.motionController = new AutonDrive(this);

        // put bucket in collect position
        //bucket = Bucket.getInstance(this);
        //bucket.setState(BucketState.TRAVEL);

        // Setup the pre-match autonStates
        //itinerary.add(StateConfigureRoutine.class);
        //itinerary.add(StateCalibratingImu.class);
        //itinerary.add(StateOpenCVObserve.class);

//        itinerary.add(StateCollectFreightWithVelocityControl.class);
//        itinerary.add(StateUndoCollectTravelWithVelocityControl.class);
//        itinerary.add(StateDelayTenSeconds.class);
        itinerary.add(StateCloseTalon.class);
        telemetry.addLine("Initialization complete!");
        telemetry.update();
    }

    private void executeStateMachine(){
        while (!stateComplete) {
            if (currentState.shouldExit()) {
                currentState.performTransitionalActions();
                stateComplete = true;
            } else {
                currentState.performStateActions();
                updateTelemetry();
            }
        }
//        Log.d(logTag, "....Completed state " + currentState.getClass().getSimpleName());
    }

    private void transitionToNextState(){
        // get the next state if exists
        if (itinerary.size() > 0){
            stateComplete = false;
            Class nextStateClass = itinerary.remove(0);
            currentState = EbotsAutonState.get(nextStateClass, this);
            logNewlyCreatedState(currentState);
            telemetry.clearAll();
        } else if(!allStatesCompleted){
            allStatesCompleted = true;
            Log.d(logTag, "Exiting State machine --> No more states in routine!!!");
        }
    }

    private void logNewlyCreatedState(EbotsAutonState newState){
        statesCreated++;
        String intfmt = "%d";
        String strStateCount = String.format(intfmt, statesCreated);

        try{
            Log.d(logTag, "State #" + strStateCount + " created type " + newState.getClass().getSimpleName());
            String poseString = currentPose == null ? "NULL" : currentPose.toString();
            Log.d(logTag, "Pose when state created: " + poseString);
        } catch (NullPointerException e){
            Log.d(logTag, "Error creating state #" + strStateCount + ".  Returned Null");
            Log.d(logTag, e.getStackTrace().toString());
        } catch (Exception e) {
            Log.d(logTag, "Exception encountered " + e.getStackTrace().toString());
        }
    }

    private void updateTelemetry(){
        ebotsimu = EbotsImu.getInstance(hardwareMap);
        if (currentState != null) {
            telemetry.addData("Current State", currentState.getClass().getSimpleName());
        }
        telemetry.addData("Current heading", ebotsimu.getCurrentFieldHeadingDeg(false));
        telemetry.update();
    }

    //Open up the camera for Freight Detection
    private void startCamera(){



    }



}
