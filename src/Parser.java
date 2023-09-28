import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokenList = new ArrayList<Token>();

    private int iterator;

    private int openPars;

    private boolean error; // To show whether there's an error in the recursion

    public Parser() {
        iterator = 0;
        openPars = 0;
        error = false;
    }

    public void addToken(Token nieuwToken) {
        tokenList.add(nieuwToken);
    }

    public void printList() {
        for (Token token : tokenList) {
        System.out.println(token.value);
        }
    }

    public boolean parse() {
        if(tokenList.isEmpty()) {
            return false;
        }
        iterator = 0;
        openPars = 0;
        expr();
        return (!error && openPars == 0);
    }

    private void expr1() {
        if(iterator == tokenList.size()-1 || tokenList.get(iterator).isParClose()){
            return;
        }
        lexpr();
        expr1();
    }

    private void lexpr() {
        if(tokenList.get(iterator).isLambda()) {
            iterator++;
            if(tokenList.get(iterator).isVar()) {
                iterator++;
                lexpr();
            } 
            else {
                error = true;
            }
        }   
        else {
            pexpr();
        }
    }

    private void pexpr() {
        if(tokenList.get(iterator).isParOpen()) {
            openPars++;
            iterator++;
            expr();
            if(error) {
                return;
            } // There's an error in the code we don't continue
            if(tokenList.get(iterator).isParClose()) {
                openPars--;
                iterator++;
                
                
            }

            else {
                error = true;
            }
        }

        else if(tokenList.get(iterator).isVar()) {
            iterator++;  
        }
    }


    private void expr() {
        lexpr();
        expr1();
    }
}