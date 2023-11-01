public class BinaryTree {
    private Node root;

    public BinaryTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    // Returns if there is node which still has availability for 
    // and stores that in gapNode
    private boolean findGap(Node node, Node gapNode) {
        if(node == null) {
            return false;
        }

        if(findGap(node.leftChild, gapNode)) {
            return true;
        }

        if((node.token.isLambda() || node.token.isApply()) && (node.leftChild == null || node.rightChild == null)) {
            gapNode = node;
            System.out.println("changed");
            return true;
        }

        if(findGap(node.rightChild, gapNode)) {
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

        Node gapNode = null;
        if(findGap(root, gapNode)) {
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

