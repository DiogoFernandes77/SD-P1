package Simulation;
import Simulation.entities.*;
import Simulation.locations.*;

public class Start{

    public static int n_passenger;
    public static int plane_capacity;
    public static int boarding_min;
    public static int boarding_max;

    public static void main(String[] args) throws InterruptedException{
            
        if(args.length == 4){//custom config
            try{
                n_passenger = Integer.parseInt(args[0]);
                plane_capacity = Integer.parseInt(args[1]);
                boarding_max = Integer.parseInt(args[2]);
                boarding_min = Integer.parseInt(args[3]);
                
            }catch(Exception e){
                System.out.print("Args must be numbers \n");
                System.exit(1);
            }
           System.out.print("Config Ok \n");

        }else if(args.length == 0){//default config
            n_passenger = 21;
            plane_capacity = 10;
            boarding_max = 8;
            boarding_min = 5;
            System.out.print("Config Ok \n");

        }else{
            System.out.print("Arguments missing/wrong \n");
            System.exit(1);
        }



        
        
    }




}