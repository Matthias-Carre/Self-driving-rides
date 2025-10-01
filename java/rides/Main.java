package rides;

import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;

//file use for the moment



public class Main {
    
    public static void main(String[] args) throws IOException {

        Instancemc inst = new Instancemc("./inputs/a_example.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/b_should_be_easy.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/c_no_hurry.in");
        inst.fileOut = "c.out";
        inst.longestRides();

        inst = new Instancemc("./inputs/d_metropolis.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/e_high_bonus.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/group_B_instance.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/group_C_instance.in");
        inst.longestRides();

        inst = new Instancemc("./inputs/group_D_instance.in");
        inst.longestRides();


        //inst.earlyStartGoal();
    }
}