package org.firstinspires.ftc.teamcode.powerplay2022.states;

import android.util.Log;

import org.firstinspires.ftc.teamcode.ebotsenums.Speed;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonStateVelConBase;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;

public class StatePivotNeg45 extends EbotsAutonStateVelConBase {


    public StatePivotNeg45(EbotsAutonOpMode autonOpMode){
        super(autonOpMode);
        boolean debugOn = true;
        Log.d(logTag, "Entering " + this.getClass().getSimpleName() + " constructor");

        // Must define
        motionController.setSpeed(Speed.ROTATE);

        travelDistance = 0; //Inches

        if (debugOn){
            Log.d(logTag, "travelDistance: " + String.format(twoDec, travelDistance));
        }
        // travel direction and
        travelDirectionDeg = 0;
        targetHeadingDeg = -45;

        initAutonState();
        setDriveTarget();

        Log.d(logTag, "Constructor complete");
    }
}
