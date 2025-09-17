package rides;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class RideAssignment {

    public static int score(List<Vehicle> vehicles, List<Course> rides, int B) {
            int score = 0;
            for (Vehicle vehicle : vehicles) {
                int time = 0;
                int currentRow = 0;
                int currentCol = 0;
                for (int i=0;i<vehicle.coursesAssi.size();i++) {
                    int rideId = vehicle.coursesAssi.get(i);
                    Course ride = rides.get(rideId);
                    int travelTimeToStart = Math.abs(currentRow - ride.debutligne) + Math.abs(currentCol - ride.debutcol);
                    time += travelTimeToStart;
                    if (time <= ride.earlystart) {
                        score += B;
                        time = ride.earlystart;
                    }
                    time += ride.distance;
                    
                    if (time <= ride.latefin) {
                        score += ride.distance;
                    }
                    currentRow = ride.finligne;
                    currentCol = ride.fincol;
                }
            }
            return score;
    
        }
    
        //provisoire
        public static Course earlyCourse(List<Course> rides) {
            int earliest = 1000000000;
            Course earliestCourse = null;
            for (int i = 0; i < rides.size(); i++) {
                Course ride = rides.get(i);
                if (ride.earlystart < earliest && !ride.assigned) {
                    earliest = ride.earlystart;
                    earliestCourse = ride;
                }
            }
            earliestCourse.assigned = true;
            return earliestCourse;
        }    
    
        public static int distance(Vehicle v, Course c) {
            return Math.abs(v.currentRow - c.debutligne) + Math.abs(v.currentCol - c.debutcol);
        }
    
        public static void sorti(List<Vehicle> vehicles) {
            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle vehicle = vehicles.get(i);
                System.out.print(vehicle.coursesAssi.size() + " ");
                for (int rideId : vehicle.coursesAssi) {
                    System.out.print(rideId + " ");
                }
                System.out.println();
            }
        }
    
        public static void main(String[] args) throws IOException {
            File file = new File("inputs/b_should_be_easy.in");
            //File file = new File("inputs/a_example.in");


            //Scanner scanner = new Scanner(System.in);
            Scanner scanner = new Scanner(file);
    
            int R = scanner.nextInt(); // #rows
            int C = scanner.nextInt(); // #colums
            int F = scanner.nextInt(); // #vehicles
            int N = scanner.nextInt(); // #rides
            int B = scanner.nextInt(); // value for starting on time bonus
            int T = scanner.nextInt(); // end of rides
    
            List<Vehicle> vehicles = new ArrayList<>();
            List<Course> rides = new ArrayList<>();
    
    
            for (int i = 0; i < F; i++) {
                vehicles.add(new Vehicle(i));
            }
            for (int i = 0; i < N; i++) {
                int a = scanner.nextInt();  // départ x
                int b = scanner.nextInt();  // départ y
                int x = scanner.nextInt();  //l'arrivée x
                int y = scanner.nextInt();  // l'arrivée y
                int s = scanner.nextInt();  //  début course
                int f = scanner.nextInt();  //  fin course
                rides.add(new Course(i, a, b, x, y, s, f));
            }
    
            scanner.close();
            
            for (int i = 0; i < N; i++) {
                boolean done= false;
                Course ride = earlyCourse(rides);
                //System.out.println("ride id:" + ride.courseId);
                for(int j=0; j<F; j++){
                    Vehicle vehicle = vehicles.get(j);
                    int dist = distance(vehicle, ride);
                    int arrivee = vehicle.availableTime + dist;
    
                    if(arrivee <= ride.earlystart){
                        //System.out.println("On donne ride " + ride.courseId + " au vehicle " + vehicle.vehicleId);
                        done = true;
                        vehicle.availableTime = ride.earlystart + ride.distance;
                        vehicle.currentRow = ride.finligne;
                        vehicle.currentCol = ride.fincol;
                        vehicle.coursesAssi.add(ride.courseId);
                        break;
                    }
    
                }
                if(!done){
                    int lessLoaded = 0;
                    for(int j=1; j<F; j++){
                        if(vehicles.get(j).coursesAssi.size() < vehicles.get(lessLoaded).coursesAssi.size()){
                            lessLoaded = j;
                        }
                    }
                    Vehicle vehicle = vehicles.get(lessLoaded);
                    //System.out.println("On donne ride " + ride.courseId + " au vehicle " + vehicle.vehicleId);
                    vehicle.coursesAssi.add(ride.courseId);
                }
            }
    
                /* 
                int bestTime = Integer.MAX_VALUE;
    
    
                for (int j = 0; j < vehicles.size(); j++) {
                    Vehicle vehicle = vehicles.get(j);
                    int traveltime_debut = Math.abs(vehicle.currentRow - ride.debutligne) + Math.abs(vehicle.currentCol - ride.debutcol);
                    int early_debut_possible = Math.max(vehicle.availableTime + traveltime_debut, ride.earlystart);
                    int fintemps = early_debut_possible + ride.distance;
                    if (fintemps <= ride.latefin) {
                        if (early_debut_possible < bestTime) {
                            bestVehicle = vehicle;
                            bestTime = early_debut_possible;
                        }
                    }
                }
    
                if (bestVehicle != null) {
    
                    bestVehicle.coursesAssi.add(ride.courseId);
                    bestVehicle.currentRow = ride.finligne;
                    bestVehicle.currentCol = ride.fincol;
                    bestVehicle.availableTime = bestTime + ride.distance;
                }
            */
            System.out.println("Out:");
            sorti(vehicles);
            System.out.println("score="+score(vehicles, rides, B));
        
    }

}
