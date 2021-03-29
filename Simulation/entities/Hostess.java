package Simulation.entities;

import Simulation.locations.DepartAirport;

public class Hostess extends Thread{

    public enum State{
        WAIT_FOR_NEXT_FLIGHT,
        WAIT_FOR_PASSENGER,
        CHECK_PASSENGER,
        READY_TO_FLY
    }
    private State hostess_state;
    public Hostess(){
        hostess_state = State.WAIT_FOR_NEXT_FLIGHT;

    }




   
    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
        waitForNextFlight();

        
    
    
    }


    private void waitForNextFlight(){
        DepartAirport.getInstance().waitForNextFlight();

    }
    private void prepareForPassBoarding(){
        hostess_state = State.WAIT_FOR_PASSENGER;
        DepartAirport.getInstance().prepareForPassBoarding();

    }
    private void waitForNextPassenger(){
        hostess_state = State.WAIT_FOR_PASSENGER;
        DepartAirport.getInstance().waitForNextPassenger();


    }

    private void checkDocuments(){
        hostess_state = State.CHECK_PASSENGER;
        DepartAirport.getInstance().checkDocuments();

    }










}