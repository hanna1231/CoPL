import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp3 parser) { // Reads the whole expression token by token in 
        System.out.println(invoer);
        boolean isLVar = false;
        boolean isUVar = false;
        // Token nieuw = null;
        int dot = 0;

        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            if(invoer.charAt(i) == '\\' || invoer.charAt(i) == 'λ' || invoer.charAt(i) == '(' || invoer.charAt(i) == ')' || invoer.charAt(i) == ':' || invoer.charAt(i) == '^' || invoer.charAt(i) == '-' || invoer.charAt(i) == '.') {
                if(isLVar || isUVar) { // Check for variables 
                    Token nieuwVarToken = new Token(var); 
                    parser.addToken(nieuwVarToken); 
                }
                if(invoer.charAt(i) == '-') {
                    i++;
                    if(invoer.charAt(i) == '>'){
                        Token nieuw = new Token("->");
                        parser.addToken(nieuw);
                    }
                    else{
                        System.err.println("Syntax error: - not followed by >");
                        return false;
                    } 
                }
                else if(invoer.charAt(i) == '.') {          //checks for dot
                    Token newParantheses = new Token("(");
                    parser.addToken(newParantheses);
                    dot++;                                  //for the closing parentheses
                }
                else if(invoer.charAt(i) == ')') {
                    for(int j = 0; j < dot; j++) {
                        Token newParantheses = new Token(")");
                        parser.addToken(newParantheses);
                    }
                    dot = 0;
                    Token nieuw = new Token(invoer.substring(i, i + 1));    //Add the substring to the tokenlist
                    parser.addToken(nieuw);
                }
                else {
                    Token nieuw  = new Token(invoer.substring(i, i + 1)); //Add the substring to the tokenlist
                    parser.addToken(nieuw);
                }
                // parser.addToken(nieuw);
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

        if(dot > 0) {
            for(int j = 0; j < dot; j++) {
                Token newParantheses = new Token(")");  //add closing parenthesis for every dot
                parser.addToken(newParantheses);
            }
            dot = 0;
        }


        return true;
    }

    // Input must be read from file called filenaam
    private static String LeesFileExpressie(String filenaam) {
        try{
            Scanner filescanner = new Scanner(new File(filenaam)); // We initialize a new scanner
            StringBuilder expressie = new StringBuilder(); // We initialize a new stringbuilder
    
            while(filescanner.hasNextLine()){
                expressie.append(filescanner.nextLine() + "\n"); // We add the next line to the stringbuilder
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
        int index = 0; //Iterator which loops over the substrings, so you can read in multiple expressions
        int laatste_expressie = 0; //Keeps track of where the last expression has been built
        int exitStatus = 1;


        if(args.length != 1) {
            System.err.println("Please enter a filename");
            System.exit(1);
        }

        String string = args[0]; // We read the input of the user
        String alle_expressies = LeesFileExpressie(string);
        
        while(laatste_expressie < alle_expressies.length()){ // Check voor einde expressies
            ParserOp3 parser = new ParserOp3(); // We initialize a new object parser
            String expressie = ""; 
            index++;
            while(alle_expressies.charAt(index) != '\n') { //Zolang geen spatie, maak de substring verder
            index++;
            }
            expressie = alle_expressies.substring(laatste_expressie, index); // Maak de substring
            if(leesIn(expressie, parser)) {
            exitStatus = parser.parse();
            }
            System.out.println("\n");
            laatste_expressie = index + 1;
        }
        
        System.exit(exitStatus);
    }
}
