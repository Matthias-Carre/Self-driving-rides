package org.rides;

import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Instancemc {

    String fileIn;
    String fileOut = "outdefault" ;

    int rows;
    int cols;
    int numVehicles;
    int numRides;
    int startingBonus;
    int maxSteps;

    ArrayList<Vehicle> vehicles;
    ArrayList<Ride> rides;

    public Instancemc(String fileIn)throws FileNotFoundException{
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

    private void out() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("outputs/" + fileOut))) {
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
        out();
        return score();
    }

    protected int longestRides(){
        ArrayList<Ride> ridesSorted = new ArrayList<>();
        for(int j=0;j<numRides;j++){
            Ride longestRide=null;
            for(int i = 0; i < rides.size(); i++) {
                Ride ride = rides.get(i);
                if(longestRide==null && !ride.assigned ){
                    longestRide=ride;
                }
                else if( ((!ride.assigned) && (ride.distance>longestRide.distance))){
                    longestRide=ride;
                }
            }
            ridesSorted.add(longestRide);
            longestRide.assigned = true;
        }


        for (int i = 0; i < ridesSorted.size(); i++) {
            Ride ride = ridesSorted.get(i);
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

        out();
        return score();

    }

    protected int longestRidesLocalSearch(){
        ArrayList<Ride> ridesSorted = new ArrayList<>();
        for(int j=0;j<numRides;j++){
            Ride longestRide=null;
            for(int i = 0; i < rides.size(); i++) {
                Ride ride = rides.get(i);
                if(longestRide==null && !ride.assigned ){
                    longestRide=ride;
                }
                else if( ((!ride.assigned) && (ride.distance>longestRide.distance))){
                    longestRide=ride;
                }
            }
            ridesSorted.add(longestRide);
            longestRide.assigned = true;
        }


        for (int i = 0; i < ridesSorted.size(); i++) {
            Ride ride = ridesSorted.get(i);
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
        //upgradeRide(vehicles.get(6));
        //upgradeRide(vehicles.get(7));
        swapTry(vehicles);
        printMetrics();
        out();
        return score();

    }


    protected int earlyRides(){
        ArrayList<Ride> ridesSorted = new ArrayList<>();
        for(int j=0;j<numRides;j++){
            Ride earlyRide=null;
            for(int i = 0; i < rides.size(); i++) {
                Ride ride = rides.get(i);
                if(earlyRide==null && !ride.assigned ){
                    earlyRide=ride;
                }
                else if( ((!ride.assigned) && (ride.earlyStart>earlyRide.earlyStart))){
                    earlyRide=ride;
                }
            }
            ridesSorted.add(earlyRide);
            earlyRide.assigned = true;
        }

        for (int i = 0; i < ridesSorted.size(); i++) {
            Ride ride = ridesSorted.get(i);
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

        out();
        return score();

    }
    private void swapTry(List<Vehicle> vehicles){
        Vehicle v1 = vehicles.get(0);
        Vehicle v2 = vehicles.get(1);
        System.out.println("Base Score: V1="+v1.calculateScore(startingBonus)+" V2="+v2.calculateScore(startingBonus));
        //System.out.println("v1="+v1.printRides());
        Ride r1a = v1.getRaces().get(0);
        for(int i=0;i<v2.getRaces().size();i++){
            Ride r2 = v2.getRaces().get(i);
            v2.getRaces().set(i,r1a);
            v1.getRaces().set(0,r2);
            System.out.println("Score v1:"+v1.calculateScore(startingBonus));
            System.out.println("Score v2:"+v2.calculateScore(startingBonus));
        }
    }

    private void upgradeRide(Vehicle vehicle){
        //List<Ride> betterRides = vehicle.getRaces();
        System.out.println("upgrade size:"+ vehicle.getRaces().size()+" actual score:"+vehicle.calculateScore(startingBonus));
        for (int i = 0; i < vehicle.getRaces().size(); i++) {
            Ride ride = rides.get(i);
            for (int j = 0; j < vehicle.getRaces().size(); j++) {
                if(i!=j){
                    Collections.swap(vehicle.getRaces(),i,j);
                    System.out.println("Ride "+i+" "+ j +" "+ vehicle.calculateScore(startingBonus));
                }
            }
        }
    }
    private void swapRides(List<Vehicle> vehicles){
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicleA = vehicles.get(i);
            for (int j = 0; j < vehicles.size(); j++) {
                Vehicle vehicleB = vehicles.get(j);
                if(i!=j){
                    for(int k=0;k<vehicleA.getRaces().size();k++){
                        for(int l=0;l<vehicleB.getRaces().size();l++){
                            if(k!=l){
                                Ride r1 = vehicleA.getRaces().get(k);
                                Ride r2 = vehicleB.getRaces().get(l);
                                vehicleA.getRaces().set(k,r2);
                                vehicleB.getRaces().set(l,r1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void printMetrics(){
        //maxscore - % complete ride - % bonus get
        int totalScore = 0;
        int numBonus = 0;
        int finisedRides = 0;
        int maxRidesPoints=0;

        for (Vehicle vehicle : vehicles) {
            numBonus+=1;
            int score = vehicle.calculateScore(this.startingBonus);
            totalScore += score;
        }
        for(int i=0; i<rides.size();i++) {
            maxRidesPoints += rides.get(i).distance;
        }
        System.out.println("Metrics of "+ fileIn);
        System.out.println("Theorical max values:\n Max bonus:"+numRides+"Max Rides Points:"+maxRidesPoints);
        System.out.println("Results: \n number of Finised Rides:"+finisedRides);
        System.out.println(" number of bonus:"+numBonus);



    }
}