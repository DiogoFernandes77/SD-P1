package Simulation.entities;


public class Pilot extends Thread{

    
    public enum State{
        AT_TRANSFER_GATE,
        READY_FOR_BOARDING,
        WAIT_FOR_BOARDING,
        FLYING_FORWARD,
        DEBOARDING,
        FLYING_BACK
    }

    public Pilot(){
        State pilot_state = State.AT_TRANSFER_GATE;
    }
    

    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
        //Pilot actions cycle
    }
}