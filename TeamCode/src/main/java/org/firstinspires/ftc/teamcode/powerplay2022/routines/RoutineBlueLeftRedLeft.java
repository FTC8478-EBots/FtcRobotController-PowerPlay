package org.firstinspires.ftc.teamcode.powerplay2022.routines;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateStrafeRight;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.autonroutines.EbotsAutonRoutine;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateCloseTalon;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone5;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorHigh;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorMedium;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackwardFromPole;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackwardBlueCones;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardTowardPole;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardBlueCones;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateRotateLeft135;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateRotateRight135;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateRotateRight45;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateStrafeLeft;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateOpenTalon;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorLow;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardStartAuton;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateRotateLeft45;

public class RoutineBlueLeftRedLeft extends EbotsAutonRoutine {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    final int numExtraCones = 0;
    public RoutineBlueLeftRedLeft(int parkingSpace){
        itinerary.add(StateElevatorCone5.class);
        itinerary.add(StateMoveForwardStartAuton.class);
        itinerary.add(StateRotateRight45.class);
        itinerary.add(StateElevatorHigh.class);
        itinerary.add(StateMoveForwardTowardPole.class);
        itinerary.add(StateOpenTalon.class);
        itinerary.add(StateMoveBackwardFromPole.class);
        itinerary.add(StateElevatorLow.class);
        //Center of square b blue cones
        for (int i = 0;i<numExtraCones;i++) {
            itinerary.add(StateRotateLeft135.class);
            itinerary.add(StateElevatorCone5.class);
            itinerary.add(StateMoveForwardBlueCones.class);
            //itinerary.add(StateStrafeLeft.class);
            //Grab cone 5
            itinerary.add(StateCloseTalon.class);
            itinerary.add(StateElevatorMedium.class);
            itinerary.add(StateMoveBackwardBlueCones.class);
            itinerary.add(StateRotateRight135.class);
            itinerary.add(StateElevatorHigh.class);
            itinerary.add(StateMoveForwardTowardPole.class);
            itinerary.add(StateOpenTalon.class);
            itinerary.add(StateMoveBackwardFromPole.class);
            itinerary.add(StateElevatorLow.class);
        }
        itinerary.add(StateRotateLeft45.class);
        //Cone dropped successfully
        if(parkingSpace == 1){
            itinerary.add(StateStrafeLeft.class);
            //itinerary.add(StateMoveForward.class);
        }else if(parkingSpace == 2){
            //itinerary.add(StateMoveForward.class);
        }else if(parkingSpace == 3){
            itinerary.add(StateStrafeRight.class);
            //itinerary.add(StateMoveForward.class);
        }else{
            System.out.println("Can't see parking space (error 404 not found)");
        }
    }
    //Parking
    //TODO: Fix parking
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Getters & Setters
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Class Methods
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Methods
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

}
