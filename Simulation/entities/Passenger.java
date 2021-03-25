package Simulation.entities;

public class Passenger extends Thread{

    int id_passenger = 0;

    public enum State{
      GOING_TO_AIRPORT,
        IN_QUEUE,
        IN_FLIGHT,
        AT_DESTINATION
    }

    public Passenger(){
        State passenger_state = State.GOING_TO_AIRPORT;

    }

}