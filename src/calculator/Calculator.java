/* SIMPLE CALCULATOR
 * Calculator program that can perform basic arithmetic operations such as addition(+), subtraction(-),
 * multiplication(*), division(/), power of(^) and percentage(%) calculations.
 * @author Padma Gokul
*/
package calculator;
import java.util.Stack;

//Implementation part of 'Calculator_main'
public class Calculator 
{
    public double evaluate(String expression) 
    {
        //Creating two stacks. For Numbers: 'values' and Operators: 'operators'
        Stack<Double> values = new Stack<Double>(); 
        Stack<Character> operators = new Stack<Character>(); 
        expression = expression.replace("(-", "(0-");
        //Double.parseDouble(expression);
        
        for (int index=0; index<expression.length();index++) {
           
            char token = expression.charAt(index);
            //If current token is a number, extract it with decimal values and push it to 'values' 
            if (operators.isEmpty() && values.isEmpty() && token=='-') {
                values.push((double) -1);
                operators.push('*');
            }
            
            else if (Character.isDigit(token)) {
                double before_point = 0;
                double after_point = 0;
                int count = 0;
                // There may be more than one digits in number
                while (Character.isDigit(token)) {
                    before_point = before_point * 10 + (token-'0');
                    index++;
                    if (index < expression.length()) {
                        token = expression.charAt(index);
                        //To evaluate values after decimal point
                        if (token=='.') {
                            index++;
                            token = expression.charAt(index);
                         // There may be more than one digits after decimal point
                            while (Character.isDigit(token)) {
                                  after_point = after_point * 10 + (token-'0');
                                  count++;
                                  index++;
                                  if (index < expression.length())
                                     token = expression.charAt(index);
                                  else
                                     break;
                                }
                            }
                    }
                    if (index < expression.length())
                        token = expression.charAt(index);
                    else
                        break;
                }
                //To append the values before and after the decimal point
                before_point = (double)(before_point + (after_point / Math.pow(10, count)));
                index--;
                values.push(before_point);
            }
            //If current token is an opening brace, push it to 'operators'
            else if (token=='(') 
                operators.push(token);
            //If closing brace encountered, solve entire brace
            else if (token==')') {
                while (operators.peek()!='(') {
                    values.push(arithmeticOperation(operators.pop(),values.pop(), values.pop()));
                }
                operators.pop();
            }
            //If current token is operator
            else if (token == '+' || token == '*' || token == '/' || token == '^' || token == '-' || token == '%') {
                /* While top operator(operator_2) of 'operators' has same or greater precedence to current token(operator_1), 
                pop and apply operator on top of 'operators' to top two elements in 'values'(BODMAS)
                Since '%' is not a part of BODMAS, add it to operators directly*/
                
                    while (!operators.isEmpty() && hasPrecedence(token,operators.peek()) && token!='%') {
                        values.push(arithmeticOperation(operators.pop(), values.pop(), values.pop()));
                    }    
                    operators.push(token);
            }
        }
        // Apply remaining operators in 'operators' to remaining values in 'values'
        while (!operators.isEmpty()) {
            values.push(arithmeticOperation(operators.pop(),values.pop(), values.pop()));
        }
        // Top of 'values' contains result, return it to user
        return values.pop();
    } 

    //Method that returns true if 'operator_2' has higher or same precedence as 'operator_1', otherwise returns false 
    public boolean hasPrecedence(char operator_1, char operator_2) 
    { 
        boolean true_or_false;
        if (operator_2 == '(' || operator_2 == ')') 
            true_or_false = false;
        else if ((operator_1 == '^')  && (operator_2 == '*' || operator_2 == '/' || operator_2 == '+' || operator_2 == '-')) 
            true_or_false = false;
        else if ((operator_1 == '*' || operator_1 == '/') && (operator_2 == '+' || operator_2 == '-')) 
            true_or_false = false; 
        else
            true_or_false = true;
        return true_or_false;
    } 

    // Method to apply an operators 'operators' on values 'number_1' and 'number_2'. Returns the result to be pushed to 'values' 
    public double arithmeticOperation(char operators, double number_1, double number_2) 
    { 
        double result = 0;
        switch (operators) { 
        case '+':
            result = number_1 + number_2;
            break;
        case '-': 
            result = number_2 - number_1;
            break;
        case '*': 
            result = number_1 * number_2;
            break;
        case '/':
            if (number_1 == 0)
                System.out.println("Denominator cannot be zero");
            else
                result = number_2 / number_1;
            break;
        case '^':
            result = Math.pow(number_2,number_1);
            break;
        case '%':
            result = (float) ((number_2 / number_1) * 100);
            break;
        default:
            result=0;
        } 
        return result; 
    } 
    
    //Method that returns boolean output when actual output is equal to floor output
    public boolean isWhole(double output) {
        boolean result;
        if (Math.floor(output) != output)
            result = false;
        else 
            result = true;
        return result;
    }  
    
    public static void clearScreen() {  
        System.out.println("\033[H\033[2J");  
        System.out.flush();  
    }
}

