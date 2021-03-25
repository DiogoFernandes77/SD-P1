package Simulation.entities;


public class Hostess extends Thread{

    public enum State{
        WAIT_FOR_NEXT_FLIGHT,
        WAIT_FOR_PASSENGER,
        CHECK_PASSENGER,
        READY_TO_FLY
    }

    public Hostess(){
        Hostess.State hostess_state = Hostess.State.WAIT_FOR_NEXT_FLIGHT;

    }
}