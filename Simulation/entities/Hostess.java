package Simulation.entities;

import Simulation.locations.DepartAirport;
import Simulation.Start;
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
        prepareForPassBoarding();
        //este while pode ter o valor das variaveis desatualizado not sure yet
        while(getCurrent_capacity() < getBoardingMin()|| (getCurrent_capacity() < getBoardingMax() && getIsQueueEmpty())){
            waitForNextPassenger();
            checkDocuments(); 
        }
        System.out.print(" BOARDING COMPLETA");

        
    
    
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

    
    
    private int getBoardingMin(){
        return DepartAirport.getInstance().getBoardingMin();
    }
    private int getBoardingMax(){
        return DepartAirport.getInstance().getBoardingMax();
    }
    private int getCurrent_capacity(){
        return DepartAirport.getInstance().getCurrent_capacity();
    }
    private boolean getIsQueueEmpty(){

        return DepartAirport.getInstance().getIsQueueEmpty();
    }


}