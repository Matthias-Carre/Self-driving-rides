package org.rides;

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
        /*
        inst.fileOut = "outmc_a_example.in";
        inst.longestRides();

        inst = new Instancemc("./inputs/b_should_be_easy.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/c_no_hurry.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/d_metropolis.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/e_high_bonus.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/group_B_instance.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/group_C_instance.in");
        inst.longestRides();
*/
        inst = new Instancemc("./inputs/group_D_instance.in");
        inst.fileOut ="D";
        inst.longestRidesLocalSearch();

        inst = new Instancemc("./inputs/group-p.in");
        inst.fileOut ="P";
        inst.longestRidesLocalSearch();

        inst = new Instancemc("./inputs/group-p_big.in");
        inst.fileOut = "pbig";
        inst.longestRidesLocalSearch();

    }
    public static void main(String[] args) throws IOException {
        //rundm();
        runmc();
    }
}