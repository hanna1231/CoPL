public class Node {
    public Node leftChild;

    public Node rightChild;

    public Token token;

    public Node(Token token) {
        leftChild = null;
        rightChild = null;
        this.token = token;
    }

    public String getTokenValue() {
        return this.token.getValue();
    }
}
