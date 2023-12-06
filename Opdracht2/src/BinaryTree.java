import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private Node root;
    private Node gapNode; // Stores the parent of a node which still has room for children
    private int iterator;
    private int sizeVar;
    private int reductionCounter;
    private int changeVarCounter;

    public BinaryTree() {
        root = null;
        gapNode = null;
        iterator = 0;
        sizeVar = 3;
        reductionCounter = 0;
        changeVarCounter = 0;
    }
    
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node newNode) {
        root = newNode;
    }

    public Node getGapNode() {
        return gapNode;
    }
    
    // Returns if there is node which still has availability for 
    // and stores that in gapNode
    public boolean findGap(Node node) {
        if(node == null) {
            return false;
        }

        if(findGap(node.leftChild)) {
            return true;
        }

        if((node.token.isLambda() || node.token.isApply()) && (node.leftChild == null || node.rightChild == null)) {
            gapNode = node;
            System.out.println(gapNode == null);
            System.out.println("changed");
            return true;
        }

        if(findGap(node.rightChild)) {
            return true;
        }

        return false;
    }

    public boolean addNodeApp(Node newNode) {
        if(root == null) {
            root = newNode;
            return true;
        }
        else if(root.rightChild == null) {
            root.rightChild = newNode;
            return true;
        }
        return false;
    }

    public boolean addNodeLeft(Node newNode) {
        if(root == null) {
            root = newNode;
            return true;
        }
        else if(root.leftChild == null) {
            root.leftChild = newNode;
            return true;
        }
        return false;
    }

    // Adds a node (newNode) to the left or right child of a parent node (gapNode)
    public boolean addNode(Node newNode) {
        if(root == null) {
            root = newNode;
            return true;
        }
        
        gapNode = null;
        if(findGap(root)) {
            System.out.println(gapNode == null);
            if(gapNode.leftChild == null) {
                gapNode.leftChild = newNode;
            }
            else {
                gapNode.rightChild = newNode;
            }
            return true;
        }
        return false;
    }

    public boolean addApplication() {
        Token apToken = new Token("@");
        Node apNode = new Node(apToken);
        System.out.println("addApplication");

        // Application becomes the root of the tree
        if(this.root != null) {
            apNode.leftChild = this.root;
        }
        this.root = apNode;
        return true;
    }

    public void printTree(Node node) {
        printTreeRed(node);
        System.out.print("\n");
    }

    // Prints the tree with parameter node as root
    private void printTreeRed(Node node) {
        if(node != null) {
            if(node.getTokenValue().equals("@")) {
                System.out.print("(");
            }

            else {
                System.out.print(node.getTokenValue());
            }

            printTreeRed(node.leftChild);

            if(node.getTokenValue().equals("@")) {
                System.out.print(" ");
            }

            else if(node.getTokenValue().equals("\\")) {
                System.out.print("(");
            }

            printTreeRed(node.rightChild);

            if(node.getTokenValue().equals("@") || node.getTokenValue().equals("\\")) {
                System.out.print(")");
            }
        }
    }

    // When there's no reference to objects in the tree Java will delete the objects
    // automatically with the garbage collector
    public void clearTree() {
        root = null;
        gapNode = null;
    }

    public boolean mergeTree(BinaryTree addTree) {
        System.out.println("HET IS HIER HANNA!");
        addTree.printTree(addTree.getRoot());
        if(this.root == null) {
            System.out.println("ROOT");
            this.root = addTree.getRoot();
        }
        else if(!findGap(this.root)) {
            System.out.println("FOUT");
            return false;
        }
        else if(gapNode.leftChild == null) {
            System.out.println("LINKS");
            gapNode.leftChild = addTree.getRoot();
        }
        else {
            System.out.println("RECHTS");
            gapNode.rightChild = addTree.getRoot();
        }

        return true;
    }

    public void deleteApp() {
        Node node = this.root;
        Node parent = node;

        deleteApp(node, parent);
    }

    // Recursive decent through the tree and deleting all empty leaves
    private void deleteApp(Node node, Node parent) {
        if(node == null) {
            return;
        }

        deleteApp(node.leftChild, node);
        deleteApp(node.rightChild, node);

        System.out.println("deleteApp" + node.getTokenValue());
        if(node.leftChild == null && node.rightChild != null) {
            if(node == this.root) {
                this.root = node.rightChild;
            }
            else if(parent.leftChild == node) {
                parent.leftChild = node.rightChild;
            }
            else if(parent.rightChild == node) {
                parent.rightChild = node.rightChild;
            }
            return;
        }
    }

    // New variables will be produced. Variables are of the form x*[0-9] where
    // the number at the end is increased until 9 and then an extra x is added in front
    public String newVar() {
        String newString = "";
        for(int i = 0; i < sizeVar; i++) {
            newString += "x";
        }
        newString += iterator;

        if(iterator == 9) {
            iterator = 0;
            sizeVar++;
        }
        else {
            iterator++;
        }

        return newString;  
    }

    // Recursive function that sets the lambda variable in M to N
    public void changeVarReduction(Node node, String changeVar, Node N) {
        if(node == null) {
            return;
        }

        if(node.getTokenValue().equals(changeVar)) {
            System.out.println("old N");
            printTree(N);
            node.setNode(N);
            System.out.println("new N");
            // printTree(node);
            printTree(N);
            changeVarCounter++;
            return;
        }

        changeVarReduction(node.leftChild, changeVar, N);
        // printTree(node);
        changeVarReduction(node.rightChild, changeVar, N);
        // printTree(node);
    }

    // Performs reduction, node is the application node with left child lambda
    public void reduction(Node node) {  
        // System.out.println("reduction\nTree before reduction");
        // printTree(node);
        String changeVar = node.leftChild.leftChild.getTokenValue();
        Node N = node.rightChild;
        // System.out.println("N");
        // printTree(N);
        if(node == root) {
            root = node.leftChild.rightChild;
        }
        node.setNode(node.leftChild.rightChild);
        // System.out.println("tree");
        printTree(node);
        changeVarReduction(node, changeVar, N);
        // System.out.println("tree after reduction: " + changeVarCounter);
        // printTree(node);
    }

    // Calls findAppLamdaPriv until a 1000 reductions are reached (then returning false)
    // or when there can't be reduced any further (then returning true)
    public boolean findAppLambda(Node node) {
        boolean change = true;
        while(reductionCounter < 500 && change) {
            // printTree(node);
            change = findAppLambdaPriv(node);
            System.out.println(reductionCounter);
            // printTree(node);
        }
        if(reductionCounter == 500) {
            return false;
        }
        return true;
    }


    //function that looks for application node with leftchild lambda, returns true when found
    private boolean findAppLambdaPriv (Node node) {
        if(node == null || node.leftChild == null) {
            return false;
        }
        boolean change = false;
        if(findAppLambdaPriv(node.leftChild)) {
            change = true;
        }
        if(findAppLambdaPriv(node.rightChild)) {
            change = true;
        }

        if(node.getTokenValue().equals("@") && node.leftChild.getTokenValue().equals("\\") ) {
            System.out.println("hoi");
            checkConversion(node);
            reduction(node);
            reductionCounter++;
            // if(node == root) {
            //     printTree(node);
            // }
            return true;
        }
            // if(node == root) {
            //     printTree(node);
            // }
        return change;
    }

    // Returns false whenever
    public boolean checkConversion(Node node) {
        ArrayList<String> freeVarList = new ArrayList<String>();
        if(node == null || node.leftChild == null) {
            return false;
        }

        ArrayList<String> boundVariables = new ArrayList<String>();
        freeVarList = findFreeVar(node.rightChild, boundVariables);
        System.out.println("\nFree variables of N");
        for(int i = 0; i < freeVarList.size(); i++) {
            System.out.println(freeVarList.get(i));
        }
        
        for(int i = 0; i < freeVarList.size(); i++) {
            if(isBound(node.leftChild, freeVarList.get(i))) {
                doConversion(node.leftChild, freeVarList.get(i), newVar());
                System.out.println("free var is bound in M" + freeVarList.get(i));
            }
        }
        return true;
    }
 
    public void doConversion(Node node, String oldVar, String newVar) {
        if(node == null) {
            return;
        }

        if(node.getTokenValue().equals(oldVar)) {
            node.token.setTokenValue(newVar);
        }

        doConversion(node.leftChild, oldVar, newVar);
        doConversion(node.rightChild, oldVar, newVar);
     }

    // Returns true when the token var is bound in the (sub)tree with root "node"
    public boolean isBound(Node node, String var) {
        if(node == null) {
            return false;
        }
        if(node.getTokenValue().equals("\\")) {
            if(node.leftChild.getTokenValue().equals(var)) {
                return true;
            }
        }

        if(isBound(node.leftChild, var)) {
            return true;
        }
        
        if(isBound(node.rightChild, var)){
            return true;   
        }
        return false;
    }

    private Boolean hasVar(String var, ArrayList<String> varList) {
        for(int i = 0; i < varList.size(); i++) {
            if(varList.get(i).equals(var)) {
                return true;
            }
        }
        return false;
    }

    // Returns all free variables from the (sub)tree node
    private ArrayList<String> findFreeVar(Node node, ArrayList<String> boundVariables) {
        ArrayList<String> varList = new ArrayList<String>();
        if(node == null) {
            return varList;
        }
        
        if(node.getTokenValue().equals("\\")) {
            boundVariables.add(node.leftChild.getTokenValue());
        }

        else if(node.token.isVar() && !hasVar(node.getTokenValue(), boundVariables)) {
            varList.add(node.getTokenValue());
        }

        varList.addAll(findFreeVar(node.leftChild, boundVariables));
        varList.addAll(findFreeVar(node.rightChild, boundVariables));

        return varList;
    }

}

