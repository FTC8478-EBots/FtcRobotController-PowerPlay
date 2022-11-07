package org.firstinspires.ftc.teamcode.powerplay2022.states;

import android.util.Log;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ebotsenums.Speed;
import org.firstinspires.ftc.teamcode.ebotssensors.EbotsImu;
import org.firstinspires.ftc.teamcode.ebotsutil.AllianceSingleton;
import org.firstinspires.ftc.teamcode.ebotsutil.FieldPosition;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.DriveToEncoderTarget;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonState;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.Elevator;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.TheEagleTalon;

public class StateCloseTalon implements EbotsAutonState {
    private EbotsAutonOpMode autonOpMode;
    private Telemetry telemetry;

    private int targetClicks;
    private long stateTimeLimit;
    private StopWatch stopWatch;
    private DriveToEncoderTarget motionController;

    private String logTag = "EBOTS";
    private boolean firstPass = true;
    private double travelDistance = 4.0;
    private double clicksPerSquare = 849;
    public StateCloseTalon(EbotsAutonOpMode autonOpMode){
        Log.d(logTag, "Entering StatePushOffWithEncoders constructor");
        this.autonOpMode = autonOpMode;
        this.telemetry = autonOpMode.telemetry;
        motionController = new DriveToEncoderTarget(autonOpMode);
        Log.d(logTag, "Constructor complete");
        TheEagleTalon theEagleTalon =  TheEagleTalon.getInstance(autonOpMode);
        theEagleTalon.graspTalon();
    }


    @Override
    public boolean shouldExit() {
      return true;
    }

    @Override
    public void performStateActions() {

    }

    @Override
    public void performTransitionalActions() {

    }
}
