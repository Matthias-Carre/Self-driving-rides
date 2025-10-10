package org.rides;

//import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Vehicle {
    int vehicleId;
    int currentRow;
    int currentCol;
    int availableTime;
    List<Ride> racesAssi;

    public Vehicle(int vehicleId) {
        this.vehicleId = vehicleId;
        this.currentRow = 0;
        this.currentCol = 0;
        this.availableTime = 0;
        this.racesAssi = new ArrayList<>();
    }

    List<Ride> getRaces(){
        return this.racesAssi;
    }

    public void printRides(){
        for(int i=0;i<this.racesAssi.size();i++){
            System.out.println(this.racesAssi.get(i).rideId);
        }
        System.out.println();
    }

    public int calculateScore(int bonus) {
        int totalScore = 0;
        int col=0;
        int line=0;
        int time=0;
        for (Ride ride : this.racesAssi) {

            //System.out.println("Ride ID: " + ride.rideId + " erlystart"+ ride.earlyStart + "Late Finish: " + ride.lateFin + ", Distance: " + ride.distance + ", Available Time: " + this.availableTime);
            time += Math.abs(col - ride.startCol) + Math.abs(line - ride.startLine);
            if (time <= ride.earlyStart) {
                totalScore += bonus;
                time = ride.earlyStart;
            }

            time += ride.distance;
            if (time <= ride.lateFin) {
                totalScore += ride.distance;
            }
            col =  ride.endCol;
            line = ride.endLine;
        }

        return totalScore;
    }

    public int numberOfBonus() {
        int numBonus = 0;
        int col=0;
        int line=0;
        int time=0;
        for (Ride ride : this.racesAssi) {
            //System.out.println("Ride ID: " + ride.rideId + " erlystart"+ ride.earlyStart + "Late Finish: " + ride.lateFin + ", Distance: " + ride.distance + ", Available Time: " + this.availableTime);
            time += Math.abs(col - ride.startCol) + Math.abs(line - ride.startLine);
            if (time <= ride.earlyStart) {
                numBonus++;
                time = ride.earlyStart;
            }
            time += ride.distance;
            col =  ride.endCol;
            line = ride.endLine;
        }

        return numBonus;
    }
    public int timeWaiting(){
        int timeWait = 0;
        int col=0;
        int line=0;
        int time=0;
        for (Ride ride : this.racesAssi) {
            //System.out.println("Ride ID: " + ride.rideId + " erlystart"+ ride.earlyStart + "Late Finish: " + ride.lateFin + ", Distance: " + ride.distance + ", Available Time: " + this.availableTime);
            time += Math.abs(col - ride.startCol) + Math.abs(line - ride.startLine);
            if (time <= ride.earlyStart) {
                timeWait += ride.earlyStart - time;
                time = ride.earlyStart;
            }
            time += ride.distance;
            col =  ride.endCol;
            line = ride.endLine;
        }

        return timeWait;
    }
}