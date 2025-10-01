package rides;

import java.io.*;

//file use for the moment



public class Main {

    public static void rundm() throws IOException {
        Instancedm inst = new Instancedm("./inputs/a_example.in");
        inst.earlyStartGoal();
        Instancedm instc = new Instancedm("./inputs/c_no_hurry.in");
        instc.earlyStartGoal();
        Instancedm instd = new Instancedm("./inputs/d_metropolis.in");
        instd.testGoal();
    }
    public static void runmc() throws IOException {
        Instancemc inst = new Instancemc("./inputs/a_example.in");
        inst.longestRides();
    }
    public static void main(String[] args) throws IOException {
        rundm();
         //runmc();
    }
}