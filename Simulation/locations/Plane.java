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
    private Condition flying;
    private Condition hostess;
    private int flight_id = 0;
    private final Lock lock;
    private boolean enter = false;
    
    //private final Condition arrived;
   
    private Plane(){
        plane = new ArrayList<Passenger>();
        lock = new ReentrantLock();
        flying = lock.newCondition();
        hostess = lock.newCondition();
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


    public void waitBoarding(){
        lock.lock();
        try{
            while(!enter){
                hostess.await();
            }
            enter = false;


         }catch(Exception e){
             System.out.println("Interrupter Exception Error - " + e.toString());
         }finally{
            lock.unlock();
         }
    }


    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//

    public void boardThePlane(Passenger person){
        lock.lock();
        try{
            plane.add(person);
            enter = true;
            hostess.signal();
            System.out.printf("passenger %d boarding plane \n", person.getId_passenger());



         }catch(Exception e){
             System.out.println("Interrupter Exception Error - " + e.toString());
         }finally{
             lock.unlock();
         }


    }
    
    public void waitForEndOfFlight(){
        lock.lock();
        try{
            
            flying.await();
        
            



        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }
    }
    
    public void leaveThePlane(){}



    //---------------------------------------------------/getters/setters/-----------------------------------------------------//

    public int getCapacity(){
        return plane.size();
    }



}