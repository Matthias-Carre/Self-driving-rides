package rides;

import java.io.*;
import java.util.ArrayList;

class Course {
    int rideId;
    int startLine;
    int startCol;
    int endLine;
    int endCol;
    int earlystart;
    int latefin;
    int distance;
    boolean assigned;

    public Course(int rideId, int startLine, int startcol, int endLine, int endCol, int earlystart, int latefin) {
        this.rideId = rideId;
        this.startLine = startLine;
        this.startCol = startcol ;
        this.endLine = endLine;
        this.endCol = endCol;
        this.earlystart = earlystart;
        this.latefin = latefin ;
        this.distance = Math.abs(endLine - startLine) + Math.abs(endCol - startcol);
        this.assigned = false;
    }


}

