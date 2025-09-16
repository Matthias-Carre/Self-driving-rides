package rides;

import java.io.*;
import java.util.ArrayList;

class course {
    int courseId;
    int debutligne;
    int debutcol;
    int finligne;
    int fincol;
    int earlystart;
    int latefin;
    int distance;

    public course(int courseId, int debutligne, int debutcol, int finligne, int fincol, int earlystart, int latefin) {
        this.courseId = courseId;
        this.debutligne = debutligne;
        this.debutcol = debutcol ;
        this.finligne = finligne;
        this.fincol = fincol;
        this.earlystart = earlystart;
        this.latefin = latefin ;
        this.distance = Math.abs(finligne - debutligne) + Math.abs(fincol - debutcol);
    }
}

