package rides;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//file use for the moment



public class Main {
    
    public static void main(String[] args) throws IOException {

        Instancemc inst = new Instancemc("./inputs/a_example.in");

        inst.earlyStartGoal();
    }
}