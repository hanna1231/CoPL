import java.util.ArrayList;

public class ParserOp3 {

    private BinaryTree tree = new BinaryTree(); // Create a new abstract syntax tree

    private ArrayList<Token> tokenList = new ArrayList<Token>(); // Vector of tokens   

    private int iterator; // Iterator which iterates over the expression

    private int openPars; // Check how many open parenthesis there are

    private boolean error; // To show whether there's an error in the recursion

    // Constructor of class
    public ParserOp3() {
        iterator = 0;
        openPars = 0;
        error = false;
    }

    // Returns true when (nested) expression is finished, otherwise false
    public boolean isFinished() {
        if(iterator >= tokenList.size() || tokenList.get(iterator).isParClose() || tokenList.get(iterator).isColon()){
            return true;
        }
        return false;
    }

    // Moves iterator to next position if possible, returns true if possible otherwise false
    private boolean next() {
        if(iterator+1 < tokenList.size()) {
            iterator++;
            return true;
        }
        return false;
    }

    // Sets error to true and prints the given error message
    public BinaryTree errorMessage(String message) {
        error = true;
        System.err.println(message);
        return null;
    }

    // Adds new token to the ArrayList called tokenList
    public void addToken(Token nieuwToken) {
        tokenList.add(nieuwToken);
    }

    // Prints the ArrayList called tokenList
    public void printList() {
        for (int i = 0; i < tokenList.size(); i++) {
            System.out.print(tokenList.get(i).getValue());
            if((tokenList.get(i).isLVar() || tokenList.get(i).isUVar())  && !(i+1 == tokenList.size() || tokenList.get(i+1).isParClose())) {
                System.out.print(" ");
            } // Only print a whitespace whenever there's no closing parentheses following or it isn't the end of line
        }
        System.out.print("\n");
    }

    // Parses the ArrayList called tokenList, then creates a type tree of left side of tree
    // and finally checks if the given type tree equals the made type tree
    public int parse() {
        if(tokenList.isEmpty()) {
            System.err.print("Expression is empty");
            return 1;
        }
        iterator = 0;
        openPars = 0;
        tree = judgement();

        if(error || iterator < tokenList.size()) {
            System.err.println("Expression isn't valid");
            return 1;
        } //Error or expression isn't finished
        else if(openPars > 0) {
            System.err.println("Missing closing parantheses");
            return 1;
        }
        else if(openPars < 0) {
            System.err.println("Missing opening parantheses");
            return 1;
        }
        else {
            ArrayList<String> emptyList = new ArrayList<String>(); // Necessary for function call to findFreeVar
            if(!tree.findFreeVar(tree.getRoot(), emptyList).isEmpty()) {
                ArrayList<Node> caretTokens = new ArrayList<Node>();
                tree.makeTypeTree(tree.getRoot().leftChild, null, caretTokens); // Make type tree from left side of colon
                if(tree.checkEquality(tree.getTypeTreeRoot(), tree.getRoot().rightChild)) {
                    tree.printTree(tree.getRoot());
                    System.out.print("\n");
                    return 0;
                } // Check if made type tree equals given type tree
                System.err.println("Type tree is incorrect");
                return 1;
            } // Check if there are no free variables in tree
            System.err.println("Free variables in tree");
            return 1;
        }
    }

    // Type is either a uvar or parantheses but never empty. If empty it returns null and otherwise
    // the new type tree. Iterator is placed at position after type when function is finished
    public BinaryTree type() {
        BinaryTree typeTree = new BinaryTree();
        if(!isFinished() && tokenList.get(iterator).isUVar()) {
            Node typeVarNode = new Node(tokenList.get(iterator));
            typeTree.addNode(typeVarNode);
            iterator++; // Iterator placed at position after type
            if(!isFinished() && tokenList.get(iterator).isArrow()) {
                typeTree.addArrow();
                if(next()) {
                    typeTree.mergeTree(type());
                }
                else {
                    return errorMessage("Missing type after arrow");
                }
            }
            return typeTree;
        } // Type is uvar

        else if(!isFinished() && tokenList.get(iterator).isParOpen() && next()) {
            openPars++;
            typeTree = type();
            if(error) {
                typeTree.clearTree();
                return typeTree;
            }
            if(iterator != tokenList.size() && tokenList.get(iterator).isParClose()) {
                openPars--;
            }
            else {
                return errorMessage("Missing closing parenthesis");
            }

            iterator++; // Iterator placed at position after type
            if(!isFinished() && tokenList.get(iterator).isArrow()) {
                typeTree.addArrow();
                if(next()) {
                    typeTree.mergeTree(type());
                }
                else {
                    return errorMessage("Missing type after arrow");
                }
            }
            return typeTree;
        } // Type is "("<type>")"
        return errorMessage("Type is empty"); // Type can't be empty
    }

    // Creates a caret Node and returns it
    private Node addCaret() {
        Token caretToken = new Token("^");
        return (new Node(caretToken));
    }

    // Expr is either an lvar, parantheses or lambda and can't be empty. There might be application and
    // then it calls expr again. Returns the newly made tree or null. 
    public BinaryTree expr(Node leftChild) {
        BinaryTree exprTree = new BinaryTree();
        exprTree.setRoot(leftChild);
        BinaryTree newExprTree = new BinaryTree();  // Both necessary
        BinaryTree child = new BinaryTree();        // to store trees

        if(tokenList.get(iterator).isLVar()) {
            Node lvarNode = new Node(tokenList.get(iterator));
            newExprTree.addNode(lvarNode);
            if(!next()) {
                error = true;
                return null;
            } // Input never ends with an expression
        } // Expr is a lvar
      
        else if(tokenList.get(iterator).isParOpen() && next()) {
            openPars++;
            newExprTree = expr(child.getRoot());
            if(error) {
                exprTree.clearTree();
                return exprTree;
            }
            if(iterator != tokenList.size() && tokenList.get(iterator).isParClose()) {
                openPars--;
                if(!next()) {
                    error = true;
                    return null;
                } // Input never ends with an expression
            }
            else {
                return errorMessage("Missing closing parenthesis");
            }
        } // Expr is parantheses

        else if(tokenList.get(iterator).isLambda()) {
            Node lambdaNode = new Node(tokenList.get(iterator));
            newExprTree.addNode(lambdaNode);
            if(next() && tokenList.get(iterator).isLVar()) {
                // First add the caret
                newExprTree.addNode(addCaret());
                // Add lvar
                Node lvarNode = new Node(tokenList.get(iterator));
                newExprTree.addNode(lvarNode);
                if(next() && tokenList.get(iterator).isCaret()) {
                    if(next()) { // Move to type after caret
                        newExprTree.mergeTree(type()); // Add type to tree
                        if(error) {
                            exprTree.clearTree();
                            return exprTree;
                        }
                        if(!isFinished()) {
                            newExprTree.mergeTree(expr(child.getRoot())); // Add expr to tree
                            if(error) {
                                exprTree.clearTree();
                                return exprTree;
                            }
                        }
                        else {
                            return errorMessage("Missing expression");
                        }
                    }
                    else {
                        return errorMessage("Missing type");
                    }
                }
                else {
                    return errorMessage("Missing caret");
                }
            }
            else {
                return errorMessage("Missing variable");
            }
        } // Expr is lambda

        else {
            return errorMessage("Missing expression");
        } // Expr is empty

        // Add an application node and put newly made tree underneath in case there is application
        // application node will later be removed if not necessary
        exprTree.addApplication();
        exprTree.addNodeApp(newExprTree.getRoot());

        if(!isFinished()) {
            return expr(exprTree.getRoot());
        }
        return exprTree;
    }

    // Creates a colon Node and returns it
    private Node addColon() {
        Token colonToken = new Token(":");
        return (new Node(colonToken));
    }

    // Calls expr to create left side of tree, then checks for a colon
    // and then calls type for right side of tree
    public BinaryTree judgement() {
        BinaryTree judgeTree = new BinaryTree();
        judgeTree.addNode(addColon());
        BinaryTree emptyTree = new BinaryTree();
        BinaryTree leftChild = expr(emptyTree.getRoot());
        if(error) {
            judgeTree.clearTree();
            return judgeTree;
        }
        leftChild.deleteApp(); // Move nodes in tree so that application is left associate
        judgeTree.mergeTree(leftChild);

        // Continuing with right side of colon
        if(tokenList.get(iterator).isColon() && next()) {
            BinaryTree rightChild = type();
            judgeTree.mergeTree(rightChild);
        }
        else {
            return errorMessage("Missing colon");
        }
        return judgeTree;
    }
}