/**
 *  Log Class to produce log file each initiation
 *  @author António Ramos e Diogo Fernandes
 */

package Simulation.locations;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Simulation.Log_file.Logger_Class;
import Simulation.Start;
import Simulation.entities.Passenger;

public class DepartAirport {
    private static DepartAirport depArp_instance = null;

    private static int nPassenger, boardMin, boardMax;
    private int current_capacity = 0;
    private int passenger_left;

    private final Lock lock;
    private final Condition waitingPlane, waitingPassenger,waitingCheck,waitingFly,waitingShow;
    private final Queue<Integer> queue ;
    
    private boolean plane_rdy = false;
    private boolean showing = false;
    private boolean rdyCheck = false;
    private boolean boardingComplete = false;
    private boolean block_state2 = false;
    //construct for the departure airport, know passenger, plane capacity, min and max of boarding
    private DepartAirport(){
        lock = new ReentrantLock();

        queue = new LinkedList<>();
        waitingPlane = lock.newCondition();
        waitingPassenger = lock.newCondition();
        waitingCheck = lock.newCondition();
        waitingShow = lock.newCondition();
        waitingFly = lock.newCondition();
        
        nPassenger = Start.n_passenger;
        boardMin = Start.boarding_min;
        boardMax = Start.boarding_max;
        passenger_left = nPassenger;

        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setQ(queue);
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
            current_capacity = 0;
            plane_rdy = true; 
            waitingPlane.signal();
            System.out.print("Plane is Ready \n");
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
     
    //waits for the passenger enter in the plane until hostess gives the signal
    public void waitForAllInBoarding( ){
        lock.lock();
        try{
            while(!boardingComplete){
                waitingFly.await();   
           }
           boardingComplete = false; 
           
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public void parkAtTransferGate(){
        lock.lock();
        try{
            System.out.println("PILOT: parking plane \n");
            System.out.printf("PILOT: passenger left = %d \n", passenger_left);
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
         }
    }
   
    //---------------------------------------------------/Hostess methods/-----------------------------------------------------//

    //Hostess gets ready and waits until first passenger
    public void prepareForPassBoarding(){
        lock.lock();
        try{
            System.out.print("Prepare for Boarding! \n");
            while(queue.isEmpty()){
                waitingPassenger.await();
            }
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
 
    //Hostess check documents of the passenger in queue
    public void checkDocuments(){
        lock.lock();
        try{
            int person_id = queue.peek();

            rdyCheck = true;
            waitingCheck.signalAll();
            
            System.out.println("Hostess is waiting to documents to be shown \n");
            while(!showing){
                waitingShow.await();  
            }
            queue.remove();
            showing = false;
            rdyCheck = false;
            waitingPassenger.signal();
            current_capacity++;
            passenger_left--;
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    //Hostess waits for the passengers
    public void waitForNextPassenger(){
        lock.lock();
        try{
            block_state2 = true;
            waitingPassenger.signal();
            System.out.print("Hostess waiting passenger \n");
            while(queue.isEmpty()){
                waitingPassenger.await(); 
            }
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
 
    //Hostess signals pilot that he can fly
    public void informPlaneReadyToTakeOff(){
        lock.lock();
        try{
            boardingComplete = true;
            waitingFly.signal();
            synchronized(Logger_Class.class){
                Logger_Class.getInstance().departed(current_capacity);
            }
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
 
    //Hostess waits until next flight
    public void waitForNextFlight(){
        lock.lock();
        try{
            while(!plane_rdy){
                waitingPlane.await();
            }
            plane_rdy = false;
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    
    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//

    public void enterQueue(int person){
        lock.lock();
        try{
            System.out.printf("passenger %d enters queue \n", person);
            queue.add(person);
            synchronized (Logger_Class.class) {
                Logger_Class.getInstance().setQ(queue);
            }
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    
    //Passenger waits in the queue before showing docs
    public void waitInQueue(int person){   
        lock.lock();
        try{
            waitingPassenger.signal();
            System.out.printf("passenger %d wait for check \n", person);
            while(!(rdyCheck && (queue.peek() == person))){// each thread see if hostess is ready and if is their turn
                waitingCheck.await();
            }
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    
    //Passenger shows documents
    public void showDocuments(int person){
        lock.lock();
        try{
            showing = true;
            waitingShow.signal();
            System.out.printf("passenger %d  show documents \n", person);
            synchronized(Logger_Class.class){
                Logger_Class.getInstance().pass_check( ": passenger " + person + " checked.\n");
            }
            //block state 2
            while(!block_state2){
                waitingPassenger.await(); 
            }
            block_state2 = false;
        }catch(Exception e){
            System.out.println("Interrupter Exception Error - " + e);
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
 //---------------------------------------------------/getters/setters/-----------------------------------------------------//
    public int getPassenger_left() {
    return this.passenger_left;
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
    public boolean stillPassenger(){ return (passenger_left != 0); }
}
