import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp2 parser) { // Reads the whole expression token by token in 
        boolean isVar = false;
        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            // System.out.println("leesIn: " + invoer.charAt(i)); // Check if the string is read in correctly
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
                // System.out.println("Dit werkt niet loser");
                return false;
            }

        }

        if(isVar) {
            Token nieuwVarToken = new Token(var);
            parser.addToken(nieuwVarToken);
        }
        // System.out.println("Yes dit werkt");
        return true;
    }


    private static String LeesFileExpressie(String filenaam) {
         
       try{
          Scanner filescanner = new Scanner(new File(filenaam)); // We initialize a new scanner
          StringBuilder expressie = new StringBuilder(); // We initialize a new stringbuilder

          while(filescanner.hasNextLine()){
             expressie.append(filescanner.nextLine()); // We add the next line to the stringbuilder
             expressie.append("_");
                //System.out.println(expressie.toString());
          }
          filescanner.close(); // We close the scanner
          return expressie.toString(); // We return the stringbuilder as a string
       }
       catch(FileNotFoundException e){
          System.err.println("File not found");
          return null;
       }
     }

    
    public static void main(String[] args) {
       
      // Make it so that the user can input multiple expressions
        int index = 0; //Iterator which loops over the substrings, so you can read in multiple expressions
        int laatste_expressie = 0; //Keeps track of where the last expression has been built
        // System.out.println("Please enter a string:");
        int exitStatus = 1;

        //String filenaam = sc.nextLine(); // We read the input of the user --> zodat we niet de hele tijd de filenaam moeten invoeren

        //String filenaam = "expressie.txt";

        Scanner sc = new Scanner(System.in); // We initialize a new scanner
        String string = sc.nextLine(); // We read the input of the user
        String alle_expressies = LeesFileExpressie(string);
        
        System.out.println("Uiteindelijke: ");
        System.out.println(alle_expressies);
        
        while(laatste_expressie < alle_expressies.length()){ // Check voor einde expressies
            ParserOp2 parser = new ParserOp2(); // We initialize a new object parser
            String expressie = ""; 
            index++;
            while(index < alle_expressies.length() && alle_expressies.charAt(index) != '_') { //Zolang geen spatie, maak de substring verder
               index++;
            }
            expressie = alle_expressies.substring(laatste_expressie, index); // Maak de substring
            System.out.println("Substring:");
            System.out.println(expressie);
            if(leesIn(expressie, parser)) {
               exitStatus = parser.parse();
            }
            laatste_expressie = index + 1;
        }
        sc.close(); // We close the scanner
        // System.out.println(exitStatus);

        System.exit(exitStatus);
    }
    
}
