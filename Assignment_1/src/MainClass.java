import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, ParserOp1 parser) { // Reads the whole expression token by token in 
        boolean isVar = false;
        int dot = 0;
        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) { // Iterate over the whole string
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
    
    public static void main(String[] args) {
        ParserOp1 parser = new ParserOp1(); // We initialize a new object parser
        int exitStatus = 1;
        boolean end = false;

        Scanner sc = new Scanner(System.in); // We initialize a new scanner

        while(!end && sc.hasNextLine()){
            String string = sc.nextLine(); // We read the input of the user
            System.out.println(string);
            
            if(string.equals("exit") ){
                break;
            }

            if(leesIn(string, parser)) {
                exitStatus = parser.parse();
                if(exitStatus == 1) {
                    end = true;
                }
            }
            string = "";
        }
        
        System.exit(exitStatus);
        sc.close(); // We close the scanner
    }
}
