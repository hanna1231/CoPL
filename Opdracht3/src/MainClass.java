import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp3 parser) { // Reads the whole expression token by token in 
        boolean isVar = false;
        boolean isLVar = false;
        boolean isUVar = false;

        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
            System.out.println("leesIn: " + invoer.charAt(i)); // Check if the string is read in correctly
            if(invoer.charAt(i) == '\\' || invoer.charAt(i) == '(' || invoer.charAt(i) == ')' || invoer.charAt(i) == ':' || invoer.charAt(i) == '^') { // Check if the character is a paropen or parclose
                if(isLVar || isUVar) { // Check for variables 
                    Token nieuwVarToken = new Token(var); 
                    parser.addToken(nieuwVarToken); 
                }   
                Token nieuw  = new Token(invoer.substring(i, i + 1)); //Add the substring to the tokenlist
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
                  System.out.println("Syntax error: number first character of variable");
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
                  System.out.println("Syntax error: - not followed by >");
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
        ParserOp3 parser = new ParserOp3(); // We initialize a new object parser
        System.out.println("Please provide a text file to read in from:");

        int exitStatus = 1;
        Scanner sc = new Scanner(System.in); // We initialize a new scanner
       
        //String filenaam = sc.nextLine(); // We read the input of the user --> zodat we niet de hele tijd de filenaam moeten invoeren

        String filenaam = "expressie.txt";
        
        String string = LeesFileExpressie(filenaam); // We read the file

        if(string != null && leesIn(string, parser)) {
            //exitStatus = parser.parse();
            System.out.println("GA ik hierin");
            parser.printList();
        }

        sc.close(); // We close the scanner
        System.out.println(exitStatus);


        System.exit(exitStatus); 
    }
}
