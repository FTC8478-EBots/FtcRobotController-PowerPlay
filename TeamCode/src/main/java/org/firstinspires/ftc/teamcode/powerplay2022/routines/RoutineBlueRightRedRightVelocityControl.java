package org.firstinspires.ftc.teamcode.powerplay2022.routines;

import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.autonroutines.EbotsAutonRoutine;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateBackOffConeStackRight;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateCloseTalon;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone1;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone2;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone3;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone4;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone5;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorHigh;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorLow;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorMedium;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackwardWithVelocityControlBlueConesLeft;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackwardWithVelocityControlBlueConesRight;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackwardWithVelocityControlFromPoleLeft;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackwardWithVelocityControlFromPoleRight;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardWithVelocityControlBlueConesLeft;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardWithVelocityControlBlueConesRight;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardWithVelocityControlStartAuton;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardWithVelocityControlTowardPoleLeft;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardWithVelocityControlTowardPoleRight;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateOpenTalon;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StatePivot45;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StatePivot90;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StatePivotNeg45;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StatePivotNeg90;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateStrafeLeftWithVelocityControl;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateStrafeRightWithVelocityControl;

public class RoutineBlueRightRedRightVelocityControl extends EbotsAutonRoutine {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    final int numExtraCones = 1;
    public RoutineBlueRightRedRightVelocityControl(int parkingSpace){
        itinerary.add(StateCloseTalon.class);
        itinerary.add(StateElevatorCone5.class);
        itinerary.add(StateMoveForwardWithVelocityControlStartAuton.class);
        itinerary.add(StatePivot45.class);
        itinerary.add(StateElevatorHigh.class);
        itinerary.add(StateMoveForwardWithVelocityControlTowardPoleRight.class);
        itinerary.add(StateOpenTalon.class);
        itinerary.add(StateMoveBackwardWithVelocityControlFromPoleRight.class);
        itinerary.add(StateElevatorLow.class);
        //Center of square b blue cones
        for (int i = 0;i<numExtraCones;i++) {
            itinerary.add(StatePivotNeg90.class);
            if (i == 0){
                itinerary.add(StateElevatorCone5.class);
            }
            if (i == 1){
                itinerary.add(StateElevatorCone4.class);
            }
            if (i == 2){
                itinerary.add(StateElevatorCone3.class);
            }
            if (i == 3){
                itinerary.add(StateElevatorCone2.class);
            }
            if (i == 4){
                itinerary.add(StateElevatorCone1.class);
            }
            itinerary.add(StateMoveForwardWithVelocityControlBlueConesRight.class);

            //Grab cone from stack


            itinerary.add(StateCloseTalon.class);
            itinerary.add(StateBackOffConeStackRight.class);
            itinerary.add(StateElevatorMedium.class);
            itinerary.add(StateMoveBackwardWithVelocityControlBlueConesRight.class);
            itinerary.add(StatePivot45.class);
            itinerary.add(StateElevatorHigh.class);
            itinerary.add(StateMoveForwardWithVelocityControlTowardPoleRight.class);
            itinerary.add(StateOpenTalon.class);
            itinerary.add(StateMoveBackwardWithVelocityControlFromPoleRight.class);
            itinerary.add(StateElevatorLow.class);
        }
        //itinerary.add(StateRotateLeft45.class);
        //Cone dropped successfully
        if(parkingSpace == 1){
            itinerary.add(StateStrafeLeftWithVelocityControl.class);
            //itinerary.add(StateMoveForward.class);
        }else if(parkingSpace == 2){
            //itinerary.add(StateMoveForward.class);
        }else if(parkingSpace == 3){
            itinerary.add(StateStrafeRightWithVelocityControl.class);
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
