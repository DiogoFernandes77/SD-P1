package Simulation.entities;

import Simulation.Log_file.Logger_Class;
import Simulation.locations.DepartAirport;
import Simulation.locations.Plane;

public class Hostess extends Thread{
    
    public enum State{
        WAIT_FOR_NEXT_FLIGHT,
        WAIT_FOR_PASSENGER,
        CHECK_PASSENGER,
        READY_TO_FLY
    }

    private State hostess_state;
    private boolean end_flag = false;
    public Hostess(){
        hostess_state = State.WAIT_FOR_NEXT_FLIGHT;
        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setST_Hostess(hostess_state);
        }
    }

    //implementation of the method run which establishes the thread operativeness
    @Override
    public void run(){
        do{
            
            waitForNextFlight();
            prepareForPassBoarding();
            //este while pode ter o valor das variaveis desatualizado not sure yet
            while(getCurrent_capacity() < getBoardingMin() || (getCurrent_capacity() < getBoardingMax() && !getIsQueueEmpty()) ){
                if(getPassenger_left() == 0){
                
                    System.out.println("LAST PASSENGER FLIGHT \n");
                    break;
                }
                waitForNextPassenger();
                checkDocuments(); 
            }
            System.out.print(" CHECK COMPLETE \n");
            
            while(getCurrent_capacity() != Plane.getInstance().getCapacity()){//garantir que os passageiros estao todos dentro do aviao antes de levantar voo, é raro entrar aqui mas pode acontecer
                waitBoarding();
            }
            
            System.out.print(" BOARDING COMPLETE \n");
            informPlaneReadyToTakeOff();
        }while(stillPassenger());
        
        System.out.println("HOSTESS RUNS ENDED \n");
    }

    private void waitForNextFlight(){
        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setST_Hostess(hostess_state);
            Logger_Class.getInstance().log_write("Hostess is waiting for next flight");
        }
        DepartAirport.getInstance().waitForNextFlight();
    }
    private void prepareForPassBoarding(){
        hostess_state = State.WAIT_FOR_PASSENGER;
        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setST_Hostess(hostess_state);
            Logger_Class.getInstance().log_write("Hostess is waiting for next passenger");
        }
        DepartAirport.getInstance().prepareForPassBoarding();
    }
    private void waitForNextPassenger(){
        hostess_state = State.WAIT_FOR_PASSENGER;
        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setST_Hostess(hostess_state);
            Logger_Class.getInstance().log_write("Hostess is waiting for next passenger");
        }
        DepartAirport.getInstance().waitForNextPassenger();
    }

    private void checkDocuments(){
        hostess_state = State.CHECK_PASSENGER;
        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setST_Hostess(hostess_state);
            Logger_Class.getInstance().log_write("Hostess is checking documents of passengers");
        }
        DepartAirport.getInstance().checkDocuments();
    }

    private void waitBoarding(){
       Plane.getInstance().waitBoarding();
   }
    
    private void informPlaneReadyToTakeOff(){
        hostess_state = State.READY_TO_FLY;
        synchronized (Logger_Class.class)
        {
            Logger_Class.getInstance().setST_Hostess(hostess_state);
            Logger_Class.getInstance().log_write("Hostess is ready to fly");
        }
        DepartAirport.getInstance().informPlaneReadyToTakeOff();
    }
    private int getPassenger_left(){ return DepartAirport.getInstance().getPassenger_left(); }
    private boolean stillPassenger(){
        return DepartAirport.getInstance().stillPassenger();
    }
    private int getBoardingMin(){
        return DepartAirport.getInstance().getBoardingMin();
    }
    private int getBoardingMax(){
        return DepartAirport.getInstance().getBoardingMax();
    }
    private int getCurrent_capacity(){
        return DepartAirport.getInstance().getCurrent_capacity();
    }
    private boolean getIsQueueEmpty(){
        return DepartAirport.getInstance().getIsQueueEmpty();
    }

}