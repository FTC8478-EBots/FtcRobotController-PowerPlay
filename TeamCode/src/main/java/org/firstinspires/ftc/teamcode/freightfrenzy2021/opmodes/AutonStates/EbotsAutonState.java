package org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates;

import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.EbotsAutonOpMode;
import org.firstinspires.ftc.teamcode.powerplay2022.opmodes.StateMoveToHubX2022;

public interface EbotsAutonState {

    public boolean shouldExit();

    public void performStateActions();

    public void performTransitionalActions();

    public static EbotsAutonState get(Class targetState, EbotsAutonOpMode autonOpMode) {
        EbotsAutonState newState = null;
        try {
            newState = (EbotsAutonState) targetState.getConstructors()[0].newInstance(autonOpMode);
        } catch (Exception e) { //todo; catch specific exceptions bad not good not great pretty bad do not do pro coder moment
            throw new RuntimeException("targetState not an EbotsAutonState");
        }
       return newState;
    }
}

