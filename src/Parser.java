import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokenList = new ArrayList<Token>();

    public void addToken(Token nieuwToken) {
        tokenList.add(nieuwToken);
    }

    public void printList() {
        for(int i = 0; i < tokenList.length(); i++) {
            System.out.println(tokenList(i));
        }
    }

    public boolean parse() {
        if(tokenList.length() == 0) {
            return false;
        }
        return true;
    }
}
