package Simulation.entities;

import Simulation.locations.DepartAirport;
import Simulation.locations.Plane;

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
    private int flight_passanger_number;

   
    
    
    public Pilot(){
        pilot_state = State.AT_TRANSFER_GATE;
    }
    

    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
       

        informPlaneReadyForBoarding();
        waitForAllInBoarding();
        System.out.print("PILOT: GOING TO FLY \n" );
        flyToDestinationPoint();
        System.out.print("PILOT: ARrived \n" );
        announceArrival();


    }

    private void informPlaneReadyForBoarding(){
        try{
            Thread.sleep(2000);
        }catch(Exception e){}
        pilot_state = State.READY_FOR_BOARDING;
        DepartAirport.getInstance().informPlaneReadyForBoarding();
        
    }
    private void waitForAllInBoarding(){
        pilot_state = State.WAIT_FOR_BOARDING;
        DepartAirport.getInstance().waitForAllInBoarding();
    }
    private void flyToDestinationPoint(){
        pilot_state = State.FLYING_FORWARD;
        Plane.getInstance().flyToDestinationPoint();
    }
    private void announceArrival(){
        pilot_state = State.DEBOARDING;
        Plane.getInstance().announceArrival();
    }

    public int getFlight_passanger_number() {
        return this.flight_passanger_number;
    }

    public void setFlight_passanger_number(int flight_passanger_number) {
        this.flight_passanger_number = flight_passanger_number;
    }

}