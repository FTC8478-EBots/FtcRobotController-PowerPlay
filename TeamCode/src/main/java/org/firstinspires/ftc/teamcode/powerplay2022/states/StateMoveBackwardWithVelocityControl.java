package org.firstinspires.ftc.teamcode.powerplay2022.states;

import android.util.Log;

import org.firstinspires.ftc.teamcode.ebotsenums.Speed;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonStateVelConBase;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;

public class StateMoveBackwardWithVelocityControl extends EbotsAutonStateVelConBase {


    public StateMoveBackwardWithVelocityControl(EbotsAutonOpMode autonOpMode){
        super(autonOpMode);
        boolean debugOn = true;
        Log.d(logTag, "Entering " + this.getClass().getSimpleName() + " constructor");

        // Must define
        motionController.setSpeed(Speed.MEDIUM);

        travelDistance = 60; //Inches

        if (debugOn){
            Log.d(logTag, "travelDistance: " + String.format(twoDec, travelDistance));
        }
        // travel direction and
        travelDirectionDeg = -180;
        targetHeadingDeg = 0;

        initAutonState();
        setDriveTarget();

        Log.d(logTag, "Constructor complete");
    }
}
