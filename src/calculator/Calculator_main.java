package calculator;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator_main {
    
  //Main method
    public static void main(String[] args) throws IOException {
        Scanner getinp = new Scanner(System.in);
        double output;
        int choice;
        Calculator calculator = new Calculator();
        while(true)
        {
            System.out.print(" 1.Calculation \n 2.Clear Screen \n 3.Exit Calculator \n Enter Your Choice.\n");
            //Check whether only integer is passed to choice
            try {
                choice = getinp.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.print("Your choice can only be an integer\n");
                getinp.nextLine();
                continue;
            }
            switch(choice)
            {
            case 1:
                System.out.println("Enter the expression\n");
                //Check whether the entered expression is in correct form
                try {
                    String expression = getinp.nextLine();
                    expression += getinp.nextLine();
                    output = calculator.evaluate(expression);
                    //If actual output = floored output(decimal values are zero), then remove those zeroes
                    if (calculator.isWhole(output)) {
                        //If the output is infinity, do not type cast it
                        if(output == Double.POSITIVE_INFINITY || output == Double.NEGATIVE_INFINITY)
                            System.out.println(output);
                        else
                            System.out.println((long)output);
                    }
                    else
                        System.out.println(output);
                }
                catch (Exception e) {
                    System.out.println("Check your expression\n");
                }
                break;
            case 2:
                Calculator.clearScreen();    
                System.out.println("Screen Cleared!");
                break;
            case 3:
                getinp.close();
                System.out.println("Calculator exited successfully.");
                System.exit(0);
            default:
                System.out.println("Invalid choice\n");
            }
        }
    }
}
