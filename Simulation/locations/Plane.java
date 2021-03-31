package Simulation.locations;

import Simulation.Start;
import Simulation.entities.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Plane  {
    // static variable single_instance of type Singleton
    private static Plane plane_instance = null;

    private ArrayList<Passenger> plane;
    private Pilot pilot;
    private boolean flying;
    
    private final Lock lock;
    //private final Condition arrived;
   
    private Plane(){
        plane = new ArrayList<Passenger>();
        flying = false;
        lock = new ReentrantLock();
        //arrived = lock.newCondition();
        
    }

    // static method to create instance of Singleton class
    public static Plane getInstance() {
        if (plane_instance == null)
            plane_instance = new Plane();
        return plane_instance;
    }
    

    //---------------------------------------------------/Pilot methods/-----------------------------------------------------//
    
    public void flyToDestinationPoint(){}
    
    public void announceArrival(){}
    
    public void flyToDeparturePoint(){}



    //---------------------------------------------------/Hostess methods/-----------------------------------------------------//





    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//

    public void boardThePlane(){}
    
    public void waitForEndOfFlight(){
        lock.lock();
        try{
           flying = true;
           System.out.print("Plane is flying \n");
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }
    }
    
    public void leaveThePlane(){}


}