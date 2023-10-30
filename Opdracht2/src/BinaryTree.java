public class BinaryTree {
    private Node root;

    public BinaryTree() {
        // root = null;
    }

    public Node getRoot() {
        return root;
    }
    
    public boolean findGap(Node newNode) {
        System.out.println("hoi");
        return findGap(root, newNode);
    }


    // Returns a node which still has availability for children
    private boolean findGap(Node node, Node newNode) {
        System.out.println("hoi");
        if(node == null) {
            addNode(newNode, node);
            return true;
        }

        if(findGap(node.leftChild, newNode)) {
            return true;
        }

        if((node.token.isLambda() || node.token.isApply()) && (node.leftChild == null || node.rightChild == null)) {
            addNode(newNode, node);
            return true;
        }

        return (findGap(node.rightChild, newNode));
    }

    // Adds a node (newNode) to the left or right child of a parent node (gapNode)
    public boolean addNode(Node newNode, Node gapNode) {
        if(gapNode == null) {
            root = newNode;
            return true;
        }
        else {
            if(gapNode.leftChild == null) {
                gapNode.leftChild = newNode;
                return true;
            }
            else if(gapNode.rightChild == null) {
                gapNode.rightChild = newNode;
                return true;
            }
            else {
                return false;
            }
        }
    }

    // Prints the tree with parameter node as root
    public void printTree(Node node) {
        if(node != null) {
            if(node.getTokenValue() == "@") {
                System.out.print("(");
            }

            else {
                System.out.print(node.getTokenValue());
            }

            printTree(node.leftChild);

            if(node.getTokenValue() == "@") {
                System.out.print(" ");
            }

            else if(node.getTokenValue() == "\\") {
                System.out.print("(");
            }

            printTree(node.rightChild);

            if(node.getTokenValue() == "@" || node.getTokenValue() == "\\") {
                System.out.print(")");
            }
        }
    }
}

