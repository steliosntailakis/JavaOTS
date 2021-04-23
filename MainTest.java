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
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String[] files = new String[3];

        //array for user inputs
        System.out.println("Insert dataset file.");
        files[0] = s.nextLine();
        System.out.println("Insert config file.");
        files[1] = s.nextLine();
        System.out.println("Insert output file name.");
        files[2] = s.nextLine();

        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> Name = new ArrayList<>();
        ArrayList<String> Telephone = new ArrayList<>();
        ArrayList<String> Password = new ArrayList<>();


        //tsf into an arraylist and after that seperation into three arraylists
        try {
            BufferedReader buf = new BufferedReader(new FileReader(files[0]));

            String lineJustFetched = null;
            String[] wordsArray;

            while (true) {
                lineJustFetched = buf.readLine();
                if (lineJustFetched == null) {
                    break;
                } else {
                    wordsArray = lineJustFetched.split("\t");
                    for (String each : wordsArray) {
                        if (!"".equals(each)) {
                            words.add(each);
                        }
                    }
                }
            }
//            for (String each : words) {
//                System.out.println(each);
//            }

//            System.out.println("list size:" + words.size());

            for (int i = 3; i < words.size(); i = i + 3) {
                Name.add(words.get(i));
            }
//            for (int i = 0; i < Name.size(); i++) {
//                System.out.println(Name.get(i));
//            }

            for (int i = 4; i < words.size(); i = i + 3) {
                Telephone.add(words.get(i));
            }
//            for (int i = 0; i < Telephone.size(); i++) {
//                System.out.println(Telephone.get(i));
//            }

            for (int i = 5; i < words.size(); i = i + 3) {
                Password.add(words.get(i));
            }
//            for (int i = 0; i < Password.size(); i++) {
//                System.out.println(Password.get(i));
//            }

//
        } catch (Exception e) {
            e.printStackTrace();
        }

        //reading the config file
        String textToBeChecked = null;
        try {
            BufferedReader buf = new BufferedReader(new FileReader(files[1]));
            textToBeChecked = buf.readLine();
//            System.out.println(textToBeChecked);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //checking if the config file has the word Password inside
        if(textToBeChecked.equals("Password")) {
            try {
                FileWriter writer = new FileWriter("passwordoutput.txt");
                for (String str : Password) {
                    writer.write(str + System.lineSeparator());
                }
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //encryption
            String key = "54 68 61 74 73 2";
            File inputFile = new File("passwordoutput.txt");
            File encryptedFile = new File(files[2]);
            try {
                CryptoUtils.encrypt(key, inputFile, encryptedFile);
                System.out.println("Encryption complete. The file "+files[2]+" has been created.");
            } catch (CryptoException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

            //deleting the file passwordoutput.txt for safety
            try
            {
                Files.deleteIfExists(Paths.get("passwordoutput.txt"));
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

        }
        }


    }


