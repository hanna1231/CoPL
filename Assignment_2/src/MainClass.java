import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp2 parser) { // Reads the whole expression token by token in 
        boolean isVar = false;
        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            System.out.println("leesIn: " + invoer.charAt(i)); // Check if the string is read in correctly
            if(invoer.charAt(i) == '\\' || invoer.charAt(i) == '(' || invoer.charAt(i) == ')') { // Check if the character is a lambda, paropen or parclose
                if(isVar) { // Check for variables 
                    Token nieuwVarToken = new Token(var); 
                    parser.addToken(nieuwVarToken); 
                }
                Token nieuw  = new Token(invoer.substring(i, i + 1)); //Add the substring to the tokenlist
                parser.addToken(nieuw);
                isVar = false; // Reset the variable
                var = ""; // Make variable empty again
            }

            else if((invoer.charAt(i) >= '0' && invoer.charAt(i) <= '9' ) || (invoer.charAt(i) >= 'a' && invoer.charAt(i) <= 'z' ) || (invoer.charAt(i) >= 'A' && invoer.charAt(i) <= 'Z')) { //If number or letter
               if((invoer.charAt(i) >= '0' && invoer.charAt(i) <= '9' ) && !isVar) { // If number and not variable
                    System.out.println("Syntax error: number first character of variable");
                    return false;
                }
                isVar = true;
                var = var.concat(invoer.substring(i, i + 1)); // Add the number or letter to variable
            }

            else if(invoer.charAt(i) == ' ') {// if space add variable to tokenlist
                if(isVar) {
                    Token nieuwVarToken = new Token(var);
                    parser.addToken(nieuwVarToken);
                }
                isVar = false;
                var = "";
            }

            else { // Fault has occured
                System.out.println("Dit werkt niet loser");
                return false;
            }

        }

        if(isVar) {
            Token nieuwVarToken = new Token(var);
            parser.addToken(nieuwVarToken);
        }
        System.out.println("Yes dit werkt");
        return true;
        
    }
    
    public static void main(String[] args) {
       
      // Make it so that the user can input multiple expressions
        ParserOp2 parser = new ParserOp2(); // We initialize a new object parser
        System.out.println("Please enter a string:");
        int exitStatus = 1;
        Scanner sc = new Scanner(System.in); // We initialize a new scanner
        String string = sc.nextLine(); // We read the input of the user
        
        if(leesIn(string, parser)) {
            exitStatus = parser.parse();
        }

        sc.close(); // We close the scanner
        System.out.println(exitStatus);

        System.exit(exitStatus);
    }
    
}
