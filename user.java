package ucf.assignments;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*
 * UCF COP3330 Fall 2021 Assignment 4 Solution
 * Copyright 2021 Taha Al balushi
 */

public class user {
    String userName;
    String lastName;
    String firstName;
    String password;

    public user() {
        this.userName = "";
        this.lastName = "";
        this.firstName = "";
        this.password = "";
    }

    public user(String userName, String lastName, String firstName, String password) {
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean createUser(user u) {
        String csv = "user.csv";
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csv, true));
            String user = u.getFirstName()+","+u.getLastName()+","+u.getUserName()+","+u.getPassword();
            String [] record = user.split(",");
            writer.writeNext(record);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<user> readFile(String fileName) throws IOException {
        if (fileName.isEmpty()){
            fileName = "user.csv";
        }
        List<user> result = new ArrayList<user>();
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        try {
            // Read first line
            String line = br.readLine();
            // Make sure file has correct headers
            if (line==null) throw new IllegalArgumentException("File is empty");

            // Run through following lines
            while ((line = br.readLine()) != null) {
                // Break line into entries using comma
                String[] items = line.split(",");
                try {
                    // If there are too many entries, throw a dummy exception, if
                    // there are too few, the same exception will be thrown later
                    if (items.length>4) throw new ArrayIndexOutOfBoundsException();
                    // Convert data to person record
                    user person = new user();
                    person.setFirstName(removeFirstandLast(items[0]));
                    person.setLastName (removeFirstandLast(items[1]));
                    person.setUserName(removeFirstandLast(items[2]));
                    person.setPassword(removeFirstandLast(items[3]));
                    result.add(person);
                } catch (ArrayIndexOutOfBoundsException|NumberFormatException|NullPointerException e) {
                    // Caught errors indicate a problem with data format -> Print warning and continue
                    System.out.println("Invalid line: "+ line);
                }
            }
            return result;
        } finally {
            br.close();
        }
    }

    public Boolean isLogingSuceess(String username, String password){
        List<user> listUser = null;
        System.out.println("uName: ");
        try {
            listUser= new user().readFile("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("listUser: "+listUser.size());
        for (user u:listUser ) {
            System.out.println("input uName: "+username + " pass: "+password);
            System.out.println("uName: "+u.getUserName() + " pass: "+u.getPassword());
            System.out.println("cName: "+username.equalsIgnoreCase(u.getUserName()) + " pass: "+password.equalsIgnoreCase(u.getPassword()));
            if(username.equalsIgnoreCase(u.getUserName()) && password.equalsIgnoreCase(u.getPassword())){
                return true;
            }
        }
        return false;
    }

    public String removeFirstandLast(String str)
    {

        // Removing first and last character
        // of a string using substring() method
        str = str.substring(1, str.length() - 1);

        // Return the modified string
        return str;
    }
}
