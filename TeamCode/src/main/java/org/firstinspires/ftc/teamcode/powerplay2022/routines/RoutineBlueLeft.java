package org.firstinspires.ftc.teamcode.powerplay2022.routines;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateStrafeRight;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.autonroutines.EbotsAutonRoutine;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorCone5;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorHigh;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackward;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForward;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateStrafeLeft;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateOpenTalon;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateElevatorLow;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateCloseTalon;

public class RoutineBlueLeft extends EbotsAutonRoutine {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public RoutineBlueLeft(int parkingSpace){
        //itinerary.add(StateMoveForward.class);
        //itinerary.add(StateMoveForward.class);
        //itinerary.add(StateMoveBackward.class);
        itinerary.add(StateElevatorHigh.class);
        itinerary.add(StateOpenTalon.class);
        itinerary.add(StateElevatorLow.class);
        itinerary.add(StateCloseTalon.class);
        itinerary.add(StateElevatorHigh.class);
        itinerary.add(StateOpenTalon.class);
        itinerary.add(StateElevatorLow.class);
        itinerary.add(StateCloseTalon.class);
        itinerary.add(StateElevatorHigh.class);
        itinerary.add(StateOpenTalon.class);
        itinerary.add(StateElevatorLow.class);
        itinerary.add(StateCloseTalon.class);
        //itinerary.add(StateElevatorCone5.class);
        //itinerary.add(StateStrafeLeft.class);
        //itinerary.add()
        //itinerary.add()
        if(parkingSpace == 1){
            itinerary.add(StateStrafeLeft.class);
            itinerary.add(StateMoveForward.class);
        }else if(parkingSpace == 2){
            itinerary.add(StateMoveForward.class);
        }else if(parkingSpace == 3){
            itinerary.add(StateStrafeRight.class);
            itinerary.add(StateMoveForward.class);
        }else{
            System.out.println("Can't see parking space (error 404 not found)");
        }
    }
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
