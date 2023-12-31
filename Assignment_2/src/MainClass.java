import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp2 parser) { // Reads the whole expression token by token in 
        boolean isVar = false;
        int dot = 0;
        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            // System.out.println("leesIn: " + invoer.charAt(i)); // Check if the string is read in correctly
            if(invoer.charAt(i) == '\\' || invoer.charAt(i) == 'λ' || invoer.charAt(i) == '(' || invoer.charAt(i) == ')' || invoer.charAt(i) == '.') { // Check if the character is a lambda, paropen, parclose or dot
                if(isVar) { // Check for variables 
                    Token nieuwVarToken = new Token(var); 
                    parser.addToken(nieuwVarToken); 
                }
                if(invoer.charAt(i) == '.') {
                    Token newParantheses = new Token("(");
                    parser.addToken(newParantheses);
                    dot++;
                }
                else if(invoer.charAt(i) == ')') {
                    for(int j = 0; j < dot; j++) {
                        Token newParantheses = new Token(")"); // Add closing parantheses for every dot
                        parser.addToken(newParantheses);
                    }
                    dot = 0;
                    Token nieuw  = new Token(invoer.substring(i, i + 1)); //Add the substring to the tokenlist
                    parser.addToken(nieuw);
                }
                else {
                    Token nieuw  = new Token(invoer.substring(i, i + 1)); //Add the substring to the tokenlist
                    parser.addToken(nieuw);
                }
                isVar = false; // Reset the variable
                var = ""; // Make variable empty again
            }

            else if((invoer.charAt(i) >= '0' && invoer.charAt(i) <= '9' ) || (invoer.charAt(i) >= 'a' && invoer.charAt(i) <= 'z' ) || (invoer.charAt(i) >= 'A' && invoer.charAt(i) <= 'Z')) { //If number or letter
               if((invoer.charAt(i) >= '0' && invoer.charAt(i) <= '9' ) && !isVar) { // If number and not variable
                    System.err.println("Syntax error: number first character of variable");
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
                System.err.println("Character not allowed");
                return false;
            }

        }

        if(isVar) {
            Token nieuwVarToken = new Token(var);
            parser.addToken(nieuwVarToken);
        }
        if(dot > 0) {
            for(int j = 0; j < dot; j++) {
                Token newParantheses = new Token(")"); // Add closing parantheses for every dot
                parser.addToken(newParantheses);
            }
        }
        return true;
    }

    private static String LeesFileExpressie(String filenaam) {
         
        try{
           Scanner filescanner = new Scanner(new File(filenaam)); // We initialize a new scanner
           StringBuilder expressie = new StringBuilder(); // We initialize a new stringbuilder
 
           if(filescanner.hasNextLine()){
                expressie.append(filescanner.nextLine()); // We add the next line to the stringbuilder
           }; // We check if there is a next line
           filescanner.close(); // We close the scanner
           return expressie.toString(); // We return the stringbuilder as a string
        }
        catch(FileNotFoundException e){
           System.err.println("File not found");
           return null;
        }
    }


    public static void main(String[] args) {
        ParserOp2 parser = new ParserOp2(); // We initialize a new object parser
        int exitStatus = 1;         

        if(args.length != 1) {
        System.err.println("Please enter a filename");
        System.exit(1);
        }

        String filenaam = args[0]; // We read the input of the user
        
        String string = LeesFileExpressie(filenaam); // We read the file

        if(string != null && leesIn(string, parser)) {
            exitStatus = parser.parse();
        }
        System.exit(exitStatus); 
      }
    
}
