import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
   //  private Node root;
   //  private Node gapNode; // Stores the parent of a node which still has room for children
   //  private int iterator;
   //  private int sizeVar;
   //  private int reductionCounter;

   //  public BinaryTree() {
   //      root = null;
   //      gapNode = null;
   //      iterator = 0;
   //      sizeVar = 3;
   //      reductionCounter = 0;
   //  }
    
   //  public Node getRoot() {
   //      return root;
   //  }

   //  public void setRoot(Node newNode) {
   //      root = newNode;
   //  }

   //  public Node getGapNode() {
   //      return gapNode;
   //  }
    
   //  // Returns if there is node which still has availability for 
   //  // and stores that in gapNode
   //  public boolean findGap(Node node) {
   //      if(node == null) {
   //          return false;
   //      }

   //      if(findGap(node.leftChild)) {
   //          return true;
   //      }

   //      if((node.token.isLambda() || node.token.isApply()) && (node.leftChild == null || node.rightChild == null)) {
   //          gapNode = node;
   //          System.out.println(gapNode == null);
   //          System.out.println("changed");
   //          return true;
   //      }

   //      if(findGap(node.rightChild)) {
   //          return true;
   //      }

   //      return false;
   //  }

   //  public boolean addNodeApp(Node newNode) {
   //      if(root == null) {
   //          root = newNode;
   //          return true;
   //      }
   //      else if(root.rightChild == null) {
   //          root.rightChild = newNode;
   //          return true;
   //      }
   //      return false;
   //  }

   //  public boolean addNodeLeft(Node newNode) {
   //      if(root == null) {
   //          root = newNode;
   //          return true;
   //      }
   //      else if(root.leftChild == null) {
   //          root.leftChild = newNode;
   //          return true;
   //      }
   //      return false;
   //  }

   //  // Adds a node (newNode) to the left or right child of a parent node (gapNode)
   //  public boolean addNode(Node newNode) {
   //      if(root == null) {
   //          root = newNode;
   //          return true;
   //      }
        
   //      gapNode = null;
   //      if(findGap(root)) {
   //          System.out.println(gapNode == null);
   //          if(gapNode.leftChild == null) {
   //              gapNode.leftChild = newNode;
   //          }
   //          else {
   //              gapNode.rightChild = newNode;
   //          }
   //          return true;
   //      }
   //      return false;
   //  }

   //  public boolean addApplication() {
   //      Token apToken = new Token("@");
   //      Node apNode = new Node(apToken);
   //      System.out.println("addApplication");

   //      // Application becomes the root of the tree
   //      if(this.root != null) {
   //          apNode.leftChild = this.root;
   //      }
   //      this.root = apNode;
   //      return true;
   //  }

   //  public void printTree(Node node) {
   //      printTreeRed(node);
   //      System.out.print("\n");
   //  }

   //  // Prints the tree with parameter node as root
   //  private void printTreeRed(Node node) {
   //      if(node != null) {
   //          if(node.getTokenValue().equals("@")) {
   //              System.out.print("(");
   //          }

   //          else {
   //              System.out.print(node.getTokenValue());
   //          }

   //          printTreeRed(node.leftChild);

   //          if(node.getTokenValue().equals("@")) {
   //              System.out.print(" ");
   //          }

   //          else if(node.getTokenValue().equals("\\")) {
   //              System.out.print("(");
   //          }

   //          printTreeRed(node.rightChild);

   //          if(node.getTokenValue().equals("@") || node.getTokenValue().equals("\\")) {
   //              System.out.print(")");
   //          }
   //      }
   //  }

   //  // When there's no reference to objects in the tree Java will delete the objects
   //  // automatically with the garbage collector
   //  public void clearTree() {
   //      root = null;
   //      gapNode = null;
   //  }

   //  public boolean mergeTree(BinaryTree addTree) {
   //      System.out.println("HET IS HIER HANNA!");
   //      addTree.printTree(addTree.getRoot());
   //      if(this.root == null) {
   //          System.out.println("ROOT");
   //          this.root = addTree.getRoot();
   //      }
   //      else if(!findGap(this.root)) {
   //          System.out.println("FOUT");
   //          return false;
   //      }
   //      else if(gapNode.leftChild == null) {
   //          System.out.println("LINKS");
   //          gapNode.leftChild = addTree.getRoot();
   //      }
   //      else {
   //          System.out.println("RECHTS");
   //          gapNode.rightChild = addTree.getRoot();
   //      }

   //      return true;
   //  }

   //  public void deleteApp() {
   //      Node node = this.root;
   //      Node parent = node;

   //      deleteApp(node, parent);
   //  }

   //  // Recursive decent through the tree and deleting all empty leaves
   //  private void deleteApp(Node node, Node parent) {
   //      if(node == null) {
   //          return;
   //      }

   //      deleteApp(node.leftChild, node);
   //      deleteApp(node.rightChild, node);

   //      System.out.println("deleteApp" + node.getTokenValue());
   //      if(node.leftChild == null && node.rightChild != null) {
   //          if(node == this.root) {
   //              this.root = node.rightChild;
   //          }
   //          else if(parent.leftChild == node) {
   //              parent.leftChild = node.rightChild;
   //          }
   //          else if(parent.rightChild == node) {
   //              parent.rightChild = node.rightChild;
   //          }
   //          return;
   //      }
   //  }
}

    