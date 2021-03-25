package Simulation.locations;

public class DepartAirport {
    private static int nPassenger, planeCapacity, boardMin, boarMax;

    //construct for the departure airport, know passenger, plane capacity, min and max of boarding
    public DepartAirport(int n_passenger, int plane_capacity, int board_min, int board_max){
        nPassenger = n_passenger;
        planeCapacity = plane_capacity;
        boardMin = board_min;
        boarMax = board_max;
    }

    //waits for the passenger enter in the plane and hostess give a signal to the pilot so he can go to destination airport
    private void waitForBoarding(){}

    //create threads of Passengers
    private void goingToAirport(){
        int i = 0;
        while(i < nPassenger)
        {
            //create one thread for passenger
        }
    }

    //Hostess waits for the passengers
    private void waitsForPassenger(){}

    //Hostess check documents of the first passenger if its ok then he gets into the plane until the full capacity is reached
    private void checkPassenger(){ }

    // Hostess gives permission to passenger goes to plane until the full capacity is reached
    private void toPlane(){
        //count passenger in the queue
        // see the first
        // check documents
        // if ok goes to plane until the full capacity is reached or the min is reached
    }

    // Its happening the travel between cities
    private void inPlace(){}

    // Pilot tells hostess that arrives on destination
    private void atDestination(){
        //kill every thread -> passenger that's is on the plane
        //Call method on DestAirPort
    }


}