package com.company;

import java.io.IOException;
import java.nio.file.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String[] files = new String[3];

        //array for user inputs
        System.out.println("Insert dataset file.");
        files[0] = s.nextLine();
        System.out.println("Insert config file.");
        files[1] = s.nextLine();
        System.out.println("Insert output file name.");
        files[2] = s.nextLine();



        //dataset into 2D array
        ArrayList<String> data = new ArrayList<String>();
        String[] wordsArray;
        String[][] dataTable = new String[0][];
        int numberOfLines = 0;
        int numberOfRows = 0;
        try {
            BufferedReader buf = new BufferedReader(new FileReader(files[0]));

            String lineJustFetched = null;

            while (true) {
                lineJustFetched = buf.readLine();
                if (lineJustFetched == null) {
                    break;
                } else {
                    numberOfLines++;
                    wordsArray = lineJustFetched.split("\t");
                    numberOfRows =wordsArray.length;
                    for (String word: wordsArray) {
                        data.add(word);
                    }
                }
            }
            dataTable = new String[numberOfRows][numberOfLines];

            int counter = 0;
            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfLines; j++) {
                    dataTable[i][j] = data.get(counter);
                    counter++;
                    System.out.print(dataTable[i][j]+"\t");
                }
                System.out.println("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //reading the config file
        ArrayList<String> configData = new ArrayList<String>();

        try {
            BufferedReader buf = new BufferedReader(new FileReader(files[1]));
            String lineJustFetched = null;
            while (true) {
                lineJustFetched = buf.readLine();
                if (lineJustFetched == null) {
                    break;
                } else {
                    configData.add(lineJustFetched);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < configData.size(); i++) {
            System.out.println(configData.get(i));
        }

        String[][] encryptedDataTable= new String[numberOfRows][numberOfLines];
        for(int i=0; i < dataTable.length; i++)
            for(int j=0; j < dataTable[i].length; j++)
                encryptedDataTable[i][j]=dataTable[i][j];


        for (int i = 0; i <numberOfRows ; i++) {
            for (int j = 0; j < configData.size(); j++) {
                if(dataTable[0][i].equals(configData.get(j))) {
                    for (int k = 1; k < numberOfLines; k++) {
                        System.out.println(encryptedDataTable[k][i]);
                        encryptedDataTable[k][i] = encryptElement(dataTable[k][i]);
                    }
                }
            }
        }

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfLines; j++) {
                System.out.println(encryptedDataTable[i][j]+"\t");
            }
            System.out.println("\n");
        }

        //deleting the files passwordoutput1 for safety
        try
        {
            Files.deleteIfExists(Paths.get("passwordoutput1.txt"));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }

        //encrypted table into textfile
        try
        {
            PrintWriter pr = new PrintWriter(files[2]);
            int flag;
            for (int i=0; i<numberOfRows ; i++) {
                flag = i;
                for (int j = 0; j < numberOfLines; j++) {
                    pr.print(encryptedDataTable[i][j] + "\t");
                }
                pr.println("");
            }
            pr.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No such file exists.");
        }
    }

    public static String encryptElement(String element) throws FileNotFoundException {

        String encryptedElement=null;

        try (PrintWriter out = new PrintWriter("passwordoutput1.txt")) {
            out.println(element);
        }
        String key = "54 68 61 74 73 2";
        File inputFile = new File("passwordoutput1.txt");
        File encryptedFile = new File("passwordoutput2.txt");
        try {
            CryptoUtils.encrypt(key, inputFile, encryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        try {
            BufferedReader buf = new BufferedReader(new FileReader("passwordoutput2.txt"));
            encryptedElement = buf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encryptedElement;
    }
}


