import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class diceroller{



  public static void main(String[] args) {
    // initialise map
    Map<String, String> macros = new HashMap<String, String>();
    // load the saved macros
    try{
      FileInputStream fileIn = new FileInputStream("./macros.ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      macros = (Map<String, String>) in.readObject();
      in.close();
      fileIn.close();
    }catch (IOException i) {
      // if no file is found, make a new one
      writeMacros(macros);

    }catch (ClassNotFoundException c) {
      System.out.println("Map not found");
      c.printStackTrace();
      return;
    }

    //loop the menu
    boolean done = false;
    boolean donem = false;
    while(!done){

      //getting an input
      Scanner scanner = new Scanner(System.in);
      System.out.println("Type a diceroll or macro, enter m to add a new macro, e to exit:");
      String input = scanner.next();
      if (input.equals("e")){
        done = true;
      }else if(input.equals("m")){
        donem = false;
        String macroname;
        String macrovalue;
        while (!donem){
          System.out.println("/*/*/*/* Macro Options */*/*/*/");
          System.out.println("1. Add a new macro");
          System.out.println("2. Remove a macro");
          System.out.println("3. Print all macros");
          System.out.println("4. Exit macro options");
          System.out.print("Choose an option:");
          input = scanner.next();
          switch(input){
            case "1":
              System.out.print("Type the name of the new macro: ");
              macroname = scanner.next();
              System.out.print("Type the diceroll for this macro: ");
              macrovalue = scanner.next();
              macros.put(macroname,macrovalue);
              writeMacros(macros);
              System.out.println("New macro '" + macroname + "' has been added");
              break;
            case "2":
            case "3":
              System.out.println(macros.keySet());
              break;
            case "4":
              donem =true;
            break;
            default:
            System.out.println("You typed an invalid option.");
          }
        }

      }else if (macros.containsKey(input)){
        roll(macros.get(input));
      }else{
      //the dice rolling
      parse(input);
      }
    }
  }

  private static int roll(String input){
    Random rand = new Random();
    String parts[] = input.split("d");
    int total = 0;
    ArrayList<Integer> rolls = new ArrayList<Integer>();
    int temp =0;
    for(int i=0; i<Integer.parseInt(parts[0]);i++){
      temp = rand.nextInt(Integer.parseInt(parts[1])) + 1;
      rolls.add(temp);
      total = total + temp;
    }
    System.out.print(total + " : ");

    for(int i=0; i<Integer.parseInt(parts[0]);i++){
      System.out.print(rolls.get(i) + " ");
    }
    System.out.println("");
    return total;
  }

  private static void writeMacros(Map<String, String> macros){
    try {
       FileOutputStream fileOut = new FileOutputStream("./macros.ser");
       ObjectOutputStream out = new ObjectOutputStream(fileOut);
       out.writeObject(macros);
       out.close();
       fileOut.close();
    } catch (IOException i) {
       i.printStackTrace();
    }
  }

  private static void parse(String input){
    String working = input;
    working = working.replaceAll("\\s", "");
    System.out.println(working);
    String parts[] = input.split("\\+");
    int results[] = new int[parts.length];
    int total = 0;
    System.out.println(parts.length);
    System.out.println(parts[0]);
    System.out.println(parts[1]);
    for(int i = 0; i<parts.length; i++){
      results[i] = roll(parts[i]);
      total = total +results[i];
    }

    System.out.print("Total: ");
    for(int i = 0; i<results.length; i++){
      System.out.print(results[i]);
      if((i+1)<results.length){
        System.out.print(" + ");
      }
    }
    System.out.println(" = " + total);

  }
}
