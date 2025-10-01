package rides;

import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {

    String fileIn;

    int rows;
    int cols;
    int numVehicles;
    int numRides;
    int startingBonus;
    int maxSteps;

    ArrayList<Vehicle> vehicles;
    ArrayList<Ride> rides;

    public Instance(String fileIn)throws FileNotFoundException{
        this.fileIn = fileIn;
        this.vehicles = new ArrayList<>();
        this.rides = new ArrayList<>();

        this.read();

    }
    public void read() throws FileNotFoundException{
        File file = new File(fileIn);
        Scanner scanner = new Scanner(file);

        this.rows = scanner.nextInt();
        this.cols = scanner.nextInt();
        this.numVehicles = scanner.nextInt();
        this.numRides = scanner.nextInt();
        this.startingBonus = scanner.nextInt();
        this.maxSteps = scanner.nextInt();


        for (int i = 0; i < numVehicles; i++) {
            this.vehicles.add(new Vehicle(i));
        }
        for (int i = 0; i < numRides; i++) {
            int a = scanner.nextInt();  // départ x
            int b = scanner.nextInt();  // départ y
            int x = scanner.nextInt();  // l'arrivée x
            int y = scanner.nextInt();  // l'arrivée y
            int s = scanner.nextInt();  //  début course
            int f = scanner.nextInt();  //  fin course
            rides.add(new Ride(i, a, b, x, y, s, f));
        }
        scanner.close();
    }

    private static void sorti(List<Vehicle> vehicles,String out) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            for (Vehicle vehicle : vehicles) {
                // Écrivez l'ID du véhicule et ses courses assignées
                writer.write(vehicle.racesAssi.size() + " ");
                for (Ride ride : vehicle.racesAssi) {
                    writer.write(ride.rideId + " ");
                }
                writer.newLine(); // Passez à la ligne suivante
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }


    private int score(){
        int totalScore = 0;
        for (Vehicle vehicle : vehicles) {
            int score = vehicle.calculateScore(this.startingBonus);
            totalScore += score;
        }
        System.out.println("Total Score: "+ fileIn + ": " + totalScore);
        return totalScore;
    }

    protected int earlyStartGoal(){
        for (int i = 0; i < rides.size(); i++) {
            Ride ride = rides.get(i);
            Vehicle bestVehicle = null;
            int bestTime = Integer.MAX_VALUE;


            for (int j = 0; j < vehicles.size(); j++) {
                Vehicle vehicle = vehicles.get(j);
                int travelTime = Math.abs(vehicle.currentRow - ride.startLine) + Math.abs(vehicle.currentCol - ride.startCol);
                int earlyStart = Math.max(vehicle.availableTime + travelTime, ride.earlyStart);
                int endTime = earlyStart + ride.distance;
                if (endTime <= ride.lateFin) {
                    if (earlyStart < bestTime) {
                        bestVehicle = vehicle;
                        bestTime = earlyStart;
                    }
                }
            }

            if (bestVehicle != null) {

                bestVehicle.racesAssi.add(ride);
                bestVehicle.currentRow = ride.endLine;
                bestVehicle.currentCol = ride.endCol;
                bestVehicle.availableTime = bestTime + ride.distance;
            }
        }
        return score();
    }
    
}
