package rides;

import java.io.*;
import java.util.ArrayList;

class Course {
    int courseId;
    int debutligne;
    int debutcol;
    int finligne;
    int fincol;
    int earlystart;
    int latefin;
    int distance;
    boolean assigned;

    public Course(int courseId, int debutligne, int debutcol, int finligne, int fincol, int earlystart, int latefin) {
        this.courseId = courseId;
        this.debutligne = debutligne;
        this.debutcol = debutcol ;
        this.finligne = finligne;
        this.fincol = fincol;
        this.earlystart = earlystart;
        this.latefin = latefin ;
        this.distance = Math.abs(finligne - debutligne) + Math.abs(fincol - debutcol);
        this.assigned = false;
    }


}

