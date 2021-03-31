package Simulation;
import Logging_Package.Logging;
import Simulation.entities.*;
import Simulation.locations.*;

import java.util.logging.Logger;

public class Start{
    public static int n_passenger,boarding_max,boarding_min;

    public static void main(String[] args) throws InterruptedException{
        String file_name = "logger.txt"; // logger file

        if(args.length == 3){//custom config
            try{
                n_passenger = Integer.parseInt(args[0]);
                boarding_max = Integer.parseInt(args[1]);
                boarding_min = Integer.parseInt(args[2]);
            }catch(Exception e){
                System.out.print("Args must be numbers \n");
                System.exit(1);
            }
           System.out.print("Config Ok \n");
        }else if(args.length == 0){//default config
            n_passenger = 21;
            boarding_max = 8;
            boarding_min = 5;
            System.out.print("Config Ok \n");
        }else{
            System.out.print("Arguments missing/wrong \n");
            System.out.print("N_max_passengers boarding_max boarding_min\n");
            System.exit(1);
        }

        // create and write log file
        Logging logger = new Logging(file_name); // write logs of a application

        //Initializing locations
        DepartAirport departAirport = DepartAirport.getInstance();
        DestAirport destAirport = DestAirport.getInstance();
        Plane plane = Plane.getInstance();
        
        //Initializing entities Threads
        Pilot pil = new Pilot();
        Hostess hos = new Hostess();
        logger.add_struct(n_passenger);
        Passenger[] passengers = new Passenger[n_passenger];
        for (int id = 0; id < n_passenger; id++){
            passengers[id] = new Passenger(id);
        }

        //Start entities Thread
        pil.start();
        hos.start();
        for (int id = 0; id < n_passenger; id++){
            passengers[id].start();
        }
    
        // Join the threads
        for(int id = 0; id < n_passenger; id++) {
            try {
                passengers[id].join();
            } catch (InterruptedException ex) {
                System.out.println("Interrupter Exception Error - " + ex.toString());
            }
        }
        
        try{
            pil.join();
            hos.join();
        }catch(InterruptedException ex){
            System.out.println("Interrupter Exception Error - " + ex.toString());
        }


    }
}