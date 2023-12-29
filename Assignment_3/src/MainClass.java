import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp3 parser) { // Reads the whole expression token by token in 
        System.out.println(invoer);
        boolean isLVar = false;
        boolean isUVar = false;
        Token nieuw = null;

        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            if(invoer.charAt(i) == '\\' || invoer.charAt(i) == 'λ' || invoer.charAt(i) == '(' || invoer.charAt(i) == ')' || invoer.charAt(i) == ':' || invoer.charAt(i) == '^' || invoer.charAt(i) == '-') {
                if(isLVar || isUVar) { // Check for variables 
                    Token nieuwVarToken = new Token(var); 
                    parser.addToken(nieuwVarToken); 
                }
                if(invoer.charAt(i) == '-') {
                    i++;
                    if(invoer.charAt(i) == '>'){
                        nieuw = new Token("->");
                    }
                    else{
                        System.err.println("Syntax error: - not followed by >");
                        return false;
                    } 
                }
                else {
                    nieuw  = new Token(invoer.substring(i, i + 1)); //Add the substring to the tokenlist
                }
                parser.addToken(nieuw);
                isLVar = false; // Reset the variable
                isUVar = false; // Reset the variable
                var = ""; // Make variable empty again
            }

            else if(invoer.charAt(i) >= 'a' && invoer.charAt(i) <= 'z'){
               if(!isLVar){
                  isLVar = true;
               }
               var = var.concat(invoer.substring(i, i + 1));
            }

            else if(invoer.charAt(i) >= 'A' && invoer.charAt(i) <= 'Z') {
               if(!isUVar){
                  isUVar = true;
               }
               var = var.concat(invoer.substring(i, i + 1));
            }

            else if(invoer.charAt(i) >= '0' && invoer.charAt(i) <= '9') {
               if(isLVar || isUVar) {
                  var = var.concat(invoer.substring(i, i + 1));
               }
               else{
                  System.err.println("Syntax error: number first character of variable");
                  return false;
               }
            }

            else if(invoer.charAt(i) == '-'){
               i++;
               if(invoer.charAt(i) == '>'){
                  Token nieuwVarToken = new Token("->");
                  parser.addToken(nieuwVarToken);
               }
               else{
                  System.err.println("Syntax error: - not followed by >");
                  return false;
               }  
            }

            else if(invoer.charAt(i) == ' ') {// if space add variable to tokenlist
                if(isLVar || isUVar) {
                    Token nieuwVarToken = new Token(var);
                    parser.addToken(nieuwVarToken);
                }
                isLVar = false;
                isUVar = false;
                var = "";
            }

            else { // Fault has occured
                return false;
            }

        }

        if(isLVar || isUVar) {
            Token nieuwVarToken = new Token(var);
            parser.addToken(nieuwVarToken);
        }
        return true;
    }

    // Input must be read from file called filenaam
    private static String LeesFileExpressie(String filenaam) {
        try{
            Scanner filescanner = new Scanner(new File(filenaam)); // We initialize a new scanner
            StringBuilder expressie = new StringBuilder(); // We initialize a new stringbuilder
    
            while(filescanner.hasNextLine()){
                expressie.append(filescanner.nextLine()); // We add the next line to the stringbuilder
                expressie.append("_");
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
 
         //Scanner sc = new Scanner(System.in); // We initialize a new scanner
         //String string = sc.nextLine(); // We read the input of the user
 
         if(args.length != 1) {
             System.err.println("Please enter a filename");
             System.exit(1);
         }
 
         String string = args[0]; // We read the input of the user
         String alle_expressies = LeesFileExpressie(string);
         
         System.out.println("Uiteindelijke: ");
         System.out.println(alle_expressies);
         
         while(laatste_expressie < alle_expressies.length()){ // Check voor einde expressies
             ParserOp3 parser = new ParserOp3(); // We initialize a new object parser
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
         
         System.exit(exitStatus);
     }
}
