import java.util.ArrayList;

public class ParserOp1 {

    private ArrayList<Token> tokenList = new ArrayList<Token>(); // Vector of tokens   

    private int iterator; // Iterator which iterates over the expression

    private int openPars; // Check how many open parenthesis there are

    private boolean error; // To show whether there's an error in the recursion

    private boolean next() {
        if(iterator+1 < tokenList.size()) {
            iterator++;
            return true;
        }
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

    // printList is only called after parsing and assumes a correct grammar
    public void printList() {
        for (int i = 0; i < tokenList.size(); i++) {
            System.out.print(tokenList.get(i).value);
            if((tokenList.get(i).isVar() || tokenList.get(i).isParClose()) && !(i+1 == tokenList.size() || tokenList.get(i+1).isParClose())) {
                System.out.print(" ");
            } // Only print a whitespace whenever there's no closing parentheses following or it isn't the end of line
        }
        System.out.print("\n");
    }

    public int parse() {
        if(tokenList.isEmpty()) { // Nothing in expression
            System.err.println("Expression is empty");
            return 1;
        }
        iterator = 0;
        openPars = 0;
        expr();
        if(iterator < tokenList.size()) {
            System.err.println("Expression isn't valid");
            return 1;
        } // When the expression isn't finished but the parser is
        if(!error && openPars == 0) {
            printList();
            tokenList.clear(); // Clear the tokenlist for the next expression
            return 0;
        }
        tokenList.clear(); // Clear the tokenlist for the next expression
        return 1;
    }

    private void expr1(int start) { // <expr1> ::= <lexpr><expr1> | e
        if(iterator == tokenList.size() || tokenList.get(iterator).isParClose()){
            return;
        }
        lexpr();
        if(error) {
            return;
        }
        // Add parentheses to prevent ambiguity
        Token openingToken = new Token("(");
        tokenList.add(start, openingToken);
        iterator++; // Token added so iterator needs to increase
        Token closingToken = new Token(")");
        tokenList.add(iterator, closingToken);
        iterator++; // Token added so iterator needs to increase

        expr1(start);
    }

    private void lexpr() { // <lexpr> ::= <pexpr> |  \l<var><lexpr>
        if(tokenList.get(iterator).isLambda()) {
            Token openingToken = new Token("(");
            tokenList.add(iterator, openingToken); // Add parentheses for ambiguity
            if(next() && next() && tokenList.get(iterator).isVar()) { // Jump to variable
                if(!next()) { // If no expression --> error
                    System.err.println("Missing expression after lambda");
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
                System.err.println("Missing variable after lambda");
            }
        }   
        else {
            pexpr();
        }
    }

    private void pexpr() { // <pexpr> ::= <var> | (<expr>)
        if(tokenList.size() == iterator) {
            System.err.println("Not a complete expression");
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
                System.err.println("Missing closing parenteses");
            }
        }

        else if(tokenList.get(iterator).isVar()) { // If token is not opening parenthesis --> must be a var
            iterator++;
        }
    
        else if(tokenList.get(iterator).isParClose()){ // If closing parenthesis --> error
            System.err.println("Missing expression after opening parenthesis");
            error = true;
        }
    }
    
    private void expr() { // <expr> ::= <lexpr><expr1>
        int itAtStart = iterator;
        lexpr();
        if(error) {
            return;
        } // Don't continue after an error
        expr1(itAtStart);
    }
}