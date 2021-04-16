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
       
        do{
            informPlaneReadyForBoarding();
            waitForAllInBoarding();
            System.out.print("PILOT: GOING TO FLY \n" );
            flyToDestinationPoint();
            System.out.print("PILOT: Arrived \n" );
            announceArrival();
            flyToDeparturePoint();
            parkAtTransferGate();


        }while(stillPassenger());
        
        System.out.println("PILOT RUNS ENDED \n");
    
    }

    private void informPlaneReadyForBoarding(){
        
        
        
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

    
    private void flyToDeparturePoint(){
        pilot_state = State.FLYING_BACK;
        Plane.getInstance().flyToDeparturePoint();
    }
    
    private void parkAtTransferGate(){
        pilot_state = State.AT_TRANSFER_GATE;
        DepartAirport.getInstance().parkAtTransferGate();

    }
    
    
    
    
    
    public boolean stillPassenger(){
        return DepartAirport.getInstance().stillPassenger();
    }
    
    
    public int getFlight_passanger_number() {
        return this.flight_passanger_number;
    }

    
    
    
    
    
    
    public void setFlight_passanger_number(int flight_passanger_number) {
        this.flight_passanger_number = flight_passanger_number;
    }

}