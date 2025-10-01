package rides;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//file use for the moment



public class Main {
    
    public static void main(String[] args) throws IOException {

        Instance inst = new Instance("./inputs/a_example.in");

        inst.earlyStartGoal();
    }
}