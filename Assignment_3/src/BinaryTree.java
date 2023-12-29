import java.util.ArrayList;

public class BinaryTree {
    private Node root;
    private Node gapNode; // Stores the parent of a node which still has room for children
    private Node typeTreeRoot; // Stores the root of type tree that can be made from left side of tree

    public BinaryTree() {
        root = null;
        gapNode = null;
        typeTreeRoot = null;
    }
    
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node newNode) {
        root = newNode;
    }

    public Node getTypeTreeRoot() {
        return typeTreeRoot;
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

        if((node.token.isLambda() || node.token.isApply() || node.token.isCaret() || node.token.isArrow() || node.token.isColon())
          && (node.leftChild == null || node.rightChild == null)) {
            gapNode = node;
            return true;
        }

        if(findGap(node.rightChild)) {
            return true;
        }

        return false;
    }

    // Adds newNode on the right side of an application node unless
    // root is null and newNode becomes root
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

    // Adds a node (newNode) to the left or right child of a parent node (gapNode)
    public boolean addNode(Node newNode) {
        if(root == null) {
            root = newNode;
            return true;
        }
        
        gapNode = null;
        if(findGap(root)) {
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

    // Adds an arrow node to tree as root and old root becomes leftChild of arrow Node
    public boolean addArrow() {
        Token arrowToken = new Token("->");
        Node arrowNode = new Node(arrowToken);

        // Arrow becomes the root of the tree
        if(this.root != null) {
            arrowNode.leftChild = this.root;
        }
        this.root = arrowNode;
        return true;
    }

    // Adds an application node to tree as root and old root becomes leftChild of arrow Node
    public boolean addApplication() {
        Token apToken = new Token("@");
        Node apNode = new Node(apToken);

        // Application becomes the root of the tree
        if(this.root != null) {
            apNode.leftChild = this.root;
        }
        this.root = apNode;
        return true;
    }

    // Prints the tree with parameter node as root
    public void printTree(Node node) {
        if(node != null) {
            if(node.getTokenValue().equals("@") || node.getTokenValue().equals(":") || node.getTokenValue().equals("->") ) {
                System.out.print("(");
            }
            else if(!node.getTokenValue().equals("^")) {
                System.out.print(node.getTokenValue());
            }

            printTree(node.leftChild);

            if(node.getTokenValue().equals("@")) {
                System.out.print(" ");
            }
            else if(node.token.isLambda()) {
                System.out.print("(");
            }
            else if(node.getTokenValue().equals(":")){
                System.out.print(":");
            }
            else if(node.getTokenValue().equals("->")) {
                System.out.print("->");
            }
            else if(node.getTokenValue().equals("^")) {
                System.out.print("^ (");
            }

            printTree(node.rightChild);

            if(node.getTokenValue().equals("@") || node.token.isLambda() || node.getTokenValue().equals(":") || node.getTokenValue().equals("->") || node.getTokenValue().equals("^")) {
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

    // If this BinaryTree and addTree aren't empty then addTree is placed in this BinaryTree
    // at the first available option in a WLR walk
    public boolean mergeTree(BinaryTree addTree) {
        if(addTree == null) {
            return false;
        }
        if(this.root == null) {
            this.root = addTree.getRoot();
        }
        else if(!findGap(this.root)) {
            return false;
        }
        else if(gapNode.leftChild == null) {
            gapNode.leftChild = addTree.getRoot();
        }
        else {
            gapNode.rightChild = addTree.getRoot();
        }

        return true;
    }

    // Sets right settings and calls the private function deleteApp
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
        }
    }

    // Returns true if var occurs in ArrayList varList, otherwise false
    private Boolean hasVar(String var, ArrayList<String> varList) {
        for(int i = 0; i < varList.size(); i++) {
            if(varList.get(i).equals(var)) {
                return true;
            }
        }
        return false;
    }

    // Returns all free variables from the (sub)tree node
    public ArrayList<String> findFreeVar(Node node, ArrayList<String> boundVariables) {
        ArrayList<String> varList = new ArrayList<String>();
        if(node == null) {
            return varList;
        }
        
        if(node.token.isLambda()) {
            boundVariables.add(node.leftChild.leftChild.getTokenValue()); // Add a new boundvariable
        }

        else if(node.token.isVar() && !hasVar(node.getTokenValue(), boundVariables)) {
            varList.add(node.getTokenValue());
        } // Check if token value is already in boundVariables

        varList.addAll(findFreeVar(node.leftChild, boundVariables));
        varList.addAll(findFreeVar(node.rightChild, boundVariables));

        return varList;
    }

    // Recursively checks if root of (sub) trees lambdaType and arrowType equal
    public boolean checkEquality(Node lambdaType, Node arrowType) {
        if(lambdaType == null && arrowType == null) {
            return true;
        }
        else if(lambdaType == null || arrowType == null) {
            return false;
        }
        else {
            return((lambdaType.getTokenValue().equals(arrowType.getTokenValue())) && checkEquality(lambdaType.leftChild, arrowType.leftChild) && checkEquality(lambdaType.rightChild, arrowType.rightChild));
        }
    }

    // Makes a hard copy of the node including its children
    public Node copyTree(Node node) {
        if(node == null) {
            return null;
        }
        Node newNode = new Node(new Token(node.getTokenValue()));
        newNode.leftChild = copyTree(node.leftChild);
        newNode.rightChild = copyTree(node.rightChild);
        return newNode;
    }

    // Returns the pointer Node to the type of the variable var, returns null if not found
    public Node findType(ArrayList<Node> caretTokens, String var) {
        for(int i = caretTokens.size()-1; i >= 0; i--) {
            if(caretTokens.get(i).leftChild.getTokenValue().equals(var)) {
                return caretTokens.get(i).rightChild;
            }
        } // Go backwards through list to find the closest lambda
        return null;
    }

    // Makes a new type tree accessed trough typeTreeNode of Node node, caretTokens contains in order
    // every bound variable from node to root. It returns null if node was an invalid tree and otherwise the type tree
    public Node makeTypeTree(Node node, Node typeTreeNode, ArrayList<Node> caretTokens) {
        if(node == null) {
            return null;
        }
        
        if(node.token.isLambda()) {
            caretTokens.add(node.leftChild); // Add bound variable of lambda to caretTokens
            Node newTypeNode = new Node(new Token("->"));
            newTypeNode.leftChild = copyTree(node.leftChild.rightChild); // Add type of lambda to type tree
            boolean treeNodeNull = false;

            // Add typeTreeNode to type tree
            if(typeTreeNode == null) {
                typeTreeNode = newTypeNode;
                typeTreeRoot = newTypeNode;
                treeNodeNull = true; // Remember that typeTreeNode was null because it might be root
            }
            else if(typeTreeNode.leftChild == null) {
                typeTreeNode.leftChild = newTypeNode;
            }
            else if(typeTreeNode.rightChild == null) {
                typeTreeNode.rightChild = newTypeNode;
            }
            Node right = makeTypeTree(node.rightChild, newTypeNode, caretTokens); // Continue in right child of lambda
            if(right == null) {
                return null;
            }
            newTypeNode.rightChild = right; // Add type tree of right child of lambda to the current type tree
            if(treeNodeNull) {
                typeTreeRoot = newTypeNode;
            }
            return newTypeNode;
        }

        else if(node.token.isApply()) {
            // First the type tree of both children needs to be created
            Node typeNodeLeft = null;
            Node typeNodeRight = null;
            if(node.leftChild.token.isApply() || node.leftChild.token.isLambda()) {
                typeNodeLeft = makeTypeTree(node.leftChild, null, caretTokens);
            }
            else {
                typeNodeLeft = findType(caretTokens, node.leftChild.getTokenValue());
            }

            if(node.rightChild.token.isApply()) {
                typeNodeRight = makeTypeTree(node.rightChild, null, caretTokens);
            }
            else {
                typeNodeRight = findType(caretTokens, node.rightChild.getTokenValue());
            }

            if(typeNodeLeft == null || typeNodeRight == null) {
                return null;
            }
            // Check if application is allowed and then make correct type tree
            if(typeNodeLeft.token.isArrow() && checkEquality(typeNodeLeft.leftChild, typeNodeRight)) {
                return copyTree(typeNodeLeft.rightChild);
            }
            return null;
        }
        else if(node.token.isLVar()) {
            // Type of lvar will be returned
            Node typeNode = findType(caretTokens, node.getTokenValue());
            if(typeNode == null) {
                return null;
            }
            return copyTree(typeNode);
        }
        return null;
    }
}

    