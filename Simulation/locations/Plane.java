package Simulation.locations;

import Simulation.Start;
import Simulation.entities.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class Plane  {
    // static variable single_instance of type Singleton
    private static Plane plane_instance = null;

    private ArrayList<Passenger> plane;
    private Pilot pilot;
    private Condition flying;
    private Condition hostess;
    private Condition cd_deboarding;
    private int flight_id = 0;
    private final Lock lock;
    private boolean enter = false;
    private boolean plane_flying = false;
    

    Random gen = new Random();
    //private final Condition arrived;
    
   
    private Plane(){
        plane = new ArrayList<Passenger>();
        lock = new ReentrantLock();
        flying = lock.newCondition();
        hostess = lock.newCondition();
        cd_deboarding = lock.newCondition();
        
    }

    // static method to create instance of Singleton class
    public static Plane getInstance() {
        if (plane_instance == null)
            plane_instance = new Plane();
        return plane_instance;
    }
    

    //---------------------------------------------------/Pilot methods/-----------------------------------------------------//
    
    public void flyToDestinationPoint(){
        lock.lock();
        int delay = gen.nextInt(3);
        try{
            
            flying.await(delay, TimeUnit.SECONDS);
            
        }catch(Exception e){
             System.out.println("Interrupter Exception Error - " + e.toString());
         }finally{
            lock.unlock();
         }



    }
    
    public void announceArrival(){
        lock.lock();
        
        try{
            plane_flying = false;
            flying.signalAll();
            while(!plane.isEmpty()){
                cd_deboarding.await();
            }
            System.out.println("PILOT: deboarding complete \n");
            
        }catch(Exception e){
             System.out.println("Interrupter Exception Error - " + e.toString());
         }finally{
            lock.unlock();
         }




    }
    
    public void flyToDeparturePoint(){
        lock.lock();
        int delay = gen.nextInt(3);
        try{
            System.out.println("PILOT: Flying back \n");
            flying.await(delay, TimeUnit.SECONDS);
            
        }catch(Exception e){
             System.out.println("Interrupter Exception Error - " + e.toString());
         }finally{
            lock.unlock();
         }

    }

    


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
            plane_flying = true;
            while(plane_flying){
               flying.await(); 
            }
            
        
            



        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }
    }
    
    public void leaveThePlane(Passenger person){
        lock.lock();
        try{
            
            plane.remove(person);
            cd_deboarding.signal();
            System.out.printf("Passenger %d leaving the plane \n", person.getId_passenger());
        
            



        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }


    }



    //---------------------------------------------------/getters/setters/-----------------------------------------------------//

    public int getCapacity(){
        return plane.size();
    }



}