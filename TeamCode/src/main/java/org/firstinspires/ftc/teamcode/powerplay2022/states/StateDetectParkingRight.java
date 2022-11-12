package org.firstinspires.ftc.teamcode.powerplay2022.states;

import android.util.Log;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.motioncontrollers.DriveToEncoderTarget;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.EbotsAutonState;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.AutonOpMode2022Right;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.OpenCVPipelines.ConeDetector;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.TheEagleTalon;

public class StateDetectParkingRight implements EbotsAutonState {
    private AutonOpMode2022Right autonOpMode;
    private Telemetry telemetry;

    private int targetClicks;
    private long stateTimeLimit;
    private StopWatch stopWatch;
    private DriveToEncoderTarget motionController;
    private int leftSpace = -1;
    private int rightSpace = -1;
    private String logTag = "EBOTS";
    private TheEagleTalon theEagleTalon;
    private boolean firstPass = true;
    private double travelDistance = 4.0;
    private double clicksPerSquare = 849;
    ConeDetector leftConeDetector;
    ConeDetector rightConeDetector;
    int parkingSpace = -1;
    public StateDetectParkingRight(EbotsAutonOpMode autonOpMode){
        Log.d(logTag, "Entering StatePushOffWithEncoders constructor");
        this.autonOpMode = (AutonOpMode2022Right) autonOpMode;
        this.telemetry = autonOpMode.telemetry;
        leftConeDetector = new ConeDetector();
        leftConeDetector.startCamera(autonOpMode.hardwareMap,"webcamLeft");
        rightConeDetector = new ConeDetector();
        rightConeDetector.startCamera(autonOpMode.hardwareMap,"webcamRight");
    }
    public int findParkingSpace() {
        leftSpace = leftConeDetector.getParkingSpace();
        rightSpace = rightConeDetector.getParkingSpace();
        if (leftSpace != -1)
            parkingSpace = leftSpace;
        if (rightSpace != -1)
            parkingSpace = rightSpace;

     /*   if(leftSpace == -1) {
            if (rightSpace == -1) {
                return 2;
            } else {
                return rightSpace;
            }
        }
        else {
            return leftSpace;
        }*/
        return parkingSpace;
    }

    @Override
    public boolean shouldExit() {
      return autonOpMode.isStarted();
      //  return true;
    }

    @Override
    public void performStateActions() {
        findParkingSpace();
        telemetry.addData("Left Cone", leftConeDetector.getParkingSpace());
        telemetry.addData("Right Cone",rightConeDetector.getParkingSpace());
        telemetry.addData("Parking Space",parkingSpace);

    }

    @Override
    public void performTransitionalActions() {

        autonOpMode.parkingSpace = findParkingSpace();

        leftConeDetector.stopCamera();
        rightConeDetector.stopCamera();
    }
}
