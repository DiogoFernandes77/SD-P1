/**
 *  Log Class to produce log file each initiation
 *  @author António Ramos e Diogo Fernandes
 */

package Simulation.locations;

import java.util.ArrayList;
import java.util.logging.Logger;

import Simulation.Log_file.Logger_Class;
import Simulation.entities.Passenger;

public class DestAirport{
    private static DestAirport destArp_instance = null;
    private final ArrayList<Passenger> passenger_arrived;
    private DestAirport(){
        passenger_arrived = new ArrayList<Passenger>();
        synchronized(Logger_Class.class){
            Logger_Class.getInstance().setATL(passenger_arrived);
        }
    }

    public static DestAirport getInstance() {
        if (destArp_instance == null)
            destArp_instance = new DestAirport();
        return destArp_instance;
    }

    //---------------------------------------------------/Passenger methods/-----------------------------------------------------//
    
    //Passenger death, adds himself to the list of passenger that arrived
    public void Passenger_death(Passenger person){ passenger_arrived.add(person); }

} 