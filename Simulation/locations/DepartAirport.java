package Simulation.locations;

import Simulation.Start;

public class DepartAirport {
    private static DepartAirport depArp_instance = null;
    
    
    private static int nPassenger, planeCapacity, boardMin, boardMax;

    
    
    //construct for the departure airport, know passenger, plane capacity, min and max of boarding
    private DepartAirport(){
        //Penso que esta é a melhor forma de passar as variaveis
        nPassenger = Start.n_passenger;
        planeCapacity = Start.plane_capacity;
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
    public void informPlaneReadyForBoarding(){}
    
    
    //waits for the passenger enter in the plane until hostess gives the singal
    public void waitForAllInBoarding(){}
   

    //---------------------------------------------------/Hostess methods/-----------------------------------------------------//
    
    
    //Hostess gets ready and waits untill first passenger
    public void prepareForPassBoarding(){}
 
    //Hostess check documents of the passenger in queue
    public void checkDocuments(){}

    //Hostess waits for the passengers
    public void waitForNextPassenger(){}
 
    //Hostess signals pilot that he can fly
    public void informPlaneReadyToTakeOff(){}
 
    //Hostess waits until next flight
    public void waitForNextFlight(){}
    
    
    

    

    
    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//

    //Passenger waits in the queue before showing docs
    public void waitInQueue(){}
    
    //Passenger shows documents
    public void showDocuments(){}
    
       
    
    // // Hostess gives permission to passenger after checking documents. 
    // private void boardThePlane(){
    //     //count passenger in the queue
    //     // see the first
    //     // check documents
    //     // if ok goes to plane until the full capacity is reached or the min is reached
    // }

 







}