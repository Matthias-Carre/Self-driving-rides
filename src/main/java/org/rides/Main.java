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

            //fonction utiliser
            int res = inst.earlyStartGoalLS();

            addResult(outFile,file+" : " + res );

            inst.setFileOut("ESGLS/"+file.getName());
            inst.out();
        }
    }

    public static void run() throws IOException {

        runForFolder("./in","./out_2/res.txt");



    }
    public static void main(String[] args) throws IOException {

        run();
    }
}