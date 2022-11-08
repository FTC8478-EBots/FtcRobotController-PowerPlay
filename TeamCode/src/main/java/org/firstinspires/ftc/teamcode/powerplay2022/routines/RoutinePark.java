package org.firstinspires.ftc.teamcode.powerplay2022.routines;
//Seanlandia
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateStrafeRight;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.autonroutines.EbotsAutonRoutine;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForward;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateStrafeLeft;

public class RoutinePark extends EbotsAutonRoutine {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public RoutinePark(int parkingSpace){
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
