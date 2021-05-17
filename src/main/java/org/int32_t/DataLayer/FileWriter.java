package org.int32_t.DataLayer;

import java.io.File;
import java.io.IOException;

public class FileWriter {

    private String fileName;

    public FileWriter(String fileName) {
       createFile(fileName);
       this.fileName = fileName;
    }

    public FileWriter(String fileName,String fileContents) {
        this.fileName = fileName;
        createFile(fileName);
        write(fileContents);
    }

    //write to file
    public void write(String fileContents)  {
        try {
            java.io.FileWriter myWriter = new java.io.FileWriter(fileName);
            myWriter.write(fileContents);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Try to create the log file
    private File createFile(String fileName) {
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            return myObj;
        } catch (IOException e) {
            System.out.println("Failed to create the following file: " + fileName);
            e.printStackTrace();
        }
        return null;
    }

}
