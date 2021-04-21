package Simulation.entities;

import java.util.ArrayList;
import java.util.Random;

import Simulation.Log_file.Logger_Class;
import Simulation.locations.DepartAirport;
import Simulation.locations.DestAirport;
import Simulation.locations.Plane;

public class Passenger extends Thread{
    private int id_passenger = 0;
    private int count_AtDest = 0;

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
        synchronized (Logger_Class.class) {
            Logger_Class.getInstance().setST_Passenger(id_passenger, passenger_state);
          //  Logger_Class.getInstance().log_write("Passenger State");
        }

    }

    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
        travelToAirport();
        enterQueue();
        waitInQueue();
        showDocuments();
        boardThePlane();
        waitForEndOfFlight();
        leaveThePlane();
        death();
    }

    //Passenger time to go to airport between 0 and 10 sec
    private void travelToAirport() {
        Random gen = new Random();
        int time = gen.nextInt(10);
        try{
            Thread.sleep(time * 100); 
        }catch(Exception e){
            System.out.print("Error traveling to airport");
        }
        //System.out.printf("%d arrived at airport \n", id_passenger);
    }

    private void enterQueue(){
        passenger_state = State.IN_QUEUE;
        synchronized (Logger_Class.class) {
            Logger_Class.getInstance().setST_Passenger(id_passenger, passenger_state);

            Logger_Class.getInstance().log_write("Passenger " + id_passenger + " is entering in queue");
        }
        DepartAirport.getInstance().enterQueue(this);

        //System.out.printf(passenger_state + "%d\n",id_passenger);
    }
    private void waitInQueue(){
        passenger_state = State.IN_QUEUE;
        synchronized (Logger_Class.class) {
            Logger_Class.getInstance().setST_Passenger(id_passenger, passenger_state);
            Logger_Class.getInstance().log_write("Passenger " + id_passenger + " is in queue");
        }
        DepartAirport.getInstance().waitInQueue(this);
    }
    private void showDocuments(){
        DepartAirport.getInstance().showDocuments(this);


        
    }





    private void boardThePlane(){
        Plane.getInstance().boardThePlane(this);

    }
    private void waitForEndOfFlight(){
        passenger_state = State.IN_FLIGHT;
        synchronized (Logger_Class.class) {
            Logger_Class.getInstance().setST_Passenger(id_passenger, passenger_state);
            Logger_Class.getInstance().log_write("Passenger " + id_passenger + " is in flight");
        }
        Plane.getInstance().waitForEndOfFlight();
    }
    

    private void leaveThePlane(){
        
        Plane.getInstance().leaveThePlane(this);
    }
    private void death(){
        
        passenger_state = State.AT_DESTINATION;

        synchronized (Logger_Class.class) {
            Logger_Class.getInstance().setST_Passenger(id_passenger, passenger_state);
            Logger_Class.getInstance().log_write("Passenger " + id_passenger + " is at destination");
        }
        
        DestAirport.getInstance().Passenger_death(this);
    }

    public int getId_passenger(){
    return id_passenger;
    }

}