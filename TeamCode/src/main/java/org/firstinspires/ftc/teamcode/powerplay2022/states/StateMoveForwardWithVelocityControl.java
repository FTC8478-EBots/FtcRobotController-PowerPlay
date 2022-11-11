package org.firstinspires.ftc.teamcode.powerplay2022.states;

import android.util.Log;

import org.firstinspires.ftc.teamcode.ebotsenums.BarCodePosition;
import org.firstinspires.ftc.teamcode.ebotsenums.RobotSize;
import org.firstinspires.ftc.teamcode.ebotsenums.Speed;
import org.firstinspires.ftc.teamcode.ebotsutil.AllianceSingleton;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021.Arm;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonStateVelConBase;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StatePushOffCarouselWithVelocityControl;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StatePushOffWallBlueVelocityControl;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;

public class StateMoveForwardWithVelocityControl extends EbotsAutonStateVelConBase {

    private final double HUB_DISTANCE_FROM_WALL = 48.0;
    private final double ROBOT_HALF_LENGTH = RobotSize.xSize.getSizeValue() / 2;
    private final double BUCKET_CENTER_OFFSET = RobotSize.getBucketOffset();

    public StateMoveForwardWithVelocityControl(EbotsAutonOpMode autonOpMode){
        super(autonOpMode);
        boolean debugOn = true;
        Log.d(logTag, "Entering " + this.getClass().getSimpleName() + " constructor");

        // Must define
        motionController.setSpeed(Speed.MEDIUM);
        boolean isBlue = AllianceSingleton.isBlue();

        int allianceSign = (AllianceSingleton.isBlue()) ? 1 : -1;

        double offsetDistance = StatePushOffWallBlueVelocityControl.getStateUndoTravelDistance();
        offsetDistance += StatePushOffCarouselWithVelocityControl.getStateUndoTravelDistance();


        travelDistance = HUB_DISTANCE_FROM_WALL - ROBOT_HALF_LENGTH +
                (BUCKET_CENTER_OFFSET * allianceSign) - offsetDistance;

//        travelDistance = isBlue ? 19.43 : 28.02;
        travelDistance = 60; //Inches

        if (debugOn){
            Log.d(logTag, "travelDistance: " + String.format(twoDec, travelDistance));
        }
        // travel direction and
        travelDirectionDeg = AllianceSingleton.getDriverFieldHeadingDeg();
        targetHeadingDeg = AllianceSingleton.getDriverFieldHeadingDeg();

        initAutonState();
        setDriveTarget();

        Log.d(logTag, "Constructor complete");
    }


    @Override
    public boolean shouldExit() {
        return super.shouldExit();
    }

    @Override
    public void performStateActions() {
        super.performStateActions();
    }

    @Override
    public void performTransitionalActions() {
        super.performTransitionalActions();
    }


}
