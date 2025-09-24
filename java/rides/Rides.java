package rides;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//file use for the moment



public class Rides {
    
    
    public static void sorti(List<Vehicle> vehicles,String out) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            for (Vehicle vehicle : vehicles) {
                // Écrivez l'ID du véhicule et ses courses assignées
                writer.write(vehicle.coursesAssi.size() + " ");
                for (int courseId : vehicle.coursesAssi) {
                    writer.write(courseId + " ");
                }
                writer.newLine(); // Passez à la ligne suivante
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }
    public static void run(String in,String out) throws IOException{
        File file = new File(in);
        Scanner scanner = new Scanner(file);

        //Scanner scanner = new Scanner(System.in);
        int R = scanner.nextInt();
        int C = scanner.nextInt();
        int F = scanner.nextInt();
        int N = scanner.nextInt();
        int B = scanner.nextInt();
        int T = scanner.nextInt();

        List<Vehicle> vehicles = new ArrayList<>();
        List<Course> rides = new ArrayList<>();


        for (int i = 0; i < F; i++) {
            vehicles.add(new Vehicle(i));
        }
        for (int i = 0; i < N; i++) {
            int a = scanner.nextInt();  // départ x
            int b = scanner.nextInt();  // départ y
            int x = scanner.nextInt();  // l'arrivée x
            int y = scanner.nextInt();  // l'arrivée y
            int s = scanner.nextInt();  //  début course
            int f = scanner.nextInt();  //  fin course
            rides.add(new Course(i, a, b, x, y, s, f));
        }

        scanner.close();

        for (int i = 0; i < rides.size(); i++) {
            Course ride = rides.get(i);
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
        sorti(vehicles,out);
    }
    

    public static void main(String[] args) throws IOException {
        run("inputs/a_example.in", "out_a.txt");
        run("inputs/b_should_be_easy.in", "out_b.txt");
        run("inputs/c_no_hurry.in", "out_c.txt");
        run("inputs/d_metropolis.in", "out_d.txt");
        run("inputs/e_high_bonus.in", "out_e.txt");
        run("inputs/output.txt", "out_test.txt");

    }
}