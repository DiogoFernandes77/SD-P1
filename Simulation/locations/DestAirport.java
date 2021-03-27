package Simulation.locations;

public class DestAirport{
    
    private static DestAirport destArp_instance = null;
    
    
    
    private DestAirport(){
        
        
    }

    public static DestAirport getInstance() {
        if (destArp_instance == null)
        destArp_instance = new DestAirport();
        return destArp_instance;
    }
    
    //---------------------------------------------------/Pilot methods/-----------------------------------------------------//

    //---------------------------------------------------/Hostess methods/-----------------------------------------------------//

    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//
    
    //Passenger death 
    public void Passenger_death(){
        //kill every thread -> passenger that's is on the plane

    }
        

    


   
} 