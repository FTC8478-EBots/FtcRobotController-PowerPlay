package org.firstinspires.ftc.teamcode.powerplay2022.routines;

import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateDumpFreight;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateEnterWarehouseForCollectVelocityControl;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateMoveToHubY;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StatePushOffAllianceHub;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StatePushOffWithEncoders;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateReverseToHubUsingEncoders;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateRotateToFieldCenter;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateRotateToZeroDegreesV2;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateStrafeRight;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.AutonStates.StateStrafeToTouchWall;
import org.firstinspires.ftc.teamcode.freightfrenzy2021.opmodes.autonroutines.EbotsAutonRoutine;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveBackward;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForward;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateMoveForwardWithVelocityControl;
import org.firstinspires.ftc.teamcode.powerplay2022.states.StateStrafeLeft;

public class RoutineCenterPost extends EbotsAutonRoutine {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public RoutineCenterPost(){
        itinerary.add(StateMoveForward.class);
        //itinerary.add(StateMoveForwardWithVelocityControl.class);
        //Back left motor is the problem
        itinerary.add(StateStrafeRight.class);
        //////////////////////////////////////itinerary.add(StateMoveToHubY.class);
      //  itinerary.add(StateStrafeLeft.class);
        //itinerary.add(StateMoveBackward.class);
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
