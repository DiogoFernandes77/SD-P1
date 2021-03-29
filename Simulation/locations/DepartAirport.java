package Simulation.locations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Simulation.Start;
import Simulation.entities.Passenger;

public class DepartAirport {
    private static DepartAirport depArp_instance = null;
    
    
    private static int nPassenger, planeCapacity, boardMin, boardMax;
    private int current_capacity = 0;
    private final Lock lock;
    private final Condition waitingPlane, waitingPassenger,waitingCheck,waitingFly;
    private ArrayList<Condition> listrdyCheck =  new ArrayList<Condition>();
    private Queue<Passenger> queue ;
    private boolean plane_rdy = false;
    //construct for the departure airport, know passenger, plane capacity, min and max of boarding
    private DepartAirport(){
        lock = new ReentrantLock();
        
        
        queue = new LinkedList<Passenger>();
        waitingPlane = lock.newCondition();
        waitingPassenger = lock.newCondition();
        
        
        //Penso que esta é a melhor forma de passar as variaveis
        nPassenger = Start.n_passenger;
        planeCapacity = Start.plane_capacity;
        boardMin = Start.boarding_min;
        boardMax = Start.boarding_max;
        
        for(int i = 0; i < nPassenger;i++){
            listrdyCheck.add(lock.newCondition());
        }
    
    }

    public static DepartAirport getInstance() {
        if (depArp_instance == null)
        depArp_instance = new DepartAirport();
        return depArp_instance;
    }
    
    
    
    //---------------------------------------------------/Pilot methods/-----------------------------------------------------//
    
    //Signals Hostess that plane is ready to board
    public void informPlaneReadyForBoarding(){
        
        lock.lock();
        try{
            
            
            plane_rdy = true; 
            waitingPlane.signal();
            System.out.print("Plane is Ready \n");
            
                
        
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            
            lock.unlock();
        }

        
    }
     
    //waits for the passenger enter in the plane until hostess gives the singal
    public void waitForAllInBoarding(){
        lock.lock();
        try{
            
           waitingFly.await();
            
        
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }



    }
   
    //---------------------------------------------------/Hostess methods/-----------------------------------------------------//
    
    
    //Hostess gets ready and waits untill first passenger
    public void prepareForPassBoarding(){
        lock.lock();
        try{
           
           
            while(queue.isEmpty()){
                waitingPassenger.await();
            }
        
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }




    }
 
    //Hostess check documents of the passenger in queue
    public void checkDocuments(){



    }

    //Hostess waits for the passengers
    public void waitForNextPassenger(){
        lock.lock();
        try{
            
            while(current_capacity < boardMin || (current_capacity < boardMax && !queue.isEmpty())){
                waitingPassenger.await();
            }
            
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }




    }
 
    //Hostess signals pilot that he can fly
    public void informPlaneReadyToTakeOff(){}
 
    //Hostess waits until next flight
    public void waitForNextFlight(){
        lock.lock();
        try{
            
            while(!plane_rdy){
                waitingPlane.await();

            }
            
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            System.out.print("I have been signaled");
            lock.unlock();
        }
        




    }
    
    
    

    

    
    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//

    public void enterQueue(Passenger person){
        lock.lock();
        try{
            
            queue.add(person);
            
            
        
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }
    }
    
    //Passenger waits in the queue before showing docs
    public void waitInQueue(){   
        lock.lock();
        try{
            
            waitingPassenger.signal();
            waitingCheck.await();
            
        
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }


    }
    
    //Passenger shows documents
    public void showDocuments(){



    }
    
       
    
    

 







}