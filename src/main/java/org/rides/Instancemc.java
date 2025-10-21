package org.rides;

import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Instancemc {

    String fileIn;
    String fileOut = "outputs/dflt" ;

    int rows;
    int cols;
    int numVehicles;
    int numRides;
    int startingBonus;
    int maxSteps;

    int numBonus=0;
    int numRidesDone=0;

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
        System.out.println("trois");
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter( fileOut))) {
            for (Vehicle vehicle : vehicles) {
                // Écrivez l'ID du véhicule et ses courses assignées
                writer.write(vehicle.racesAssi.size() + " ");
                for (Ride ride : vehicle.racesAssi) {
                    //System.out.println("TEST On affecte ride"+ride.rideId + "au vehicle"+ vehicle.vehicleId);
                    writer.write(ride.rideId + " ");
                }
                writer.newLine(); // Passez à la ligne suivante
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    public void setFileOut(String name){
        //on retire le .in de name
        String filename = name.substring(0, name.lastIndexOf("."));
        this.fileOut = "outputs/"+filename+".out";
    }

    private int score(){
        int totalScore = 0;
        for (Vehicle vehicle : vehicles) {
            int score = vehicle.calculateScore(this.startingBonus);
            totalScore += score;
        }

        //System.out.println("Total Score: "+ fileIn + ": " + totalScore);
        return totalScore;
    }
    public boolean isValid(){
        int[] tab = new int[numRides];
        for(Vehicle vehicle : vehicles){
            for (Ride ride : vehicle.racesAssi) {
                if(tab[ride.rideId] == 0){
                    tab[ride.rideId] = 1;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    protected int localMaxScore(){
        for(Ride ride:rides){
            Vehicle BestVehicle=null;
            int bestScore=0;
            int scoreBeforeAdd=0;
            int scoreAfterAdd=0;
            for(Vehicle vehicle:vehicles){
                scoreBeforeAdd = vehicle.calculateScore(startingBonus);
                vehicle.racesAssi.add(ride);
                scoreAfterAdd = vehicle.calculateScore(startingBonus);
                if( scoreAfterAdd - scoreBeforeAdd > bestScore){
                    bestScore = scoreAfterAdd - scoreBeforeAdd;
                    BestVehicle = vehicle;
                }
                vehicle.racesAssi.removeLast();
            }
            if(BestVehicle != null){
                BestVehicle.racesAssi.add(ride);
            }
        }
        return score();
    }


    protected int startingTime(){
        ArrayList<Ride> ridesSorted = new ArrayList<>();
        for(int j=0;j<numRides;j++){
            Ride earlystart = null;
            for(int i = 0; i < rides.size(); i++) {
                Ride ride = rides.get(i);
                if(earlystart==null && !ride.assigned ){
                    earlystart=ride;
                }
                else if( ((!ride.assigned) && (ride.distance < earlystart.earlyStart))){
                    earlystart=ride;
                }
            }
            ridesSorted.add(earlystart);
            earlystart.assigned = true;
        }
        for(Ride ride:ridesSorted){
            Vehicle bestVehicle = null;
            int bestValue = 10000000;
            for(Vehicle vehicle : vehicles){
                int travelTime = Math.abs(vehicle.currentRow - ride.startLine) + Math.abs(vehicle.currentCol - ride.startCol);
                //System.out.println((ride.earlyStart+travelTime)+" <=? "+ vehicle.availableTime);

                //minimiser ce temps
                if( bestVehicle==null ){
                    bestVehicle = vehicle;
                    bestValue = ride.earlyStart+travelTime - vehicle.availableTime;
                } else if ( ride.earlyStart+travelTime - vehicle.availableTime <= bestValue ) {
                    bestVehicle = vehicle;
                    bestValue = ride.earlyStart+travelTime - vehicle.availableTime;
                }

            }
            if(bestVehicle!=null){
                int travelTime = Math.abs(bestVehicle.currentRow - ride.startLine) + Math.abs(bestVehicle.currentCol - ride.startCol);
                bestVehicle.racesAssi.add(ride);
                bestVehicle.availableTime = ride.earlyStart + travelTime + ride.distance;
                bestVehicle.currentCol = ride.endCol;
                bestVehicle.currentRow = ride.endLine;
            }

        }
        System.out.println("TEST Starting time:"+isValid());
        System.out.println("score:"+score());
        return score();

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
        printMetrics();
        out();
        return score();
    }

    protected int earlyStartGoalLS(){
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
        for(Vehicle vehicle1:vehicles) {
            for (int i=0;i<vehicle1.racesAssi.size();i++) {

                Ride ridev1 = vehicle1.racesAssi.get(i);

                boolean assigned = false;
                for (Vehicle vehicle2 : vehicles) {
                    int val = vehicle2.calculateScore(startingBonus) + vehicle1.calculateScore(startingBonus);
                    if(vehicle1.vehicleId == vehicle2.vehicleId) {continue;}
                    for (int j = 0; j < vehicle2.racesAssi.size(); j++) {

                        Ride ridev2 = vehicle2.racesAssi.remove(j);
                        vehicle1.racesAssi.remove(i);

                        vehicle1.racesAssi.add(i, ridev2);
                        vehicle2.racesAssi.add(j, ridev1);

                        int newVal = vehicle1.calculateScore(startingBonus)+vehicle2.calculateScore(startingBonus);
                        //System.out.println("TEST V1:"+val+" V2:"+newVal);
                        if (newVal > val) {
                            System.out.println("TEST: meilleur val:" + newVal + " previus:" + val);
                            System.out.println("On echange ride:" + ridev1.rideId + " sur voiture:" + vehicle1.vehicleId + "et v:"+vehicle2.vehicleId);
                            assigned = true;
                            break;
                        }
                        vehicle2.racesAssi.remove(j);
                        vehicle1.racesAssi.remove(i);

                        vehicle1.racesAssi.add(i, ridev1);
                        vehicle2.racesAssi.add(j, ridev2);
                    }
                    if (assigned) {
                        break;
                    }
                }
            }
        }
        printMetrics();
        out();
        return score();
    }



    protected int shortestRides(){
        ArrayList<Ride> ridesSorted = new ArrayList<>();
        for(int j=0;j<numRides;j++){
            Ride shortestRide=null;
            for(int i = 0; i < rides.size(); i++) {
                Ride ride = rides.get(i);
                if(shortestRide==null && !ride.assigned ){
                    shortestRide=ride;
                }
                else if( ((!ride.assigned) && (ride.distance < shortestRide.distance))){
                    shortestRide=ride;
                }
            }
            ridesSorted.add(shortestRide);
            shortestRide.assigned = true;
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
        printMetrics();
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
        printMetrics();
        out();
        return score();

    }

    protected int randomAssignedRides(){
        for(Ride ride : rides){
            Vehicle vehicle = vehicles.get((int) (Math.random()*vehicles.size()));
            vehicle.racesAssi.add(ride);
        }
        System.out.println("score:"+score());
        printMetrics();
        /*
        for(Vehicle vehicle1:vehicles) {

            //vehicle1.printRides();

            for (int i=0;i<vehicle1.racesAssi.size();i++) {

                Ride ridev1 = vehicle1.racesAssi.get(i);

                boolean assigned = false;
                for (Vehicle vehicle2 : vehicles) {
                    int val = vehicle2.calculateScore(numBonus) + vehicle1.calculateScore(numBonus);
                    if(vehicle1.vehicleId == vehicle2.vehicleId) {continue;}
                    for (int j = 0; j < vehicle2.racesAssi.size(); j++) {

                        Ride ridev2 = vehicle2.racesAssi.remove(j);
                        vehicle1.racesAssi.remove(i);

                        vehicle1.racesAssi.add(i, ridev2);
                        vehicle2.racesAssi.add(j, ridev1);

                        int newVal = vehicle1.calculateScore(numBonus)+vehicle2.calculateScore(numBonus);
                        //System.out.println("TEST V1:"+val+" V2:"+newVal);
                        if (newVal > val) {
                            //System.out.println("TEST: meilleur val:" + newVal + " previus:" + val);
                            //System.out.println("On echange ride:" + ridev1.rideId + " sur voiture:" + vehicle1.vehicleId + "et v:"+vehicle2.vehicleId);
                            assigned = true;
                            break;
                        }
                        vehicle2.racesAssi.remove(j);
                        vehicle1.racesAssi.remove(i);

                        vehicle1.racesAssi.add(i, ridev1);
                        vehicle2.racesAssi.add(j, ridev2);
                    }
                    if (assigned) {
                        break;
                    }
                }

            }


        }
        */
        System.out.println(score());
        System.out.println(isValid());
        return score();
    }

    protected int longestRidesLocalSearch(){
        ArrayList<Ride> ridesSorted = new ArrayList<>();
        ArrayList<Ride> rideUnassigned = new ArrayList<>();

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
            //Ride ride = ridesSorted.get(i);
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
            }else{
                rideUnassigned.add(ride);
            }
        }
        for(Vehicle vehicle1:vehicles) {
            for (int i=0;i<vehicle1.racesAssi.size();i++) {

                Ride ridev1 = vehicle1.racesAssi.get(i);

                boolean assigned = false;
                for (Vehicle vehicle2 : vehicles) {
                    int val = vehicle2.calculateScore(startingBonus) + vehicle1.calculateScore(startingBonus);
                    if(vehicle1.vehicleId == vehicle2.vehicleId) {continue;}
                    for (int j = 0; j < vehicle2.racesAssi.size(); j++) {

                        Ride ridev2 = vehicle2.racesAssi.remove(j);
                        vehicle1.racesAssi.remove(i);

                        vehicle1.racesAssi.add(i, ridev2);
                        vehicle2.racesAssi.add(j, ridev1);

                        int newVal = vehicle1.calculateScore(startingBonus)+vehicle2.calculateScore(startingBonus);
                        //System.out.println("TEST V1:"+val+" V2:"+newVal);
                        if (newVal > val) {
                            //System.out.println("TEST: meilleur val:" + newVal + " previus:" + val);
                            //System.out.println("On echange ride:" + ridev1.rideId + " sur voiture:" + vehicle1.vehicleId + "et v:"+vehicle2.vehicleId);
                            assigned = true;
                            break;
                        }
                        vehicle2.racesAssi.remove(j);
                        vehicle1.racesAssi.remove(i);

                        vehicle1.racesAssi.add(i, ridev1);
                        vehicle2.racesAssi.add(j, ridev2);
                    }
                    if (assigned) {
                        break;
                    }
                }
            }
        }

        printMetrics();
        //out();
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

        printMetrics();
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
        float avgWaitingTime=0;
        int maxPoints=0;

        for (Vehicle vehicle : vehicles) {
            int score = vehicle.calculateScore(this.startingBonus);
            numBonus += vehicle.numberOfBonus();
            totalScore += score;
            avgWaitingTime += vehicle.timeWaiting();
            finisedRides += vehicle.rideDone;
        }
        avgWaitingTime = avgWaitingTime / numVehicles;
        for (Ride ride : rides) {
            maxRidesPoints += ride.distance;
        }
        this.numBonus = numBonus;
        this.numRidesDone = finisedRides;
        maxPoints = ( maxRidesPoints+ (numRides*startingBonus));
        System.out.println("=-=-= Metrics of "+ fileIn+" =-=-=");
        System.out.println("#rides = "+ numRides);
        System.out.println("#vehicles = "+ numVehicles);
        System.out.println("max time = "+ maxSteps);
        System.out.println("Bonus value = "+startingBonus);

        System.out.println("Theorical max values:\n Max bonus : "+numRides+"\n Max Rides Points : "+maxRidesPoints+"\n Total="+ maxPoints );
        System.out.println("Results: \n number of Finised Rides : "+finisedRides+"/"+numRides);
        System.out.println(" number of bonus : "+numBonus+"/"+numRides);
        System.out.println(" average waiting time : "+ (int) avgWaitingTime );
        System.out.println(" Final Score : "+totalScore);

    }

}