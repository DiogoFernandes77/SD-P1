package Simulation.locations;

import java.util.ArrayList;

import Simulation.entities.Passenger;

public class DestAirport{
    
    private static DestAirport destArp_instance = null;
    private ArrayList<Passenger> passenger_arrived;
    private DestAirport(){
        passenger_arrived = new ArrayList<Passenger>();
    }

    public static DestAirport getInstance() {
        if (destArp_instance == null)
            destArp_instance = new DestAirport();
        return destArp_instance;
    }
    
    //---------------------------------------------------/Pilot methods/-----------------------------------------------------//

    //---------------------------------------------------/Hostess methods/-----------------------------------------------------//

    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//
    
    //Passenger death, adds himself to the list of passenger that arrived
    public void Passenger_death(Passenger person){
            passenger_arrived.add(person);

    }

} 