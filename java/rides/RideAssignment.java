package rides;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class RideAssignment {
    
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
        Scanner scanner = new Scanner(System.in);
        int R = scanner.nextInt();
        int C = scanner.nextInt();
        int F = scanner.nextInt();
        int N = scanner.nextInt();
        int B = scanner.nextInt();
        int T = scanner.nextInt();

        List<Vehicle> vehicles = new ArrayList<>();
        List<course> rides = new ArrayList<>();


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
            rides.add(new course(i, a, b, x, y, s, f));
        }

        scanner.close();

        for (int i = 0; i < rides.size(); i++) {
            course ride = rides.get(i);
            Vehicle bestVehicle = null;
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
        }
        sorti(vehicles);
    }

}
