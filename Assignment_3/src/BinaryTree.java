import java.util.ArrayList;
import java.util.List;

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
    
   //  // Returns if there is node which still has availability for 
   //  // and stores that in gapNode
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

   //  // Adds a node (newNode) to the left or right child of a parent node (gapNode)
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


    // Prints the tree with parameter node as root
    public void printTree(Node node) {
        if(node != null) {
            if(node.getTokenValue().equals("@") || node.getTokenValue().equals(":") || node.getTokenValue().equals("->") ) {
                System.out.print("(");
            }
            else if(node.getTokenValue().equals("^")) {
                //stik er in
            }
            else {
                System.out.print(node.getTokenValue());
            }

            printTree(node.leftChild);

            if(node.getTokenValue().equals("@")) {
                System.out.print(" ");
            }
            else if(node.getTokenValue().equals("\\")) {
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

            if(node.getTokenValue().equals("@") || node.getTokenValue().equals("\\") || node.getTokenValue().equals(":") || node.getTokenValue().equals("->") || node.getTokenValue().equals("^")) {
                System.out.print(")");
            }
           
            
        }
    }

   //  // When there's no reference to objects in the tree Java will delete the objects
   //  // automatically with the garbage collector
    public void clearTree() {
        root = null;
        gapNode = null;
    }

    public boolean mergeTree(BinaryTree addTree) {
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
        
        if(node.getTokenValue().equals("\\")) {
            boundVariables.add(node.leftChild.leftChild.getTokenValue());
        }

        else if(node.token.isVar() && !hasVar(node.getTokenValue(), boundVariables)) {
            varList.add(node.getTokenValue());
        }

        varList.addAll(findFreeVar(node.leftChild, boundVariables));
        varList.addAll(findFreeVar(node.rightChild, boundVariables));

        return varList;
    }

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

    // Returns the pointer Node to type of the variable var, returns null if not found
    public Node findType(ArrayList<Node> caretTokens, String var) {
        for(int i = caretTokens.size()-1; i >= 0; i--) {
            if(caretTokens.get(i).leftChild.getTokenValue().equals(var)) {
                return caretTokens.get(i).rightChild;
            }
        } // Go backwards through list to find the closest lambda
        return null;
    }

    public Node makeTypeTree(Node node, Node typeTreeNode, ArrayList<Node> caretTokens) {
        System.out.println("makeTypeTree");
        if(node == null) {
            System.out.println("node is null");
            return null;
        }
        
        if(node.token.isLambda()) {
            caretTokens.add(node.leftChild);
            Node newTypeNode = new Node(new Token("->"));
            newTypeNode.leftChild = copyTree(node.leftChild.rightChild);
            boolean treeNodeNull = false;

            if(typeTreeNode == null) {
                typeTreeNode = newTypeNode;
                typeTreeRoot = newTypeNode;
                treeNodeNull = true;
            }
            else if(typeTreeNode.leftChild == null) {
                typeTreeNode.leftChild = newTypeNode;
            }
            else if(typeTreeNode.rightChild == null) {
                typeTreeNode.rightChild = newTypeNode;
            }
            Node right = makeTypeTree(node.rightChild, newTypeNode, caretTokens);
            if(right == null) {
                System.out.println("return null");
                return null;
            }
            newTypeNode.rightChild = right;
            if(treeNodeNull) {
                typeTreeRoot = newTypeNode;
            }
            return newTypeNode;
        }
        else if(node.token.isApply()) {
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
            if(typeNodeLeft.token.isArrow() && checkEquality(typeNodeLeft.leftChild, typeNodeRight)) {
                return copyTree(typeNodeLeft.rightChild);
            }
            return null;
        }
        else if(node.token.isLVar()) {
            System.out.println("lvar");
            Node typeNode = findType(caretTokens, node.getTokenValue());
            if(typeNode == null) {
                return null;
            }
            return copyTree(typeNode);

        }
        System.out.println("return false");
        return null;
    }





}

    