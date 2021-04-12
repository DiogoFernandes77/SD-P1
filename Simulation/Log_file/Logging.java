/**
 *  Log Class to produce log file each initiation
 *  @author António Ramos e Diogo Fernandes
 */

package Simulation.Log_file;

import java.io.*;

public class Logging {
    String file_n;
    private final static String directory_file = "Simulation/Log_file/";
    private final static String default_name = "Logger_";
    private final static String extension_file = ".txt";
    int id_file = 1;

    //pilot variables abbreviate
    private final static String atTransferGate = "ATRG";
    private final static String readyForBoarding = "RDFB";
    private final static String waitForBoarding = "WTFB";
    private final static String flyingForward = "FLFW";
    private final static String deboarding = "DRPP";
    private final static String flyingBack = "FLBK";

    //hostess variables abbreviate
    private final static String waitForNextFlight = "WTFL";
    private final static String waitForPassenger = "WTPS";
    private final static String checkPassenger = "CKPS";
    private final static String readyToFly = "RDTF";

    //passenger variables abbreviate
    private final static String goingToAirport = "GTAP";
    private final static String inQueue = "INQE";
    private final static String inFlight = "INFL";
    private final static String atDestination = "ATDS";

    //test creation of files
    public static void main(String[] args)
    {
       int file_id = checkFiles();

       try {
           FileWriter createFile = new FileWriter(directory_file + default_name + (file_id + 1) + extension_file);
           createFile.write("test id");
           createFile.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
            //new Logging(directory_file);


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

    public Logging(String file_name) {
        this.file_n = file_name;
        //create_file();
        String file_alter = add_struct(21);       // PT HT PX InQ InF PTAL
        boarding_struct(file_alter);  // Flight #: boarding started.
        passenger_check();  // Flight #: passenger # checked.
        departed();         // Flight #: departed with # passengers.
        arrived();          // Flight #: arrived.
        returning();        // Flight #: returned
        airlift_summary();  // Airlift sum up: Flight # transported # passengers
        legend();           // Legend

    }

    public String add_struct(int n_passenger){
        String name_file = "";

        try {
            //check if the program already executed and created a file, if created add id to create another
            File f = new File(file_n + id_file + ".txt");
            if (f.exists()) {
                id_file += 1;
                name_file = file_n + id_file + ".txt";

                FileWriter fileWriter = new FileWriter(name_file);
                fileWriter.write("PT \t HT  ");
                for (int i = 0; i < n_passenger; i++) {
                    if (i < 10)
                        fileWriter.write(" P0" + i + " ");
                    else
                        fileWriter.write(" P" + i + " ");
                }
                fileWriter.write("\tInQ InF PTAL\n");
                fileWriter.write("ATRG WTFL");
                for (int i = 0; i < n_passenger; i++)
                    fileWriter.write(" GTAP");
                fileWriter.write("\t0\t 0\t 0\n\n");

                fileWriter.flush();
                fileWriter.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return name_file;
    }

    public void boarding_struct(String file_name) {
        try {
            FileWriter fileWriter = new FileWriter(file_name,true); //continue writing when it's finished
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void passenger_check() {
    }
    private void departed() {
    }
    private void arrived() {
    }

    private void returning() {
    }

    private void airlift_summary() {
    }

    private void legend() {
    }

}
