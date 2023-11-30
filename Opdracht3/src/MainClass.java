import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, Parser parser) { // Reads the whole expression token by token in 
        boolean isVar = false;
        boolean isLVar = false;
        boolean isUVar = false;

        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            System.out.println("leesIn: " + invoer.charAt(i)); // Check if the string is read in correctly
            if(invoer.charAt(i) == '(' || invoer.charAt(i) == ')') { // Check if the character is a paropen or parclose
                if(isLVar && isUVar) { // Check for variables 
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
                else if(invoer.charAt(i) >= 'a' && invoer.charAt(i) <= 'z' ) {
                  
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

    private static String LeesFileExpressie(String filenaam) {
         
      try{
         Scanner filescanner = new Scanner(new File(filenaam)); // We initialize a new scanner
         StringBuilder expressie = new StringBuilder(); // We initialize a new stringbuilder

         while(filescanner.hasNextLine()){
               expressie.append(filescanner.nextLine()); // We add the next line to the stringbuilder
         }
         filescanner.close(); // We close the scanner
         return expressie.toString(); // We return the stringbuilder as a string
      }
      catch(FileNotFoundException e){
         System.out.println("File not found");
         return null;
      }
    }
    
    public static void main(String[] args) {
       
      // Make it so that the user can input multiple expressions
        Parser parser = new Parser(); // We initialize a new object parser
        System.out.println("Please provide a text file to read in from:");

        int exitStatus = 1;
        Scanner sc = new Scanner(System.in); // We initialize a new scanner
        String filenaam = sc.nextLine(); // We read the input of the user
        
        String string = LeesFileExpressie(filenaam); // We read the file

        if(string != null && leesIn(string, parser)) {
            exitStatus = parser.parse();
        }

        sc.close(); // We close the scanner
        System.out.println(exitStatus);

        System.exit(exitStatus);
    }
    
}
