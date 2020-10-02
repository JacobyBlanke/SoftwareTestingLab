package com.company;
/**
 * Name: Jacoby Blanke
 * Program Name: Software Testing
 * Date: 09/25/2020
 * Extra: Option to sort by Last Name
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
public class Pair {
    static boolean[] tester = new boolean[33], coder = new boolean[33]; //tester and coder keep track of whether or not someone has already been a tester or a coder, changing to true when they are used
    static boolean blockSame, testerSort, bothSort, firstDone; 
    static int lastRand, studentsLeft = 0;
    static String lastStudent = "";
    static String[] sortedTester = new String[33], sortedCoder = new String[33], arguments; //sortedTester has the pairs with the Tester listed first, and sortedCoder has them with the Coder first
    static void sort(String[] array, int count) { //method to sort and format the array by alphabetical order
        String temp;
        for (int i = 0; i < count; i++) { //iterates through and compares elements in the array to sort them
            for (int j = i + 1; j < count; j++) { 
                if (array[i].compareTo(array[j]) > 0) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        if (bothSort && !firstDone)
            System.out.println("\t\tTESTER\t\t\t\t\t\t   CODER"); //If its printing both by Coder and Tester on the first run
        else if (bothSort && firstDone)
            System.out.println("\t\tCODER\t\t\t\t\t\t   TESTER"); //if printin by both coder and tester on the second run
        else if (testerSort)
            System.out.println("\t\tTESTER\t\t\t\t\t\t   CODER"); //if only printing by tester
        else
            System.out.println("\t\tCODER\t\t\t\t\t\t   TESTER"); //if only printing by coder
        if (arguments.length > 1 && arguments[1].equals("--last-name"))
            System.out.format("%-50s %-50s", (String.format("%-15s %-15s %-15s", "Last Name", "First Name", "Block")), (String.format("%-15s %-15s %-15s", "Last Name", "First Name","Block")));
        else
            System.out.format("%-50s %-50s", (String.format("%-15s %-15s %-15s", "First Name", "Last Name", "Block")), (String.format("%-15s %-15s %-15s", "First Name", "Last Name","Block")));
        System.out.print("\n----------------------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) { //print each non-null array element
            if (array[i] != null)
                System.out.print(array[i]);
        }
        System.out.print("\n");
        firstDone = true;
    }
    static void pair(String[] first, String[] last, int[] block, int i) {
        String tmp1, tmp2;
        int random = new Random().nextInt(33);
        if (studentsLeft == 0)  
            return;
        for (;;) { //infinitely loop through random spots in the name array, til it finds someone who is available as a coder 
            if (!coder[random] && first[random] != last[random]) {
                if (arguments.length > 1 && arguments[1].equals("--last-name")) 
                    tmp1 = String.format("%-15s %-15s %-15s", last[random], first[random], block[random]); //if the --last-name option is used, it will sort by last names
                else
                    tmp1 = String.format("%-15s %-15s %-15s", first[random], last[random], block[random]); //formats the name and block spaced out
                coder[random] = true;
                lastRand = random;
                break;
            } 
            random = new Random().nextInt(33);
        }
        for (;;) { //does the same as above, but it has to find someone available as a tester instead
            if (!tester[random] && (random != lastRand) && first[random] != last[random]) { //if the person has not been a tester and isnt the same as the previous person and isn't a null array element
                if (arguments.length > 1 && arguments[1].equals("--last-name")) 
                    tmp2 = String.format("%-15s %-15s %-15s", last[random], first[random], block[random]);
                else
                    tmp2 = String.format("%-15s %-15s %-15s", first[random], last[random], block[random]); 
                tester[random] = true;
                break;
            } 
            random = new Random().nextInt(33);
        }
        studentsLeft -= 1;
        sortedCoder[i] = "\n" + String.format("%-50s %-50s", tmp1, tmp2); //load the coder oriented and tester oriented lines into arrays
        sortedTester[i] = "\n" + String.format("%-50s %-50s", tmp2, tmp1);
    }
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] firstName = new String[33], lastName = new String[33];
        arguments = args;
        String choice;
        int[] block = new int[33];
        int blockSort, commaLocation, rand1, rand2, studentsTotal, count = 0, studentCount = 0;
        try {
            System.out.println("What block? (0 for all)"); //get what block we pair people in
            blockSort = Integer.parseInt(in.readLine());
            System.out.println("Sort? (T for tester, C for Coder, A for all)"); 
            choice = in.readLine();
            if (choice.startsWith("T")) {
                testerSort = true;
            } else if (choice.startsWith("A"))
                bothSort = true;
            if (!(blockSort == 0))
                blockSame = true;
            BufferedReader read = new BufferedReader(new FileReader(args[0])); //read in file path as a commandline argument; Using bufferedReader over scanner as its faster/more efficient
            String line = read.readLine();
            do {
                if (blockSame && Character.getNumericValue(line.charAt(0)) == blockSort) { //goes through the class list and loads info into arrays, only those that are compatible with the selected blocks
                    block[count] = Character.getNumericValue(line.charAt(0));
                    line = line.substring(2);
                    commaLocation = line.indexOf(",");
                    firstName[count] = line.substring(commaLocation + 1);
                    lastName[count] = line.substring(0, commaLocation);
                    count++;
                    line = read.readLine();
                } else if (!blockSame) {
                    block[count] = Character.getNumericValue(line.charAt(0));
                    line = line.substring(2);
                    commaLocation = line.indexOf(",");
                    firstName[count] = line.substring(commaLocation + 1);
                    lastName[count] = line.substring(0, commaLocation);
                    count++;
                    line = read.readLine();
                } else
                    line = read.readLine();
            } while (line != null);
        } catch (Exception e) {
            System.out.println(e);
        }
        for (int i = 0; i < block.length; i++) {
            if (block[i] != 0)
                studentsLeft++;
        }
        studentsTotal = studentsLeft;
        for (int i = 0; i <= studentsTotal; i++)
            pair(firstName, lastName, block, i);
        for (int i = 0; i < sortedTester.length; i++) {
            if (sortedTester[i] != null)
                studentCount++;
        }
        if (bothSort) {
            sort(sortedTester, studentCount);
            studentsTotal = studentsLeft;
            System.out.print("\n\n\n\n");
            sort(sortedCoder, studentCount);
        } else if (testerSort)
            sort(sortedTester, studentCount);
        else
            sort(sortedCoder, studentCount);
    }
}
