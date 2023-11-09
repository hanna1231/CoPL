import java.util.ArrayList;

public class Parser {

    private BinaryTree tree = new BinaryTree(); // Create a new abstract syntax tree

    private ArrayList<Token> tokenList = new ArrayList<Token>(); // Vector of tokens   

    private int iterator; // Iterator which iterates over the expression

    private int openPars; // Check how many open parenthesis there are

    private boolean error; // To show whether there's an error in the recursion

    private boolean next() {
        if(iterator+1 < tokenList.size()) {
            iterator++;
            System.out.println("Next is true");
            return true;
        }
            System.out.println("Next is false");
        return false;
    }

    public Parser() {
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
            System.out.print(tokenList.get(i).getValue());
            if(tokenList.get(i).isVar() && !(i+1 == tokenList.size() || tokenList.get(i+1).isParClose())) {
                System.out.print(" ");
            } // Only print a whitespace whenever there's no closing parentheses following or it isn't the end of line
        }
        System.out.print("\n");
    }

    public boolean parse() {
        printList();
        if(tokenList.isEmpty()) { // Nothing in expression
            System.out.println("Expression is empty");
            return false;
        }
        iterator = 0;
        openPars = 0;
        expr();
        if(iterator < tokenList.size()) { 
            System.out.println("iterator: " + tokenList.get(iterator).getValue());
            System.out.println("(Expression isn't valid)");
            return false;
        } // When the expression isn't finished but the parser is
        if(!error && openPars == 0) {
            printList();
            System.out.print("Output tree: ");
            tree.printTree(tree.getRoot());
            return true;
        }
        return false;
    }

    private void expr1() { // <expr1> ::= <lexpr><expr1> | e
        System.out.println("expr1");
        System.out.println("iterator: " + iterator + ", tokenList size: " + tokenList.size());
        if(iterator == tokenList.size() || tokenList.get(iterator).isParClose()){
            System.out.println("Nested expression finished");
            return;
        }
        System.out.println("Continuing in expr1");
        lexpr();
        if(error) {
            return;
        }
        expr1();
    }

    private BinaryTree lexpr() { // <lexpr> ::= <pexpr> |  \l<var><lexpr>
        BinaryTree lexprTree = new BinaryTree();
        System.out.println("lexpr");
        if(tokenList.get(iterator).isLambda()) {
            Token openingToken = new Token("(");
            tokenList.add(iterator, openingToken); // Add parentheses for ambiguity

            // Add lamda to tree
            next(); // Move iterator to lambda
            Node lambdaNode = new Node(tokenList.get(iterator));
            if(!lexprTree.addNode(lambdaNode)) {
                System.out.println("Node can't be added to tree");
                error = true;
                lexprTree.clearTree();
                return lexprTree;
            }
            System.out.print("Output tree: ");
            tree.printTree(tree.getRoot());
            System.out.print("\n");

            if(next() && tokenList.get(iterator).isVar()) { // Jump to variable
                System.out.println("var (" + tokenList.get(iterator).getValue() + ")");

                // Add var to tree
                Node varNode = new Node(tokenList.get(iterator));
                if(!lexprTree.addNode(varNode)) {
                    System.out.println("Node can't be added to tree");
                    error = true;
                    lexprTree.clearTree();
                    return lexprTree;
                }

                if(!next()) { // If no expression --> error
                    System.out.println("Missing expression after lambda");
                    error = true;
                    lexprTree.clearTree();
                    return lexprTree;
                }
                BinaryTree rightChild = lexpr();
                if(error) {
                    lexprTree.clearTree();
                    return lexprTree;
                }
                lexprTree.mergeTree(rightChild); // Add rightChild to the tree
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
        return lexprTree;
    }

    private BinaryTree pexpr() { // <pexpr> ::= <var> | (<expr>)
        BinaryTree pexprTree = new BinaryTree();
        System.out.println("pexpr");

        if(tokenList.size() == iterator) {
            System.out.println("Not a complete expression");
            error = true;
            pexprTree.clearTree();
            return pexprTree;
        } // If expression stop here it is not a complete one

        if(tokenList.get(iterator).isParOpen()) { // <pexpr> --> (<expr>)
            openPars++;
            if(!next()) {
                pexprTree.clearTree();
                return pexprTree;
            }
            BinaryTree child = expr();
            if(error) {
                pexprTree.clearTree();
                return pexprTree;
            } // There's an error in the code we do n't continue
            if(iterator != tokenList.size() && tokenList.get(iterator).isParClose()) { // Check for closing parenthesis
                openPars--;
                iterator++;
                pexprTree.mergeTree(child);               
            }
            else { // Error if there is no closing parenthesis
                error = true;
                System.out.println("Missing closing parenteses");
                pexprTree.clearTree();
            }
        }

        else if(tokenList.get(iterator).isVar()) { // If token is not opening parenthesis --> must be a var
            System.out.println("var (" + tokenList.get(iterator).getValue() + ")");
            Node varNode = new Node(tokenList.get(iterator));
            if(!pexprTree.addNode(varNode)) {
                System.out.println("Node can't be added to tree");
                error = true;
                pexprTree.clearTree();
                return pexprTree;
            }
            iterator++;
        }
    
        else if(tokenList.get(iterator).isParClose()){ // If closing parenthesis --> error
            System.out.println("(missing expression after opening parenthesis)");
            error = true;
            pexprTree.clearTree();

        }
        return pexprTree;
    }
    
    private BinaryTree expr() { // <expr> ::= <lexpr><expr1>
        BinaryTree exprTree = new BinaryTree();
        System.out.println("expr");
        
        BinaryTree leftChild = lexpr();
        exprTree.mergeTree(leftChild);
        if(error) {
            return;
        } // Don't continue after an error
        expr1();
    }
}