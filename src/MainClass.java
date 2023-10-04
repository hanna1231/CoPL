import java.util.Scanner;

public class MainClass {

    public static boolean leesIn(String invoer, Parser parser) {
        boolean isVar = false;
        String var = "";
        
        for(int i = 0; i < invoer.length(); i++) {
            System.out.println("leesIn: " + invoer.charAt(i));
            if(invoer.charAt(i) == '\\' || invoer.charAt(i) == '(' || invoer.charAt(i) == ')') {
                if(isVar) {
                    Token nieuwVarToken = new Token(var);
                    parser.addToken(nieuwVarToken); 
                }
                Token nieuw  = new Token(invoer.substring(i, i + 1));
                parser.addToken(nieuw);
                isVar = false;
            }

            else if((invoer.charAt(i) >= '0' && invoer.charAt(i) <= '9' ) || (invoer.charAt(i) >= 'a' && invoer.charAt(i) <= 'z' ) || (invoer.charAt(i) >= 'A' && invoer.charAt(i) <= 'Z')) {
                isVar = true;
                var = var.concat(invoer.substring(i, i + 1));
            }

            else if(invoer.charAt(i) == ' ') {
                if(isVar) {
                    Token nieuwVarToken = new Token(var);
                    parser.addToken(nieuwVarToken);
                }
            }

            else {
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
        Parser parser = new Parser();
        System.out.println("Please enter a string:");

        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        leesIn(string, parser);
        parser.parse();
        sc.close();
        System.out.println(string);
        
    }
    
}
