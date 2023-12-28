import java.util.ArrayList;

public class ParserOp3 {

    private BinaryTree tree = new BinaryTree(); // Create a new abstract syntax tree

    private ArrayList<Token> tokenList = new ArrayList<Token>(); // Vector of tokens   

    private int iterator; // Iterator which iterates over the expression

    private int openPars; // Check how many open parenthesis there are

    private boolean error; // To show whether there's an error in the recursion

   
    //    Returns true when (nested) expression is finished, otherwise false
    public boolean isFinished() {
        if(iterator >= tokenList.size() || tokenList.get(iterator).isParClose() || tokenList.get(iterator).isColon()){
            return true;
        }
        return false;
    }
    private boolean next() {
        if(iterator+1 < tokenList.size()) {
            iterator++;
            return true;
        }
        return false;
    }

    public BinaryTree errorMessage(String message) {
        error = true;
        System.err.println(message);
        return null;
    }

    public ParserOp3() {
        iterator = 0;
        openPars = 0;
        error = false;
    }

    public void addToken(Token nieuwToken) {
        tokenList.add(nieuwToken);
    }

    public void printList() {
        for (int i = 0; i < tokenList.size(); i++) {
            System.out.print(tokenList.get(i).getValue());
            if((tokenList.get(i).isLVar() || tokenList.get(i).isUVar())  && !(i+1 == tokenList.size() || tokenList.get(i+1).isParClose())) {
                System.out.print(" ");
            } // Only print a whitespace whenever there's no closing parentheses following or it isn't the end of line
        }
        System.out.print("\n");
    }

    public int parse() {
        if(tokenList.isEmpty()) {
            System.err.print("Expression is empty");
            return 1;
        }
        iterator = 0;
        openPars = 0;
        tree = judgement();
        System.out.println("tokenlist size " + tokenList.size());
        System.out.println("iterator " + iterator);
        if(error || iterator < tokenList.size()-1) {
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
            ArrayList<String> emptyList = new ArrayList<String>();
            if(!tree.findFreeVar(tree.getRoot(), emptyList).isEmpty()) {
                ArrayList<Node> caretTokens = new ArrayList<Node>();
                tree.makeTypeTree(tree.getRoot().leftChild, null, caretTokens);
                if(tree.checkEquality(tree.getTypeTreeRoot(), tree.getRoot().rightChild)) {
                    tree.printTree(tree.getRoot());
                    System.out.print("\n");
                    return 0;
                }
                System.err.println("Type tree is incorrect");
                return 1;
            }
            System.err.println("Free variables in tree");
            return 1;
        }
    }

    public BinaryTree type() {
        BinaryTree typeTree = new BinaryTree();
        if(tokenList.get(iterator).isUVar()) {
            Node typeVarNode = new Node(tokenList.get(iterator));
            typeTree.addNode(typeVarNode);
            if(next() && tokenList.get(iterator).isArrow()) {
                typeTree.addArrow();
                if(next()) {
                    typeTree.mergeTree(type());
                }
                else {
                    return errorMessage("Missing type after arrow");
                }
            }
        } // Type is uvar

        else if(tokenList.get(iterator).isParOpen() && next()) {
            openPars++;
            typeTree = type();
            if(error) {
                typeTree.clearTree();
                return typeTree;
            }
            if(iterator != tokenList.size() && tokenList.get(iterator).isParClose()) {
                System.out.println("hoi " + iterator);
                openPars--;
            }
            else {
                return errorMessage("Missing closing parenthesis");
            }

            if(next() && tokenList.get(iterator).isArrow()) {
                typeTree.addArrow();
                if(next()) {
                    typeTree.mergeTree(type());
                }
                else {
                    return errorMessage("Missing type after arrow");
                }
            }
            // else {
            //     iterator++;
            // }
        } // Type is "("<type>")"
        return typeTree;
    }
   
    private Node addCaret() {
        Token caretToken = new Token("^");
        return (new Node(caretToken));
    }

    public BinaryTree expr(Node leftChild) {
        BinaryTree exprTree = new BinaryTree();
        exprTree.setRoot(leftChild);
        BinaryTree newExprTree = new BinaryTree();
        BinaryTree child = new BinaryTree();
        if(tokenList.get(iterator).isLVar()) {
            Node lvarNode = new Node(tokenList.get(iterator));
            newExprTree.addNode(lvarNode);
            if(!next()) {
                error = true;
                return null;
            }
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
                next();
            }
            else {
                return errorMessage("Missing closing parenthesis");
            }
        }

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
                        newExprTree.mergeTree(type());
                        newExprTree.printTree(newExprTree.getRoot());
                        if(error) {
                            exprTree.clearTree();
                            return exprTree;
                        }
                        if(!isFinished()) {
                            newExprTree.mergeTree(expr(child.getRoot()));
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
        }

        else {
            return errorMessage("Missing expression");
        }
        exprTree.addApplication();
        exprTree.addNodeApp(newExprTree.getRoot());

        // System.out.println(iterator);
        if(!isFinished()) {
            BinaryTree newTree = expr(exprTree.getRoot());
            return newTree;
        }
        return exprTree;
   }

    private Node addColon() {
        Token colonToken = new Token(":");
        return (new Node(colonToken));
    }

    public BinaryTree judgement() {
        BinaryTree judgeTree = new BinaryTree();
        judgeTree.addNode(addColon());
        BinaryTree emptyTree = new BinaryTree();
        BinaryTree leftChild = expr(emptyTree.getRoot());
        leftChild.deleteApp();
        judgeTree.mergeTree(leftChild);
        if(error) {
            judgeTree.clearTree();
            return judgeTree;
        }

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




   // Returns true when (nested) expression is finished, otherwise false
//  public boolean isFinished() {
//      System.out.println("iterator: " + iterator + ", tokenList size: " + tokenList.size());
//      if(iterator == tokenList.size() || tokenList.get(iterator).isParClose()){
//          System.out.println("Nested expression finished");
//          return true;
//      }
      
//      return false;
//  }

//  public int parse() {
//      //printList();
//      if(tokenList.isEmpty()) { // Nothing in expression
//          System.out.println("Expression is empty");
//          return 1;
//      }
//      iterator = 0;
//      openPars = 0;
//      Node emptyNode = null;
//      //tree = expr(emptyNode);
//      if(iterator < tokenList.size()) { 
//          System.out.println("iterator: " + tokenList.get(iterator).getValue());
//          System.out.println("(Expression isn't valid)");
//          return 1;
//      } // When the expression isn't finished but the parser is
//      if(!error && openPars == 0) {
//          //printList();
//          BinaryTree oldTree = tree;
//          System.out.print("Old tree: ");
//          tree.printTree(tree.getRoot());

//          tree.deleteApp();
//          System.out.println(oldTree.equals(tree));
//          System.out.print("Output tree: ");
//          tree.printTree(tree.getRoot());
//          // ArrayList<String> boundVar = new ArrayList<String>();
//          // ArrayList<String> freeVar = tree.findFreeVar(tree.getRoot(), boundVar);
//          // System.out.println("\nFree variables");
//          // for(int i = 0; i < freeVar.size(); i++) {
//          //     System.out.println(freeVar.get(i));
//          // }  
//          tree.printTree(tree.getRoot());
//          return 2;      
//      }
//      return 1;
//  }

//     private BinaryTree expr1(Node leftChild) { // <expr1> ::= <lexpr><expr1> | e
//         System.out.println("expr1");
//         BinaryTree expr1Tree =  new BinaryTree();
//         expr1Tree.setRoot(leftChild);
//         System.out.println("iterator: " + iterator + ", tokenList size: " + tokenList.size());
//         if(isFinished()){
//             return expr1Tree;
//         }
//         System.out.println("Continuing in expr1");
//         BinaryTree lexpTree = lexpr();
   
//         if(error) {
//             return expr1Tree;
//         }

//         expr1Tree.addApplication();
//         expr1Tree.addNodeApp(lexpTree.getRoot());

//         if(!isFinished()) {
//             BinaryTree rightChild = expr1(expr1Tree.getRoot());
//             return rightChild;
//         }

//         return expr1Tree;
//     }

//     private BinaryTree lexpr() { // <lexpr> ::= <pexpr> |  \l<var><lexpr>
//         BinaryTree lexprTree = new BinaryTree();
//         System.out.println("lexpr");
//         if(tokenList.get(iterator).isLambda()) {
//             Token openingToken = new Token("(");
//             tokenList.add(iterator, openingToken); // Add parentheses for ambiguity

//             // Add lamda to tree
//             next(); // Move iterator to lambda
//             Node lambdaNode = new Node(tokenList.get(iterator));
//             if(!lexprTree.addNodeApp(lambdaNode)) {
//                 System.out.println("Node can't be added to tree");
//                 error = true;
//                 lexprTree.clearTree();
//                 return lexprTree;
//             }
//             System.out.print("Output tree: ");
//             tree.printTree(tree.getRoot());
//             System.out.print("\n");

//             if(next() && tokenList.get(iterator).isVar()) { // Jump to variable
//                 System.out.println("var (" + tokenList.get(iterator).getValue() + ")");

//                 // Add var to tree
//                 Node varNode = new Node(tokenList.get(iterator));
//                 if(!lexprTree.addNodeLeft(varNode)) {
//                     System.out.println("Node can't be added to tree");
//                     error = true;
//                     lexprTree.clearTree();
//                     return lexprTree;
//                 }

//                 if(!next()) { // If no expression --> error
//                     System.out.println("Missing expression after lambda");
//                     error = true;
//                     lexprTree.clearTree();
//                     return lexprTree;
//                 }
//                 BinaryTree rightChild = lexpr();
//                 if(error) {
//                     lexprTree.clearTree();
//                     return lexprTree;
//                 }
//                 lexprTree.addNodeApp(rightChild.getRoot()); // Add rightChild to the tree
//                 Token closingToken = new Token(")"); // Add parentheses for ambiguity
//                 tokenList.add(iterator, closingToken);
//                 iterator++;
               
//             } 
//             else {
//                 error = true;
//                 System.out.println("(missing variable after lambda)");
//             }

//         }   
//         else {
//             lexprTree = pexpr();
//             System.out.println("lexprTree");
//             lexprTree.printTree(lexprTree.getRoot());
         
//         }
//         return lexprTree;
//     }

//     private BinaryTree pexpr() { // <pexpr> ::= <var> | (<expr>)
//         BinaryTree pexprTree = new BinaryTree();
//         System.out.println("pexpr");

//         if(tokenList.size() == iterator) {
//             System.out.println("Not a complete expression");
//             error = true;
//             pexprTree.clearTree();
//             return pexprTree;
//         } // If expression stop here it is not a complete one

//         if(tokenList.get(iterator).isParOpen()) { // <pexpr> --> (<expr>)
//             openPars++;
//             if(!next()) {
//                 pexprTree.clearTree();
//                 return pexprTree;
//             }
//             BinaryTree child = expr(pexprTree.getRoot());
//             if(error) {
//                 pexprTree.clearTree();
//                 return pexprTree;
//             } // There's an error in the code we don't continue
//             if(iterator != tokenList.size() && tokenList.get(iterator).isParClose()) { // Check for closing parenthesis
//                 openPars--;
//                 iterator++;
//                 return child;         
//             }
//             else { // Error if there is no closing parenthesis
//                 error = true;
//                 System.out.println("Missing closing parenteses");
//                 pexprTree.clearTree();
//             }
//         }

//         else if(tokenList.get(iterator).isVar()) { // If token is not opening parenthesis --> must be a var
//             System.out.println("var (" + tokenList.get(iterator).getValue() + "), iterator: " + iterator);
//             Node varNode = new Node(tokenList.get(iterator));
//             System.out.println(varNode.getTokenValue());
//             // pexprTree.addApplication();
//             if(!pexprTree.addNode(varNode)) {
//                 System.out.println("Node can't be added to tree");
//                 error = true;
//                 pexprTree.clearTree();
//                 return pexprTree;
//             }
//             iterator++;
//             System.out.println("pexprTree");
//             pexprTree.printTree(pexprTree.getRoot());
//         }
   
//         else if(tokenList.get(iterator).isParClose()){ // If closing parenthesis --> error
//             System.out.println("(missing expression after opening parenthesis)");
//             error = true;
//             pexprTree.clearTree();

//         }
//         return pexprTree;
//     }
   
//     private BinaryTree expr(Node leftChild) { // <expr> ::= <lexpr><expr1>
//         BinaryTree exprTree = new BinaryTree();
//         System.out.println("expr");
//         exprTree.setRoot(leftChild);
      
//         BinaryTree lexpTree = lexpr();
//         if(error) {
//             return exprTree;
//         } // Don't continue after an error
//         exprTree.addApplication();
//         exprTree.addNodeApp(lexpTree.getRoot());

//         if(!isFinished()) {
//             BinaryTree rightChild = expr1(exprTree.getRoot());
//             System.out.println("rightChild");
//             rightChild.printTree(rightChild.getRoot());
//             return rightChild;
//         }
//         return exprTree;
//     }
}