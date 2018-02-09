/*
 *  Copyright (C) Kristopher Sewell - All Rights Reserved 
 *  Written by Kristopher Sewell, Jan 2018
 *  
 *  Name: Kristopher Sewell
 *  NETID: kjs170430
 *  Class: CE2336.002
 * 
 *  File: ./TieFigher/Main.java
 */

package TieFighter;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.lang.Math;
import java.io.FileNotFoundException;


public class Main {


  public static void area(File file, String[] names, Double[][][] data) throws FileNotFoundException {
    if(file.canWrite()) {
      PrintWriter out = new PrintWriter(file);
      
      
      //print to file
      for(int i = 0; i < names.length; i++) {
        out.print(names[i]);
        out.printf(" %.2f\n",(0.5 * Math.abs(sum(data[i]))));
      }
      out.close();
    }
  }

  private static double sum(Double[][] data) {
    //find the last coord
    int lastPOS = 0;
    for(int i = 1; i < data.length; i++) {
      if (data[0][0] == data[i][0] && data[0][1] == data[i][1]) {
        lastPOS = i;
        break;
      } 
    }
    //calculate and return the sum
    double sum = 0;
    for (int i = 0; i < lastPOS-2; i++) {
      sum += (data[i+1][0] + data[i][0]) * (data[i+1][1] + data[i][1]);
    }
    return sum;
  }

  public static void parse(File file, String[] names, Double[][][] data) throws FileNotFoundException {
    if(file.exists() && file.canRead()) {
      Scanner input = new Scanner(file);
      int i = 0; //index for each pilot
      int j = 0; //index for coord
      
      while(input.hasNext()) {
        String temp = input.next();
        System.out.println(temp);
        if(checkName(temp)) {
          if (names[i].isEmpty()) {
            names[i] = temp;
          } else {
            names[i] += ' ' + temp; 
          }
        } else {
          i++; //lock name use i-1 for coord input
          while(!checkName(temp) && input.hasNext()) {
            int commaPOS = temp.indexOf(',');
            //format of x,y
            data[i-1][j][0] = Double.parseDouble(temp.substring(0,commaPOS));
            data[i-1][j][1] = Double.parseDouble(temp.substring(commaPOS+1));
            j++;
            temp = input.next();
            System.out.println(temp);
          }
          j = 0; //reset j back to 0
        }      
      }
      input.close();
    }
  }

  private static boolean checkName(String s) {
    char[] check = {'0','1','2','3','4','5','6','7','8','9',',','.'};
    for(char c: check) {
      if(s.charAt(0) == c) {
        return false;
      }
    }
    return true;
  }


  public static void main(String[] args) {
    try {
      //allocated required resources
      String[] pilotnames = new String[20];
      Double[][][] coord = new Double[20][16][2];
      File input = new File("pilot_routes.txt");
      File output = new File("pilot_areas.txt");

      //get input from file
      parse(input,pilotnames,coord);

      //calculate and return data
      area(output,pilotnames,coord);

      System.exit(0);

    } catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage());
      System.exit(1);
    }


  }

}


