package Logging_Package;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {
    String file_n = "";

    //test creation of files
    public static void main(String[] args)
    {
        Logging logger = new Logging("Logging_Package/logger.txt");
    }

    public Logging(String file_name) {
        this.file_n = file_name;
        create_file();
        add_struct(21);       // PT HT PX InQ InF PTAL
        boarding_struct();  // Flight #: boarding started.
        passenger_check();  // Flight #: passenger # checked.
        departed();         // Flight #: departed with # passengers.
        arrived();          // Flight #: arrived.
        returning();        // Flight #: returned
        airlift_summary();  // Airlift sum up: Flight # transported # passengers
        legend();           // Legend

    }

    public void create_file(){
        File file = new File(file_n);
        try {
            if(file.createNewFile()){
                System.out.println("File " + file_n + " created on directory Logging_Package");
            }else {
                System.out.println("File already exists, so we gonna erase and create a new one");
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add_struct(int n_passenger){
        try {
            FileWriter fileWriter = new FileWriter(file_n);
            fileWriter.write("PT \t HT  ");
            for(int i = 0; i < n_passenger; i ++) {
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

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void boarding_struct() {
        try {
            FileWriter fileWriter = new FileWriter(file_n,true); //continue writing when it's finished
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
