package org.firstinspires.ftc.teamcode.powerplay2022.states;
import android.util.Log;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.DriveToEncoderTarget;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonState;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.Elevator;

public class StateElevatorCone5 implements EbotsAutonState {
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
    private Elevator elevator;
    public StateElevatorCone5(EbotsAutonOpMode autonOpMode){
        Log.d(logTag, "Entering StatePushOffWithEncoders constructor");
        this.autonOpMode = autonOpMode;
        this.telemetry = autonOpMode.telemetry;
        Log.d(logTag, "Constructor complete");
        elevator = Elevator.getInstance(autonOpMode);
        elevator.moveToLevel(Elevator.Level.CONE_5);
    }


    @Override
    public boolean shouldExit() {
        return elevator.isAtTargetLevel();
    }

    @Override
    public void performStateActions() {

    }

    @Override
    public void performTransitionalActions() {

    }
}
