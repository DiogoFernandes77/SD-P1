package Simulation.entities;

public class Passenger extends Thread{

    private static int id_passenger = 0;

    public enum State{
      GOING_TO_AIRPORT,
        IN_QUEUE,
        IN_FLIGHT,
        AT_DESTINATION
    }

    public Passenger(int id){
        State passenger_state = State.GOING_TO_AIRPORT;
        id_passenger = id; 
    }



    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
        //Pilot actions cycle
    }




}