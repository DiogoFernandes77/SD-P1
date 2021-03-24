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
    private State pilot_state;

    public Pilot(){
        pilot_state = State.AT_TRANSFER_GATE;
    }


}