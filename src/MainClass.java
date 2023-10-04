import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, Parser parser) { // Reads the whole expression token by token in 
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
        Parser parser = new Parser(); // We initialize a new object parser
        System.out.println("Please enter a string:");
        boolean exitStatus = false;

        Scanner sc = new Scanner(System.in); // We initialize a new scanner
        String string = sc.nextLine(); // We read the input of the user
        leesIn(string, parser);
        exitStatus = parser.parse();
        sc.close();
        System.out.println(exitStatus);

        if(exitStatus) {
            System.exit(0);
        }

        else {
            System.exit(1);
        }
    }
    
}
