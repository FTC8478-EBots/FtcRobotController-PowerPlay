package org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates;

import android.util.Log;

import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;

public class StateUndoEnterWarehouseWithVelocityControl extends EbotsAutonStateVelConBase{

    public StateUndoEnterWarehouseWithVelocityControl(EbotsAutonOpMode autonOpMode){
        super(autonOpMode);
        Log.d(logTag, "Entering " + this.getClass().getSimpleName() + " constructor");

        // Must define

        travelDistance = StateEnterWarehouseForCollectVelocityControl.getStateUndoTravelDistance();
        travelDirectionDeg = 180.0;
        targetHeadingDeg = 0.0;

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
