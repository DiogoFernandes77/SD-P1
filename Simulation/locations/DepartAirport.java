package Simulation.locations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Simulation.Start;
import Simulation.entities.Passenger;

public class DepartAirport {
    private static DepartAirport depArp_instance = null;
    
    
    private static int nPassenger, boardMin, boardMax;
    private int current_capacity = 0;
    private final Lock lock;
    private final Condition waitingPlane, waitingPassenger,waitingCheck,waitingFly,waitingShow;
    private Queue<Passenger> queue ;
    private HashMap<Integer,Condition> cond_map ;
    private HashMap<Integer,Boolean> rdyToCheck_map;
    private boolean plane_rdy = false;
    private boolean showing = false;
    //construct for the departure airport, know passenger, plane capacity, min and max of boarding
    private DepartAirport(){
        lock = new ReentrantLock();
        
        
        queue = new LinkedList<Passenger>();
        cond_map = new HashMap<Integer,Condition>();
        rdyToCheck_map = new HashMap<Integer,Boolean>();
        waitingPlane = lock.newCondition();
        waitingPassenger = lock.newCondition();
        waitingCheck = lock.newCondition();
        waitingShow = lock.newCondition();
        waitingFly = lock.newCondition();
        
        //Penso que esta é a melhor forma de passar as variaveis
        nPassenger = Start.n_passenger;
        boardMin = Start.boarding_min;
        boardMax = Start.boarding_max;
        
    
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
            
            current_capacity = 0;
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

        lock.lock();
        try{
            int person_id = queue.peek().getId_passenger();

            rdyToCheck_map.put(person_id, true);
            cond_map.get(person_id).signal();
            
            System.out.printf("Hostess is waiting to documents to be shown \n");
            while(!showing){
                waitingShow.await();  
            }
            queue.remove();
            showing = false;
            waitingPassenger.signal();
            current_capacity++;
            
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }


    }

    //Hostess waits for the passengers
    public void waitForNextPassenger(){
        lock.lock();
        try{
            
            waitingPassenger.signal();
            System.out.print("Hostess waiting passanger \n");
            while(queue.isEmpty()){
                
                
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
            System.out.print("I have been signaled \n");
            lock.unlock();
        }
        
    }
    
    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//

    public void enterQueue(Passenger person){
        lock.lock();
        try{
            System.out.printf("passenger %d enters queue \n", person.getId_passenger());
            queue.add(person);
            cond_map.put(person.getId_passenger(), lock.newCondition());
            rdyToCheck_map.put(person.getId_passenger(),false);
        
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }
    }
    
    //Passenger waits in the queue before showing docs
    public void waitInQueue(Passenger person){   
        lock.lock();
        try{
            
            waitingPassenger.signal();
            System.out.printf("passenger %d wait for check \n", person.getId_passenger());
            while(!rdyToCheck_map.get(person.getId_passenger())){
                
                
                cond_map.get(person.getId_passenger()).await();
            }
            
            showing = true;
            waitingShow.signal();
            
            System.out.printf("passenger %d  show documents \n", person.getId_passenger());

            
            //block state 2
            waitingPassenger.await();
            System.out.printf("passenger %d boarding plane \n", person.getId_passenger());
            
            
            
            
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }


    }
    
    //Passenger shows documents
    public void showDocuments(){
        lock.lock();
        try{
            
            waitingShow.signal();
            
            waitingPassenger.await();

            
            
        
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e.toString());
        }finally{
            lock.unlock();
        }


    }

    public int getBoardingMin(){
        return boardMin;
    }
    public int getBoardingMax(){
        return boardMax;
    }
    public int getCurrent_capacity(){
        return current_capacity;
    }
    public boolean getIsQueueEmpty(){

        return queue.isEmpty();
    }




}