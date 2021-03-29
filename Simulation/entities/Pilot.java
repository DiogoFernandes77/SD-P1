package Simulation.entities;

import Simulation.locations.DepartAirport;

public class Pilot extends Thread{

    
    public enum State{
        AT_TRANSFER_GATE,
        READY_FOR_BOARDING,
        WAIT_FOR_BOARDING,
        FLYING_FORWARD,
        DEBOARDING,
        FLYING_BACK
    }
    private State pilot_state;
    public Pilot(){
        pilot_state = State.AT_TRANSFER_GATE;
    }
    

    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
       

        informPlaneReadyForBoarding();
        //waitForAllInBoarding();


    }

    private void informPlaneReadyForBoarding(){
        try{
            Thread.sleep(2000);
        }catch(Exception e){}
        pilot_state = State.READY_FOR_BOARDING;
        DepartAirport.getInstance().informPlaneReadyForBoarding();
        
    }
    private void waitForAllInBoarding(){
        pilot_state = State.READY_FOR_BOARDING;
        DepartAirport.getInstance().waitForAllInBoarding();
    }





}