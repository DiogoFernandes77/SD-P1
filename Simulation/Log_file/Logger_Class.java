/**
 *  Log Class to produce log file each initiation
 *  @author António Ramos e Diogo Fernandes
 */

package Simulation.Log_file;

import Simulation.Start;
import Simulation.entities.Hostess;
import Simulation.entities.Passenger;
import Simulation.entities.Pilot;

import java.io.*;
import java.util.ArrayList;
import java.util.Queue;

public class Logger_Class {
    //implements singleton for repository information about what happens in the simulation, gives output file
    private static Logger_Class loggerClass = null;

    private static String file_name; // name file
    private final static String directory_file = "Simulation/Log_file/"; // where output files is stored
    private final static String default_name = "Logger_"; //default name
    private final static String extension_file = ".txt"; //extension file

    public ArrayList<String> Summary = new ArrayList<>();

    //auxiliar variables
    int FN; // number of flight
    public Passenger.State[] ST_Passenger; // State of the Passengers; save state of each passenger
    public Pilot.State ST_Pilot;
    public Hostess.State ST_Hostess;

    private ArrayList<Passenger> Q; // State of the waiting queue
    private ArrayList<Passenger> IN_F; // State of in flight
    private ArrayList<Passenger> ATL; // State of number of passengers that have already arrived at their destination

    private static FileWriter fileWriter;

    //pilot variables abbreviate
    public String[] Pilot_state = new String[] {"ATRG", "RDFB","WTFB", "FLFW", "DRPP", "FLBK"};

    //hostess variables abbreviate
    public String[] Hostess_state = new String[] {"WTFL", "WTPS", "CKPS", "RDTF"};

    //passenger variables abbreviate
    private String[] Passenger_state = new String[]{"GTAP", "INQE", "INFL", "ATDS"};


    //creation file
    public String createFile()
    {
       int file_id = checkFiles(); // check if exists files previously created
       file_name = directory_file + default_name + (file_id + 1) + extension_file; //

       try {
           fileWriter = new FileWriter(file_name);
           fileWriter.close();
       } catch (IOException e) {
           e.printStackTrace();
       }

        return file_name;
    }

    //check current files
    private static int checkFiles() {
        int fileId = 0;
        File dir = new File(directory_file);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowerCaseName = name.toLowerCase();
                return lowerCaseName.endsWith(extension_file);
            }
        };

        String files[] = dir.list(filter);
        if(files.length == 0){
            System.out.println("Logger files not created\n");
            fileId = 0;
        }
        else{
            for(String fileName : files){
                String fullName[] = fileName.split(extension_file);
                String name_File = fullName[0];
                String[] get_number = name_File.split("_");
                fileId = Integer.parseInt(get_number[1]);
            }
        }

        return fileId;
    }

    public Logger_Class() { ST_Passenger = new Passenger.State[Start.n_passenger]; }

    // Implements singleton
    public static Logger_Class getInstance(){
        if (loggerClass == null)
        {
            loggerClass = new Logger_Class();
        }
        return loggerClass;
    }

    // start writing head of file
    public void init(){
        String file_name = createFile();
        add_struct(file_name, Start.n_passenger);   //header file
    }

    //header file
    public void add_struct(String file_n, int n_passenger){
        try {
            fileWriter = new FileWriter(file_n);
            fileWriter.write("PT \t HT  ");
            for (int i = 0; i < n_passenger; i++) {
                if (i < 10)
                    fileWriter.write(" P0" + i + " ");
                else
                    fileWriter.write(" P" + i + " ");
            }
            fileWriter.write("\tInQ InF PTAL\n");
            fileWriter.write(Pilot_state[0] + " " + Hostess_state[0] + " ");
            for(int i = 0; i < n_passenger; i ++){
                fileWriter.write(Passenger_state[0] + " ");
            }
            fileWriter.write("\t0\t0\t0\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // write on flight struct of Flight x: boarding started.
    // x is the number of the flight
    public void board_start(String text){
        try {
            fileWriter = new FileWriter(file_name, true);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //add struct Flight x: passenger y checked.
    public void pass_check(String text){
        try {
            fileWriter = new FileWriter(file_name, true);
            fileWriter.write("\nFlight " + FN + text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void departed(int total_transported){
        //" departed with " + current_capacity + " passengers.\n"
        try {
            fileWriter = new FileWriter(file_name, true);
            fileWriter.write("\nFlight " + FN + " departed with " + total_transported + " passengers.\n");
            Summary.add("Flight " + FN + " departed with " + total_transported + " passengers.");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void summary(){
        try {
            fileWriter = new FileWriter(file_name, true);
            fileWriter.write("\nAirlift sum up:\n");
            for (String s : Summary)
                fileWriter.write(s + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //when change main events
    public void log_write(String type){

        try {
            fileWriter = new FileWriter(file_name, true);
            StringBuilder struct_string = new StringBuilder();

            //struct_string.append("Flight ").append(FN).append(":boarding started.\n");
            struct_string.append(Pilot_state[ST_Pilot.ordinal()]).append(" ");

            struct_string.append(Hostess_state[ST_Hostess.ordinal()]).append(" ");
            for (int i = 0; i < Start.n_passenger; i ++){
                struct_string.append(Passenger_state[ST_Passenger[i].ordinal()]).append(" ");
            }
            //struct_string.append("\t").append("\t").append(ATL.size());
            struct_string.append("\t").append(Q.size()).append("\t").append(IN_F.size()).append("\t");
            struct_string.append("\n");

            fileWriter.write(struct_string.toString());
            fileWriter.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // -------------------------- SETTERS ------------------------- //

    public void setFN(int FN) { this.FN = FN; }

    public void setST_Passenger(int id, Passenger.State ST_Passenger) { this.ST_Passenger[id] = ST_Passenger; }

    public void setST_Pilot(Pilot.State ST_Pilot) { this.ST_Pilot = ST_Pilot; }

    public void setST_Hostess(Hostess.State st) { this.ST_Hostess = st; }

    public void setQ(Queue<Passenger> q) { Q = new ArrayList<>(q); }

    public void setIN_F(ArrayList<Passenger> IN_F) { this.IN_F = IN_F; }

    public void setATL(ArrayList<Passenger> ATL) { this.ATL = ATL; }
}
