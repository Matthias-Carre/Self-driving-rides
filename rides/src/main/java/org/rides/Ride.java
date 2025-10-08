package org.rides;
//import java.io.*;
//import java.util.ArrayList;

class Ride {
    int rideId;
    int startLine;
    int startCol;
    int endLine;
    int endCol;
    int earlyStart;
    int lateFin;
    int distance;
    boolean assigned;

    public Ride(int rideId, int startLine, int startcol, int endLine, int endCol, int earlystart, int latefin) {
        this.rideId = rideId;
        this.startLine = startLine;
        this.startCol = startcol ;
        this.endLine = endLine;
        this.endCol = endCol;
        this.earlyStart = earlystart;
        this.lateFin = latefin ;
        this.distance = Math.abs(endLine - startLine) + Math.abs(endCol - startcol);
        this.assigned = false;
    }


}
