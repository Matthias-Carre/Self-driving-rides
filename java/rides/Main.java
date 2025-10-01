package rides;

import java.io.*;

//file use for the moment



public class Main {
    
    public static void main(String[] args) throws IOException {

        Instancemc inst = new Instancemc("./inputs/a_example.in");
        inst.longestRides();

        inst.earlyStartGoal();
        Instancedm instancedmc = new Instancedm("./inputs/c_no_hurry.in ");
        Instancedm instancedmd = new Instancedm("./inputs/d_metropolis.in ");
        instancedmc.testGoal();
        instancedmd.testGoal();
    }}
