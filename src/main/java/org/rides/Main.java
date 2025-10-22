package org.rides;

import java.io.*;

import static java.lang.System.*;

//file use for the moment

public class Main {
    public static void addResult(String path,String result){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path,true))){
            writer.write(result);
            writer.newLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void runForFolder(String folderName,String outFile) throws IOException {
        //on veux exec pour tout les fichier de folder
        File folder = new File(folderName);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            System.out.println(file.getName()) ;
            Instancemc inst = new Instancemc(file.getPath());

            inst.setFileOut(file.getName());
            int res = inst.earlyStartGoalLS();
            addResult(outFile,file+" : " + res );
            System.out.println(inst.isValid());
            inst.setFileOut("ESGLS/"+file.getName());
            inst.out();
        }
    }

    public static void rundm() throws IOException {
        Instancedm inst = new Instancedm("./inputs/a_example.in");
        inst.earlyStartGoal();
        Instancedm instc = new Instancedm("./inputs/c_no_hurry.in");
        instc.earlyStartGoal();
        Instancedm instd = new Instancedm("./inputs/d_metropolis.in");
        instd.testGoal();
    }
    public static void runmc() throws IOException {

        runForFolder("./in","./out_2/res.txt");



        /*
        Instancemc inst = new Instancemc("./inputs/a_example.in");

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

        inst = new Instancemc("./inputs/group_D_instance.in");
        inst.fileOut ="D";
        inst.longestRidesLocalSearch();
        inst.earlyStartGoal();


        inst = new Instancemc("./inputs/group-p.in");
        inst.fileOut ="P";
        inst.longestRidesLocalSearch();
        inst.earlyStartGoal();


        inst = new Instancemc("./inputs/group-p_big.in");
        inst.fileOut = "pbig";
        inst.longestRidesLocalSearch();
        inst.earlyStartGoal();
*/

    }
    public static void main(String[] args) throws IOException {
        //rundm();
        runmc();
    }
}