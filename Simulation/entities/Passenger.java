package Simulation.entities;

import java.util.Random;
import java.util.concurrent.locks.Condition;

import Simulation.locations.DepartAirport;
import Simulation.locations.Plane;

public class Passenger extends Thread{
    private int id_passenger = 0;

    public enum State{
      GOING_TO_AIRPORT,
        IN_QUEUE,
        IN_FLIGHT,
        AT_DESTINATION
    }
    private State passenger_state;
    
    public Passenger(int id){
        passenger_state = State.GOING_TO_AIRPORT;
        id_passenger = id; 
    }

    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
        travelToAirport();
        enterQueue();
        waitInQueue();
        boardThePlane();
        inFlight();
    }

    //Passenger time to go to airport between 0 and 10 sec
    private void travelToAirport() {
        Random gen = new Random();
        int time = gen.nextInt(10);
        try{
            Thread.sleep(time * 1000); 
        }catch(Exception e){
            System.out.print("Error traveling to airport");
        }
        //System.out.printf("%d arrived at airport \n", id_passenger);
    }

    private void enterQueue(){
        passenger_state = State.IN_QUEUE;
        DepartAirport.getInstance().enterQueue(this);

        //System.out.printf(passenger_state + "%d\n",id_passenger);
    }
    private void waitInQueue(){
        passenger_state = State.IN_QUEUE;
        DepartAirport.getInstance().waitInQueue(this);
    }
    
    private void boardThePlane(){
        Plane.getInstance().boardThePlane(this);

    }
    private void inFlight(){
        passenger_state = State.IN_FLIGHT;
        Plane.getInstance().waitForEndOfFlight();
    }
    private void showDocuments(){

    }

    public int getId_passenger(){
    return id_passenger;
    }


}