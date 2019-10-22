/*******************************
** Name    - Rohan Sam Thomas **
** UTDID   - RST170000        **
** Class   - CS2336           **
** Date    - 09/05/2019       **
** JDK     - 11               **
** IDE     - IntelliJ Idea    **
********************************/

/*
// Project Zero\\   Given an input file with max 30 players and  their
per ball performance, this program calculates each players's stats and
also prints out the player with the highest stats in each department
*/


// importing required libraries
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

// class main
public class Main {
    // private static int arrSize = 30;                            // declaring const global variable

    public static void main (String[] args) {
        Scanner fileName = new Scanner(System.in);              // to get file name from user
        // String inputFile;                                       // variable to hold file name


        // declaring input and output files
        File inFile = new File(fileName.nextLine());           // getting input file from user
        File outFile = new File("leaders.txt");  // declaring output file


        // opening input and output files
        Scanner in = null;                       // opening user entered file
        try {
            in = new Scanner(inFile);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        // will create an output file if not found

        // creating parallel arrays
        String[] names = new String[30];               // array to hold names
        int[] arr_h = new int[30];                     // array to hold hits
        int[] arr_bb = new int[30];                    // array to hold walks
        int[] arr_k = new int[30];                     // array to hold strikeouts
        int[] arr_hbp = new int[30];                   // array to hold hit  by pitch
        int[] arr_o = new int[30];                     // array to hold out
        int[] arr_s = new int[30];                     // array to hold sacrifices
        Double[] arr_BA = new Double[30];              // array to hold batting average
        Double[] arr_OB = new Double[30];              // array to hold On-base percentage

        /*

        Checks to see if file is open;
        If file is open , if-statement executes: if(true);
        If file is close, if-statement doesn't execute: if(false);

        */

        // total number of players
        // will increment each time the following while loop executes
        int no_Players = 0;

        if (inFile.canRead()) {
            assert in != null;
            while (in.hasNext()) {

                // getInput();
                String name = in.next();
                String record = in.next();

                //System.out.println(record + " --- " + name);
                // method to find batting records of each player and storing it into array
                Batting_Record(names, arr_h, arr_bb, arr_k, arr_hbp, arr_o, arr_s, name, record, no_Players);

                // increment after each line to account for each player
                no_Players = no_Players + 1;
            }
            // using try-catch to eliminate FileNotFound Exception
            try {
                // declaring a new PrintWriter to print out the values to the file
                PrintWriter out = new PrintWriter(outFile);             // opening a predefined output file
                // calling method to display player data
                Display_Player_Data(out, names, arr_h, arr_bb, arr_k, arr_hbp, arr_o, arr_s, no_Players, arr_BA, arr_OB);
                // calling method to find and  print the leaders of each section
                Display_Leaders(out, names, arr_h, arr_bb, arr_k, arr_hbp, no_Players, arr_BA, arr_OB);
                // closing outfile
                out.close();
                // if we cannot open outfile
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            // close input file
            in.close();
        }                   // end of file, no more information to read
    }                       // end of method main

    // method to find batting records of each player and storing it into array
    private static void Batting_Record (String[] names, int[] arr_h, int[] arr_bb, int[] arr_k,
                                        int[] arr_hbp, int[] arr_o, int[] arr_s, String name, String record, int index) {


        names[index] = name;
        int size = record.length();
        // go through the string and update the correct arrays
        for(int i = 0; i < size; i++) {
            // y holds the next char
            char y = record.charAt(i);
            // switch statement to update the correct array
            // H - hit, O - out, K - strikeouts, W - walks, P - hits per pitch, S - sacrifice,
            switch (y) {
                case 'H':
                    arr_h[index] = arr_h[index] + 1;
                    break;
                case 'O':
                    arr_o[index]++;
                    break;
                case 'K':
                    arr_k[index]++;
                    break;
                case 'W':
                    arr_bb[index]++;
                    break;
                case 'P':
                    arr_hbp[index]++;
                    break;
                case 'S':
                    arr_s[index]++;
                    break;
                    // check for invalid characters
                default:
                    break;
            }
        }
    }
    //
    private static void Display_Player_Data (PrintWriter outputStream, String[] names, int[] arr_h,
                                             int[] arr_bb, int[] arr_k, int[] arr_hbp, int[] arr_o,
                                             int[] arr_s, int index, Double[] arr_BA, Double[] arr_OB) {


        for(int i = 0; i < index; i++) {
            // print names
            outputStream.println(names[i]);

/*
            // finding batting average
            double BA = (arr_h[i])/(double) ((arr_h[i])+(arr_o[i])+(arr_k[i]));
            // finding On-base percentage
            double OB = (arr_h[i]+arr_bb[i]+arr_hbp[i])/(double)(arr_h[i]+arr_o[i]+arr_k[i]+arr_bb[i]+arr_hbp[i]+arr_s[i]);
*/

            // checking denominator of BA
            if (((arr_h[i]) + (arr_o[i]) + (arr_k[i])) != 0) {
                // printing BA
                outputStream.println("BA: " + BigDecimal.valueOf((arr_h[i]) / (double) ((arr_h[i]) + (arr_o[i]) + (arr_k[i]))).setScale(3, RoundingMode.HALF_UP));
                // storing value of BA into an array for comparison as double
                arr_BA[i] = BigDecimal.valueOf((arr_h[i]) / (double) ((arr_h[i]) + (arr_o[i]) + (arr_k[i]))).setScale(3, RoundingMode.HALF_UP).doubleValue();
            } else {
                // if denominator is 0, print the value as 0.000
                outputStream.println("BA: 0.000");
                // and store the value into array
                arr_BA[i] = 0.000;
            }
            // checking denominator of OB%
            if ((arr_h[i] + arr_o[i] + arr_k[i] + arr_bb[i] + arr_hbp[i] + arr_s[i]) != 0) {
                //  printing the  value of OB
                outputStream.println("OB%: " + BigDecimal.valueOf((arr_h[i] + arr_bb[i] + arr_hbp[i]) / (double) (arr_h[i] + arr_o[i] + arr_k[i] + arr_bb[i] + arr_hbp[i] + arr_s[i])).setScale(3, RoundingMode.HALF_UP));
                // storing value of OB for comparison as double
                arr_OB[i] = BigDecimal.valueOf((arr_h[i] + arr_bb[i] + arr_hbp[i]) / (double) (arr_h[i] + arr_o[i] + arr_k[i] + arr_bb[i] + arr_hbp[i] + arr_s[i])).setScale(3, RoundingMode.HALF_UP).doubleValue();
            } else {
                // if denominator is 0, print out the value of OB as 0.000
                outputStream.println("OB%: 0.000");
                // also store the value into the array
                arr_OB[i] = 0.000;
            }
            // printing leaders
            {
                // print out the values of H, BB, K, and HBP for each players
                outputStream.println("H: " + arr_h[i]);
                outputStream.println("BB: " + arr_bb[i]);
                outputStream.println("K: " + arr_k[i]);
                outputStream.println("HBP: " + arr_hbp[i]);
                outputStream.println("");
            }

        } // end of loop to print all players values

    } // end of procedure Display_Player_Data

    // method to find and display leaders
    private static void Display_Leaders (PrintWriter outputStream, String[] names, int[] arr_h,
                                         int[] arr_bb, int[] arr_k, int[] arr_hbp, int index, Double[] arr_BA, Double[] arr_OB) {
        // printing the leaders
        outputStream.println("LEAGUE LEADERS");

        // variables to compare largest and smallest values
        int largestVal = -99999;
        Double largestBA = -99999.00;
        Double largestOB = -99999.00;
        int smallestVal = 99999;


        // find largest Batting Average
        {
            // go through array to get largest BA
            for(int i = 0; i < index; i++) {
                // find largest BA
                if (arr_BA[i] > largestBA) {
                    largestBA = arr_BA[i];
                }
            }
            // printing BA
            outputStream.print("BA: ");
            int i = 0;
            for(int j = 0; j < index; j++) {
                // going through the arrays to find all largest values
                if (Double.compare(arr_BA[j], largestBA) == 0) {
                    if (i == 1) outputStream.print(", ");           // print ", " after the first one
                    outputStream.print(names[j]);                   // PRINT name/s of leaders
                    i = 1;                                          // goes to 1 after first player
                }

            }
            outputStream.printf(" %.3f\n", largestBA);              // printing the largest BA
        }


        // Find largest OB %
        {
            for(int j = 0; j < index; j++) {
                if (arr_OB[j] > largestOB) {
                    // find largest
                    largestOB = arr_OB[j];
                }
            }
            // printing largest OB names
            outputStream.print("OB%: ");
            int k = 0;
            // traversing through array to get all names with largest values
            for(int j = 0; j < index; j++) {
                if (Double.compare(arr_OB[j], largestOB) == 0) {
                    // print  "," from second name
                    if (k == 1) {
                        outputStream.print(", ");
                    }
                    outputStream.print(names[j]);
                    k = 1;
                }

            }
            // printing the largest OB%
            outputStream.printf(" %.3f\n", largestOB);
        }

        // Largest H
        {
            for(int j = 0; j < index; j++) {
                if (arr_h[j] > largestVal) largestVal = arr_h[j];
            }
            // printing all names with largest H
            outputStream.print("H: ");
            int k = 0;
            // traversing through array to get all players with largest H
            for(int j = 0; j < index; j++) {
                if (arr_h[j] == largestVal) {
                    if (k == 1) outputStream.print(", ");
                    outputStream.print(names[j]);
                    k = 1;
                }
            }
            // printing largest H value
            outputStream.println(" " + largestVal);
        }
        // resetting largestVal
        largestVal = -99999;
        // largest BB
        {
            for(int j = 0; j < index; j++) {
                if (arr_bb[j] > largestVal) largestVal = arr_bb[j];
            }
            // Printing players with largest BB
            outputStream.print("BB: ");
            int k = 0;
            // traversing through array to file players with largest BB
            for(int j = 0; j < index; j++) {
                if (arr_bb[j] == largestVal) {
                    if (k == 1) outputStream.print(", ");
                    outputStream.print(names[j]);
                    k = 1;
                }
            }
            // printing largest BB
            outputStream.println(" " + largestVal);
        }
        // resetting
        largestVal = -99999;
        // smallest K
        {
            for(int j = 0; j < index; j++) {
                if (arr_k[j] < smallestVal) smallestVal = arr_k[j];
            }
            // printing all players with smallest K
            outputStream.print("K: ");
            int k = 0;
            for(int j = 0; j < index; j++) {
                if (arr_k[j] == smallestVal) {
                    if (k == 1) outputStream.print(", ");
                    outputStream.print(names[j]);
                    k = 1;
                }
            }
            // printing smallest K
            outputStream.println(" " + smallestVal);
        }

        // printing player with largest HBP
        {
            for(int j = 0; j < index; j++) {
                if (arr_hbp[j] > largestVal) {
                    largestVal = arr_hbp[j];
                }
            }
            // printing all players with largest HBP
            outputStream.print("HBP: ");
            int k = 0;
            for(int j = 0; j < index; j++) {
                if (arr_hbp[j] == largestVal) {
                    if (k == 1) outputStream.print(", ");
                    outputStream.print(names[j]);
                    k = 1;
                }
            }
            // printing largest HBP value
            outputStream.println(" " + largestVal);
        }
    }
}