import java.util.ArrayList;

public class ParserOp1 {

    private ArrayList<Token> tokenList = new ArrayList<Token>(); // Vector of tokens   

    private int iterator; // Iterator which iterates over the expression

    private int openPars; // Check how many open parenthesis there are

    private boolean error; // To show whether there's an error in the recursion

    private boolean next() {
        if(iterator+1 < tokenList.size()) {
            iterator++;
            // System.out.println("Next is true");
            return true;
        }
            // System.out.println("Next is false");
        return false;
    }

    public ParserOp1() {
        iterator = 0;
        openPars = 0;
        error = false;
    }

    public void addToken(Token nieuwToken) {
        tokenList.add(nieuwToken);
    }

    public void printList() {
        System.out.print("Output: ");
        for (int i = 0; i < tokenList.size(); i++) {
            // if(tokenList.get(i).isVar() && !(i+1 == tokenList.size() || tokenList.get(i+1).isParClose())) {
            //     System.out.println("(");
            // }
            System.out.print(tokenList.get(i).value);
            if(tokenList.get(i).isVar() && !(i+1 == tokenList.size() || tokenList.get(i+1).isParClose())) {
                System.out.print(" ");
            } // Only print a whitespace whenever there's no closing parentheses following or it isn't the end of line
        }
        System.out.print("\n");
    }

    public boolean parse() {
        // printList();
        if(tokenList.isEmpty()) { // Nothing in expression
            //System.out.println("Expression is empty");
            return false;
        }
        iterator = 0;
        openPars = 0;
        expr();
        if(iterator < tokenList.size()) { 
            //System.out.println("iterator: " + tokenList.get(iterator).value);
            //System.out.println("(Expression isn't valid)");
            return false;
        } // When the expression isn't finished but the parser is
        if(!error && openPars == 0) {
            printList();
            return true;
        }
        return false;
    }

    private void expr1() { // <expr1> ::= <lexpr><expr1> | e
        // System.out.println("expr1");
        // System.out.println("iterator: " + iterator + ", tokenList size: " + tokenList.size());
        if(iterator == tokenList.size() || tokenList.get(iterator).isParClose()){
            //System.out.println("Nested expression finished");
            return;
        }
        //System.out.println("Continuing in expr1");
        lexpr();
        if(error) {
            return;
        }
        expr1();
    }

    private void lexpr() { // <lexpr> ::= <pexpr> |  \l<var><lexpr>
        //System.out.println("lexpr");
        if(tokenList.get(iterator).isLambda()) {
            Token openingToken = new Token("(");
            tokenList.add(iterator, openingToken); // Add parentheses for ambiguity
            if(next() && next() && tokenList.get(iterator).isVar()) { // Jump to variable
                //System.out.println("var (" + tokenList.get(iterator).value + ")");
                if(!next()) { // If no expression --> error
                    System.out.println("Missing expression after lambda");
                    error = true;
                    return;
                }
                lexpr();
                if(error) {
                    return;
                }
                Token closingToken = new Token(")"); // Add parentheses for ambiguity
                tokenList.add(iterator, closingToken);
                iterator++;
                
            } 
            else {
                error = true;
                System.out.println("(missing variable after lambda)");
            }
        }   
        else {
            pexpr();
        }
    }

    private void pexpr() { // <pexpr> ::= <var> | (<expr>)
        // System.out.println("pexpr");
        if(tokenList.size() == iterator) {
            System.out.println("Not a complete expression");
            error = true;
            return;
        } // If expression stop here it is not a complete one

        if(tokenList.get(iterator).isParOpen()) { // <pexpr> --> (<expr>)
            openPars++;
            if(!next()) {
                return;
            }
            expr();
            if(error) {
                return;
            } // There's an error in the code we don't continue
            if(iterator != tokenList.size() && tokenList.get(iterator).isParClose()) { // Check for closing parenthesis
                openPars--;
                iterator++;                
            }

            else { // Error if there is no closing parenthesis
                error = true;
                System.out.println("Missing closing parenteses");
            }
        }

        else if(tokenList.get(iterator).isVar()) { // If token is not opening parenthesis --> must be a var
            //System.out.println("var (" + tokenList.get(iterator).value + ")");
            iterator++;
        }
    
        else if(tokenList.get(iterator).isParClose()){ // If closing parenthesis --> error
            System.out.println("(missing expression after opening parenthesis)");
            error = true;
        }
    }
    
    private void expr() { // <expr> ::= <lexpr><expr1>
        // System.out.println("expr");
        int iAtStart = iterator;
        lexpr();
        if(error) {
            return;
        } // Don't continue after an error
        // if(iterator == tokenList.size() || tokenList.get(iterator).isParClose()) {
        //     Token openingToken = new Token("(");
        //     tokenList.add(iAtStart, openingToken);
        //     iterator++;
        expr1();
    }
}