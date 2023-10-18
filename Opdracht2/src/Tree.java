public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    // Returns a node which still has availability for children
    public boolean findGap(Node node, Node gapNode) {
        if(node == null) {
            gapNode = node;
            return true;
        }

        if(findGap(node.leftChild, gapNode)) {
            return true;
        }

        if((node.token.isLambda() || node.token.isApply()) && (node.leftChild == null || node.rightChild == null)) {
            gapNode = node;
            return true;
        }

        return (findGap(node.rightChild, gapNode));
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

